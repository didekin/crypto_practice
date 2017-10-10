package com.didekin.tutor.crypto.charhelper;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;
import static java.lang.Integer.toHexString;
import static java.math.BigInteger.valueOf;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

/**
 * User: pedro@didekin
 * Date: 22/09/2017
 * Time: 10:49
 */
public class CharManipulator {

    /**
     * It returns a decimal integer representation of an ASCII character, passed as a hexadecimal string.
     *
     * @param hexAsciiChar: hexadecimal string representation of a ASCII char, without prefix '0x'.
     */
    static int getAsciiDecimalFromHexChar(String hexAsciiChar)
    {
        return parseInt(hexAsciiChar, 16);
    }

    /**
     * Invariants:
     * 1. The length of the initial sequence is an even number.
     *
     * @param hexCharSequence : concatenation of characters encoding as hexadecimal numbers with two digits.
     * @return a list with ASCII characters encoding as decimal integers.
     */
    public static List<Character> getAsciiDecimalCharListFromHexCharsSeq(String hexCharSequence)
    {
        List<Character> letters = new ArrayList<>(hexCharSequence.length() / 2);
        for (int i = 0; i <= hexCharSequence.length() - 2; i += 2) {
            letters.add((char) getAsciiDecimalFromHexChar(hexCharSequence.substring(i, i + 2)));
        }
        return letters;
    }

    /**
     * @param asciiCharSymbol: symbolic representation of a ASCII char.
     * @return an hexadecimal string representation of an ASCII character passed as a symbol.
     */
    public static String getAsciiHexStrFromSymbolChar(String asciiCharSymbol) throws UnsupportedEncodingException
    {
        return toHexString(asciiCharSymbol.getBytes("US-ASCII")[0]);
    }

    /**
     * It returns an hexadecimal string representation of a text passed as symbolic characters.
     *
     * @param asciiCharsSymbols: a concatenations of symbols representing ASCII chars.
     * @return a concatenation of the hexadecimal string representations of each ASCII character in the text passed as parameter.
     */
    public static String getAsciiHexStrFromStrText(String asciiCharsSymbols)
    {
        return asciiCharsSymbols.chars().mapToObj(Integer::toHexString).map(String::toUpperCase).collect(joining());
    }

    /**
     * @param asciiCharSymbol: symbol representation of a ASCII char.
     * @return a decimal integer representation of the ASCII character passed as a symbol.
     */
    static int getAsciiDecimalFromSymbol(String asciiCharSymbol) throws UnsupportedEncodingException
    {
        return asciiCharSymbol.getBytes("US-ASCII")[0];
    }

    public static char getAsciiCharFromAsciiDecimal(int asciiDecimal)
    {
        return (char) asciiDecimal;
    }

    // ................................... OPERATIONS ....................................

    public static boolean isAsciiLetter(int charInt)
    {
        return (charInt >= 65 && charInt <= 90) || (charInt >= 97 && charInt <= 122);
    }

    public static int lowAsciiLetterCase(int charInt)
    {
        return (charInt >= 97 && charInt <= 122) ? charInt : charInt + 32;
    }

    public static int modBase(int intToModulo, int base)
    {
        return valueOf(intToModulo).mod(valueOf(base)).intValue();
    }

    static int doXor(int asciiOne, int asciiTwo)
    {
        return asciiOne ^ asciiTwo;
    }

    /**
     * @param sourceText:       the text, as a symbols string, to be xor.
     * @param asciiHexArrayKey: an hexadecimal string representation of the key to be used.
     * @return a string with the hexadecimal representation of the xor result for each of the characters in the plainText.
     */
    public static String cipherXorWithHexArrayKey(String sourceText, String[] asciiHexArrayKey)
    {
        int[] asciiIntArrayKey = stream(asciiHexArrayKey).mapToInt(CharManipulator::getAsciiDecimalFromHexChar).toArray();
        return cipherXorWithIntArrayKey(sourceText.chars().toArray(), asciiIntArrayKey);
    }

    private static String cipherXorWithIntArrayKey(int[] sourceCharsArray, int[] asciiIntArrayKey)
    {
        return stream(xorCharArrayWithKeyArray(sourceCharsArray, asciiIntArrayKey))
                .mapToObj(Integer::toHexString).map(String::toUpperCase)
                .map(string -> string.length() == 1 ? "0".concat(string) : string)
                .peek(stringHex -> {
                    if (stringHex.length() != 2){
                        throw new IllegalArgumentException();
                    }
                })
                .collect(joining());
    }

    public static int[] xorCharArrayWithKeyArray(int[] sourceCharsArray, int[] asciiIntArrayKey)
    {
        int[] xorArray = new int[sourceCharsArray.length];
        for (int i = 0; i < sourceCharsArray.length; ++i) {
            xorArray[i] = doXor(sourceCharsArray[i], asciiIntArrayKey[modBase(i, asciiIntArrayKey.length)]);
        }
        return xorArray;
    }
}
