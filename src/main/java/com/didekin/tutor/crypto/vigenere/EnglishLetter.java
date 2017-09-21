package com.didekin.tutor.crypto.vigenere;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import static java.lang.Math.pow;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;

/**
 * User: pedro@didekin
 * Date: 17/09/17
 * Time: 14:24
 */
public enum EnglishLetter {

    a("a", .082, 0),
    b("b", .015, 1),
    c("c", .028, 2),
    d("d", .043, 3),
    e("e", .127, 4),
    f("f", .022, 5),
    g("g", .02, 6),
    h("h", .061, 7),
    i("i", .07, 8),
    j("j", .002, 9),
    k("k", .008, 10),
    l("l", .04, 11),
    m("m", .024, 12),
    n("n", .067, 13),
    o("o", .075, 14),
    p("p", .019, 15),
    q("q", .001, 16),
    r("r", .06, 17),
    s("s", .063, 18),
    t("t", .091, 19),
    u("u", .028, 20),
    v("v", .01, 21),
    w("w", .023, 22),
    x("x", .001, 23),
    y("y", .02, 24),
    z("z", .001, 25),;

    static final BigInteger baseModulo = BigInteger.valueOf(26);
    static final double squareOriginalProb = stream(EnglishLetter.values()).mapToDouble(EnglishLetter::getProbability).map(prob -> pow(prob, 2)).sum();

    static final Map<Integer, String> fromOrderToLetter = new HashMap<>(26);

    static {
        for (EnglishLetter letterEnum : EnglishLetter.values()) {
            fromOrderToLetter.putIfAbsent(letterEnum.order, letterEnum.letter);
        }
    }

    static String getLetterStrFromOrder(int order)
    {
        return fromOrderToLetter.get(order);
    }

    static final Map<String, Integer> fromLetterToOrder = new HashMap<>(26);

    static {
        for (EnglishLetter letterEnum : EnglishLetter.values()) {
            fromLetterToOrder.putIfAbsent(letterEnum.letter, letterEnum.order);
        }
    }

    static int getOrderFromLetterStr(String letter)
    {
        return fromLetterToOrder.get(letter);
    }

    final String letter;
    final double probability;
    final int order;

    EnglishLetter(String character, double probability, int orderIn)
    {
        letter = character;
        this.probability = probability;
        order = orderIn;
    }

    public double getProbability()
    {
        return probability;
    }

    int getAsciiDecimalFromLetter() throws UnsupportedEncodingException
    {
        return letter.getBytes("US-ASCII")[0];
    }

    char getCharFromLetter()
    {
        return letter.charAt(0);
    }

    int doXor(EnglishLetter letter) throws UnsupportedEncodingException
    {
        return getAsciiDecimalFromLetter() ^ letter.getAsciiDecimalFromLetter();
    }

    // =================================== STATIC METHODS  =====================================

    // ================= Conversions  =================

    /**
     * It returns a decimal integer representation of an ASCII character, passed as a hexadecimal string.
     *
     * @param hexadecimalLetter: hexadecimal string representation of a ASCII char, without prefix '0x'.
     */
    static int getAsciiDecimalFromHexChar(String hexadecimalLetter)
    {
        return Integer.parseInt(hexadecimalLetter, 16);
    }

    /**
     * Invariants:
     * 1. The length of the initial sequence is an even number.
     * @param hexCharSequence : concatenation of characters encoding as hexadecimal numbers with two digits.
     * @return an array with ASCII characters encoding as decimal integers.
     */
    static List<Character> getLettersArrFromHexString(String hexCharSequence)
    {
        List<Character>  letters = new ArrayList<>(hexCharSequence.length()/2);
        for (int i = 0; i <= hexCharSequence.length() - 2; i += 2){
            letters.add((char) getAsciiDecimalFromHexChar(hexCharSequence.substring(i,i+2)));
        }
        return letters;
    }

    /**
     * It returns an hexadecimal string representation of an ASCII character passed as a symbol.
     *
     * @param charToHexString: symbolic representation of a ASCII char.
     */
    static String getAsciiHexStrFromSymbolChar(String charToHexString) throws UnsupportedEncodingException
    {
        return Integer.toHexString(getAsciiDecimalFromLetter(charToHexString));
    }

    /**
     * It returns an hexadecimal string representation of a text passed as symbolic characters.
     *
     * @param textToHexString: hexadecimal string representation of a ASCII char.
     * @return a concatenation of the hexadecimal string representations of each ASCII character in the text passed as parameter.
     */
    static String getAsciiHexStrFromStrText(String textToHexString)
    {
        return "0x".concat(textToHexString.chars().mapToObj(Integer::toHexString).map(String::toUpperCase).collect(joining()));
    }

    /**
     * @param letter: hexadecimal string representation of a ASCII char.
     * @return a decimal integer representation of the ASCII character passed as a symbol.
     */
    static int getAsciiDecimalFromLetter(String letter) throws UnsupportedEncodingException
    {
        return letter.getBytes("US-ASCII")[0];
    }

    /**
     * @param asciiCharDecimalInt: decimal integer representation of an ASCII character.
     * @return a symbol representation of the ASCII character passed as an integer.
     */
    static String getLetterLowerCaseFromAsciiDec(int asciiCharDecimalInt)
    {
        return new String(new byte[]{(byte) asciiCharDecimalInt}).toLowerCase();
    }

    // ================= Operations  =================

    static int doModuloAlphabet(int orderLetter)
    {
        return BigInteger.valueOf(orderLetter).mod(baseModulo).intValue();
    }

    static int doModWithBase(int intToModulo, int base)
    {
        return BigInteger.valueOf(intToModulo).mod(BigInteger.valueOf(base)).intValue();
    }

    static int doXor(int asciiOne, int asciiTwo)
    {
        return asciiOne ^ asciiTwo;
    }

    /**
     * @param plainText:        the text, as a symbols string, to be xor.
     * @param asciiHexArrayKey: an hexadecimal string representation of the key to be used.
     * @return a string with the hexadecimal representation of the xor result for each of the characters in the plainText.
     */
    static String doXorWithHexStrKey(String plainText, String[] asciiHexArrayKey)
    {
        int[] asciiIntArrayText = plainText.chars().toArray();
        int[] asciiIntArrayKey = Arrays.stream(asciiHexArrayKey).mapToInt(EnglishLetter::getAsciiDecimalFromHexChar).toArray();
        int[] xorArray = new int[asciiIntArrayText.length];
        for (int i = 0; i < asciiIntArrayText.length; ++i) {
            xorArray[i] = doXor(asciiIntArrayText[i], asciiIntArrayKey[doModWithBase(i, asciiIntArrayKey.length)]);
        }
        return Arrays.stream(xorArray)
                .mapToObj(Integer::toHexString).map(String::toUpperCase)
                .map(string -> string.length() != 2 ? "0".concat(string) : string)
                .collect(joining());
    }

    /**
     * Return a 26 size array with the probability distribution of each letter in the text.
     * Probability == 0 for no present letters.
     */
    static double[] doProbArrayFromText(String text)
    {
        SortedMap<String, Long> freqDistrib = text.chars().mapToObj(EnglishLetter::getLetterLowerCaseFromAsciiDec).collect(groupingBy(String::toLowerCase, TreeMap::new, counting()));
        for (EnglishLetter englishLetter : values()) {
            freqDistrib.putIfAbsent(englishLetter.letter, 0L);
        }
        return freqDistrib.values().stream().mapToDouble(value -> ((double) value) / text.length()).toArray();
    }
}
