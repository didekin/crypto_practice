package com.didekin.tutor.cryptobookone.vigenere;

import com.didekin.tutor.cryptobookone.charhelper.CharManipulator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import static java.lang.Math.pow;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

/**
 * User: pedro@didekin
 * Date: 21/09/17
 * Time: 10:54
 * <p>
 * <p>
 * Invariants:
 * 1. the range for xor decrypted integer ASCII characters is [32, 127]; that is: only printable characters.
 * 3. maximum key period = 13.
 */
class VigenereBreaker {

    static final int key_max_length = 13;

    /**
     * @param fullCipheredText : encrypted text in a two-digit hexadecimal concatenation.
     * @param period           :           the period of the key to be tried.
     * @return a list with the characters in the 0 + (n * period) positions of the original hexadecimal string.
     */
    static List<Character> getSubtextForTrialPeriod(String fullCipheredText, int period)
    {
        List<Character> lettersList = CharManipulator.getAsciiDecimalCharListFromHexCharsSeq(fullCipheredText);
        List<Character> selectedList = new ArrayList<>(lettersList.size() / period);
        for (int i = 0; i < lettersList.size(); ++i) {
            if (CharManipulator.modBase(i, period) == 0) {
                selectedList.add(lettersList.get(i));
            }
        }
        return selectedList;
    }

    static double[] doProbArrayFromText(List<Character> textChars)
    {
        SortedMap<Integer, Long> sortedMap = textChars.stream()
                .mapToInt(character -> (int) character)
                .boxed()
                .collect(groupingBy(Integer::intValue, TreeMap::new, counting()));

        for (int i = 0; i < 256; ++i) {
            sortedMap.putIfAbsent(i, 0L);
        }
        return sortedMap.values().stream()
                .mapToDouble(value -> ((double) value) / textChars.size())
                .toArray();
    }

    /**
     * Method to obtain the period of the key.
     */
    static int getKeyPeriodFromText(String fullCipheredText, int keyMaxLength)
    {
        double maxSquareSum = 0.0;
        int selectedPeriod = 1;
        for (int period = 1; period <= keyMaxLength; ++period) {
            double squareSum = stream(doProbArrayFromText(getSubtextForTrialPeriod(fullCipheredText, period))).map(prob -> pow(prob, 2)).sum();
            if (squareSum > maxSquareSum) {
                maxSquareSum = squareSum;
                selectedPeriod = period;
            }
        }
        return selectedPeriod;
    }

    /**
     * Method to obtain key as an array of decimal ASCII characters.
     */
    static int[] getKeyCharsFromCipheredAndKeyPeriod(String hexSourceText, int keyPeriod)
    {
        int[] keyInDecimal = new int[keyPeriod];
        for (int i = 0; i < keyPeriod; ++i) {
            keyInDecimal[i] = getIthCharInKey(composeIthStream(hexSourceText, i + 1, keyPeriod));
        }
        return keyInDecimal;
    }

    /**
     * @param hexSourceText: ASCII characters sequence in hexadecimal format.
     * @param ithKeyIndex    :            an integer in the range [1, keyperiod].
     * @param keyPeriod:     the length of the key.
     * @return a list with the ASCII characters in the ith-stream (ith position of the key).
     */
    static List<Character> composeIthStream(String hexSourceText, int ithKeyIndex, int keyPeriod)
    {
        if (ithKeyIndex < 1 || ithKeyIndex > keyPeriod) {
            throw new IllegalArgumentException();
        }

        List<Character> charList = CharManipulator.getAsciiDecimalCharListFromHexCharsSeq(hexSourceText);
        List<Character> ithCharList = new ArrayList<>(charList.size() / keyPeriod);
        for (int p = ithKeyIndex; p <= charList.size(); p += keyPeriod) {
            ithCharList.add(charList.get(p - 1));
        }
        return ithCharList;
    }

    static int getIthCharInKey(List<Character> ithStreamChars)
    {
        int[] asciiIntArrayText = ithStreamChars.stream().mapToInt(character -> (int) character).toArray();

        int[] asciiCharArr = new int[1];
        int[] charIntStreamArr;
        Map<Integer, Long> letterFreqMap;
        double maxCrossProdProb = 0;
        double totalLettersFreq;
        Long[] freqInDecipher = new Long[EnglishLetter.numberEnglishLetters];
        double crossProduct = 0;
        int ithFinalCharInKey = 1;

        // Iterate over the set of ASCII characters.
        for (int i = 0; i < 256; ++i) {
            // Initialize with the character for the iteration.
            asciiCharArr[0] = i;
            // Decrypt ciphered text.
            charIntStreamArr = Arrays.stream(CharManipulator.xorCharArrayWithKeyArray(asciiIntArrayText, asciiCharArr)).toArray();
            // Check for non printable characters.
            if (stream(charIntStreamArr).anyMatch(intChar -> intChar < 32 || intChar > 127)) {
                continue;
            }
            // Frequencies table for English letters in deciphered text.
            letterFreqMap = stream(charIntStreamArr).filter(CharManipulator::isAsciiLetter)
                    .map(CharManipulator::lowAsciiLetterCase)
                    .boxed()
                    .collect(groupingBy(Integer::intValue, TreeMap::new, counting()));
            // Complete table with non appearing English letters.
            for (int j = 97; j <= 122; ++j) {
                letterFreqMap.putIfAbsent(j, 0L);
            }
            // Total ocurrences of (lower case) English letters.
            totalLettersFreq = letterFreqMap.values().stream().mapToLong(Long::longValue).sum();
            // Array with frequencies for all English letters in deciphered text.
            letterFreqMap.values().toArray(freqInDecipher);
            // Cross product of the assumed English letters' probabilities and probabilities in deciphered text.
            for (int k = 0; k < freqInDecipher.length; ++k) {
                crossProduct += EnglishLetter.getLetterProbFromOrder(k) * (freqInDecipher[k] / totalLettersFreq);
            }
            // Update with the new maximum cross product and the new ASCII character candidate, if that is the case.
            if (crossProduct > maxCrossProdProb) {
                ithFinalCharInKey = i;
                maxCrossProdProb = crossProduct;
            }
            crossProduct = 0;
        }
        return ithFinalCharInKey;
    }
}
