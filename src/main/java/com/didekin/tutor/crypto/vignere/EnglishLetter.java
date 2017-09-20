package com.didekin.tutor.crypto.vignere;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import static java.lang.Math.pow;
import static java.util.Arrays.stream;
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

    static int getAsciiDecimalFromStringChar(String letter) throws UnsupportedEncodingException
    {
        return letter.getBytes("US-ASCII")[0];
    }

    static String getStringCharFromAsciiDec(int asciiChar)
    {
        return new String(new byte[]{(byte) asciiChar}).toLowerCase();
    }

    static int doModuloAlphabet(int orderLetter)
    {
        return BigInteger.valueOf(orderLetter).mod(baseModulo).intValue();
    }

    static int doXor(int asciiOne, int asciiTwo)
    {
        return asciiOne ^ asciiTwo;
    }

    /**
     * Return a 26 size array with the probability distribution of each letter in the text.
     * Probability == 0 for no present letters.
     */
    static double[] doProbArrayFromText(String text)
    {
        SortedMap<String, Long> freqDistrib = text.chars().mapToObj(EnglishLetter::getStringCharFromAsciiDec).collect(groupingBy(String::toLowerCase, TreeMap::new, counting()));
        for (EnglishLetter englishLetter : values()) {
            freqDistrib.putIfAbsent(englishLetter.letter, 0L);
        }
        return freqDistrib.values().stream().mapToDouble(value -> ((double) value) / text.length()).toArray();
    }
}
