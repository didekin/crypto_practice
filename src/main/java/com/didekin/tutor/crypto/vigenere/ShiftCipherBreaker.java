package com.didekin.tutor.crypto.vigenere;

import static com.didekin.tutor.crypto.vigenere.EnglishLetter.numberEnglishLetters;
import static java.util.stream.Collectors.joining;

/**
 * User: pedro@didekin
 * Date: 17/09/17
 * Time: 15:20
 */
class ShiftCipherBreaker {

    private static int getShiftFromProb(double[] probabilities)
    {
        int shift = 0;
        double lastMinDiff = 1;
        for (int k = 0; k < numberEnglishLetters; ++k) {
            double sumK = 0;
            for (int i = 0; i < numberEnglishLetters; ++i) {
                sumK += EnglishLetter.values()[i].probability * probabilities[(i + k) % numberEnglishLetters];
            }
            if (Math.abs(sumK - EnglishLetter.squareOriginalProb) < lastMinDiff) {
                lastMinDiff = Math.abs(sumK - EnglishLetter.squareOriginalProb);
                shift = k;
            }
        }
        return shift;
    }

    static String decryptCipherFromShift(String cipheredText, int shift)
    {
        return cipheredText.chars()
                .mapToObj(EnglishLetter::getLetterLowerCaseFromAsciiDec)
                .mapToInt(EnglishLetter::getOrderFromLetterStr)
                .map(order -> EnglishLetter.doModuloAlphabet(order - shift))
                .mapToObj(EnglishLetter::getLetterStrFromOrder)
                .collect(joining());
    }

    static String decryptCipheredText(String cipheredText)
    {
        int shift = getShiftFromProb(EnglishLetter.doProbArrayFromText(cipheredText));

        return cipheredText.chars()
                .mapToObj(EnglishLetter::getLetterLowerCaseFromAsciiDec)
                .mapToInt(EnglishLetter::getOrderFromLetterStr)
                .map(order -> EnglishLetter.doModuloAlphabet(order - shift))
                .mapToObj(EnglishLetter::getLetterStrFromOrder)
                .collect(joining());
    }
}
