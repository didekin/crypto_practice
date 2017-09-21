package com.didekin.tutor.crypto.vigenere;


import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import static com.didekin.tutor.crypto.vigenere.EnglishLetter.b;
import static com.didekin.tutor.crypto.vigenere.EnglishLetter.c;
import static com.didekin.tutor.crypto.vigenere.EnglishLetter.doProbArrayFromText;
import static com.didekin.tutor.crypto.vigenere.EnglishLetter.doXor;
import static com.didekin.tutor.crypto.vigenere.EnglishLetter.doXorWithHexStrKey;
import static com.didekin.tutor.crypto.vigenere.EnglishLetter.getAsciiDecimalFromHexChar;
import static com.didekin.tutor.crypto.vigenere.EnglishLetter.getAsciiDecimalFromLetter;
import static com.didekin.tutor.crypto.vigenere.EnglishLetter.getAsciiHexStrFromStrText;
import static com.didekin.tutor.crypto.vigenere.EnglishLetter.getAsciiHexStrFromSymbolChar;
import static com.didekin.tutor.crypto.vigenere.EnglishLetter.getLetterLowerCaseFromAsciiDec;
import static com.didekin.tutor.crypto.vigenere.EnglishLetter.getLettersArrFromHexString;
import static com.didekin.tutor.crypto.vigenere.EnglishLetter.h;
import static com.didekin.tutor.crypto.vigenere.EnglishLetter.j;
import static com.didekin.tutor.crypto.vigenere.EnglishLetter.squareOriginalProb;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * User: pedro@didekin
 * Date: 19/09/17
 * Time: 11:16
 */
public class EnglishLetterTest {

    // =================================== INSTANCE METHODS  ===================================

    @Test
    public void test_CharFromString()
    {
        assertThat(b.getCharFromLetter(), is('b'));
        System.out.printf("char b = %s", b.getCharFromLetter());
    }

    @Test
    public void test_CharSequenceFromString()
    {
        assertThat("abc".chars()
                .map(Integer::new)
                .mapToObj(Integer::toString)
                .reduce("", String::concat), is("979899"));
    }

    @Test
    public void test_AsciiDecimal() throws UnsupportedEncodingException
    {
        assertThat(b.getAsciiDecimalFromLetter(), is(98));
        assertThat(h.getAsciiDecimalFromLetter(), is(104));
    }

    // =================================== STATIC METHODS  =====================================

    // ================= Conversions  =================
    @Test
    public void test_GetAsciiDecimalFromCharHexChar() throws Exception
    {
        assertThat(getAsciiDecimalFromHexChar("70"), is(112));
    }

    @Test
    public void test_GetLettersArrFromHexString() throws Exception
    {
        assertThat(getLettersArrFromHexString("424644"), is(Arrays.asList('B', 'F', 'D')));
    }

    @Test
    public void test_GetAsciiHexStrFromSymbolChar() throws Exception
    {
        assertThat(getAsciiHexStrFromSymbolChar("H"), is("48"));
    }

    @Test
    public void test_GetAsciiDecimalFromLetter() throws Exception
    {
        assertThat(getAsciiDecimalFromLetter("b"), is(98));
        assertThat(getAsciiDecimalFromLetter("h"), is(104));
        assertThat(getAsciiDecimalFromLetter("\n"), is(10));
    }

    @Test
    public void test_GetAsciiHexStrFromStrText() throws Exception
    {
        assertThat(getAsciiHexStrFromStrText("Hello!"), is("0x48656C6C6F21"));
    }

    @Test
    public void test_GetLetterLowerCaseFromAsciiDec() throws Exception
    {
        assertThat(getLetterLowerCaseFromAsciiDec(98), is("b"));
        assertThat(getLetterLowerCaseFromAsciiDec("c".chars().findFirst().getAsInt()), is(c.letter));
    }

    // ================= Operations  =================

    @Test
    public void test_squareOriginalProb()
    {
        assertThat(Math.abs(squareOriginalProb - 0.065) < 0.005, is(true));
    }

    @Test
    public void test_doXor() throws UnsupportedEncodingException
    {
        assertThat(doXor(0, 1), is(1));
        assertThat(doXor(0, 0), is(0));
        assertThat(doXor(12, 12), is(0));
        assertThat(doXor(12, 0), is(12));

        assertThat(h.doXor(j), is(2));
    }

    @Test
    public void test_DoXorWithHexStrKey() throws Exception
    {
        assertThat(doXorWithHexStrKey("Hello!", new String[]{"A1", "2f"}), is("E94ACD43CE0E"));
    }

    @Test
    public void test_DoProbDistFromText() throws Exception
    {
        String text = "abacccbbb";
        double[] dist = doProbArrayFromText(text);
        assertThat(dist[0], is(2.0 / text.length())); // 'a'.
        assertThat(dist[1], is(4.0 / text.length()));  // 'b'.
        assertThat(dist[2], is(3.0 / text.length()));   // 'c'.

        text = "ac";
        dist = doProbArrayFromText(text);
        assertThat(dist[0], is(1.0 / text.length()));   // 'a'.
        assertThat(dist[25], is(0.0));   // 'z'
    }
}