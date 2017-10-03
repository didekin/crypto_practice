package com.didekin.tutor.crypto.vigenere;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import static java.lang.Math.pow;
import static java.util.Arrays.stream;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

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

    public static final int numberEnglishLetters = 26;
    static final BigInteger baseModulo = BigInteger.valueOf(numberEnglishLetters);
    static final double squareOriginalProb = stream(EnglishLetter.values()).mapToDouble(EnglishLetter::getProbability).map(prob -> pow(prob, 2)).sum();

    static final Map<Integer, EnglishLetter> fromOrderToLetterEnum = new HashMap<>(numberEnglishLetters);

    static {
        for (EnglishLetter letterEnum : EnglishLetter.values()) {
            fromOrderToLetterEnum.putIfAbsent(letterEnum.order, letterEnum);
        }
    }

    static String getLetterStrFromOrder(int order)
    {
        return fromOrderToLetterEnum.get(order).letter;
    }

    static double getLetterProbFromOrder(int order)
    {
        return fromOrderToLetterEnum.get(order).probability;
    }

    static final Map<String, Integer> fromLetterToOrder = new HashMap<>(numberEnglishLetters);

    static {
        for (EnglishLetter letterEnum : EnglishLetter.values()) {
            fromLetterToOrder.putIfAbsent(letterEnum.letter, letterEnum.order);
        }
    }

    static int getOrderFromLetterStr(String letter)
    {
        return fromLetterToOrder.get(letter);
    }

    public final String letter;
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

    public int doXor(EnglishLetter letter) throws UnsupportedEncodingException
    {
        return getAsciiDecimalFromLetter() ^ letter.getAsciiDecimalFromLetter();
    }

    // =================================== STATIC METHODS  =====================================

    static int doModuloAlphabet(int orderLetter)
    {
        return BigInteger.valueOf(orderLetter).mod(baseModulo).intValue();
    }

    /**
     * @param asciiCharDecimal: decimal integer representation of an ASCII character.
     * @return a symbol representation of the ASCII character passed as an integer.
     */
    public static String getLetterLowerCaseFromAsciiDec(int asciiCharDecimal)
    {
        return new String(new byte[]{(byte) asciiCharDecimal}).toLowerCase();
    }

    /**
     * Invariants:
     * 1. the text is composed exclusively of letters, either in upper or lower case.
     * <p>
     * Return a 26 size array with the probability distribution of each letter in the text.
     * Probability == 0 for no present letters.
     */
    public static double[] doProbArrayFromText(String text)
    {
        SortedMap<String, Long> freqDistrib = text.chars().mapToObj(EnglishLetter::getLetterLowerCaseFromAsciiDec).collect(groupingBy(identity(), TreeMap::new, counting()));
        for (EnglishLetter englishLetter : values()) {
            freqDistrib.putIfAbsent(englishLetter.letter, 0L);
        }
        return freqDistrib.values().stream().mapToDouble(value -> ((double) value) / text.length()).toArray();
    }
}
