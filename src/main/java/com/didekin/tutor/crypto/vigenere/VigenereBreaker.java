package com.didekin.tutor.crypto.vigenere;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import static com.didekin.tutor.crypto.vigenere.EnglishLetter.doModWithBase;
import static com.didekin.tutor.crypto.vigenere.EnglishLetter.getLettersArrFromHexString;
import static java.lang.Math.pow;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

/**
 * User: pedro@didekin
 * Date: 21/09/17
 * Time: 10:54
 * <p>
 *
 * Invariants:
 * 1. the range for xor decrypted integer ASCII characters is [32, 127]; that is: only printable characters.
 * 2. we do not consider '\n' as a possible ocurrence in the plain text.
 * 3. no numbers in the plain text.
 */
class VigenereBreaker {

    private static final int key_max_length = 13;

    /**
     * @param fullCipheredText : encrypted text in a two-digit hexadecimal concatenation.
     * @param period           :           the period of the key to be tried.
     * @return a list with the characters in the 0 + (n * period) positions of the original hexadecimal string, after eliminating
     * non-letters characters and converting these to lower case.
     */
    static List<Character> getSubtextByShift(String fullCipheredText, int period)
    {
        List<Character> lettersList = getLettersArrFromHexString(fullCipheredText);
        List<Character> selectedList = new ArrayList<>(lettersList.size() / period);
        for (int i = 0; i < lettersList.size(); ++i) {
            if (doModWithBase(i, period) == 0) {
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

    static int getKeyPeriodFromText(String fullCipheredText)
    {
        double maxSquareSum = 0.0;
        int selectedPeriod = 1;
        for (int period = 1; period <= key_max_length; ++period) {
            double squareSum = stream(doProbArrayFromText(getSubtextByShift(fullCipheredText, period))).map(prob -> pow(prob, 2)).sum();
            if (squareSum > maxSquareSum) {
                maxSquareSum = squareSum;
                selectedPeriod = period;
            }
        }
        return selectedPeriod;
    }

    static int[] getKeyCharsFromCipheredText()
    {
        return new int[]{0};
    }
}
