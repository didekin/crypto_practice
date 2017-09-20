package com.didekin.tutor.crypto.vignere;

import static java.util.stream.Collectors.joining;

/**
 * User: pedro@didekin
 * Date: 17/09/17
 * Time: 15:20
 */
class ShiftCipherBroker {

    private static int getShiftFromProb(double[] probabilities)
    {
        int shift = 0;
        double lastMinDiff = 1;
        for (int k = 0; k < 26; ++k) {
            double sumK = 0;
            for (int i = 0; i < 26; ++i) {
                sumK += EnglishLetter.values()[i].probability * probabilities[(i + k) % 26];
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
                .mapToObj(EnglishLetter::getStringCharFromAsciiDec)
                .mapToInt(EnglishLetter::getOrderFromLetterStr)
                .map(order -> EnglishLetter.doModuloAlphabet(order - shift))
                .mapToObj(EnglishLetter::getLetterStrFromOrder)
                .collect(joining());
    }

    static String decryptCipheredText(String cipheredText)
    {
        int shift = getShiftFromProb(EnglishLetter.doProbArrayFromText(cipheredText));

        return cipheredText.chars()
                .mapToObj(EnglishLetter::getStringCharFromAsciiDec)
                .mapToInt(EnglishLetter::getOrderFromLetterStr)
                .map(order -> EnglishLetter.doModuloAlphabet(order - shift))
                .mapToObj(EnglishLetter::getLetterStrFromOrder)
                .collect(joining());
    }
}
