package com.didekin.tutor.crypto.vignere;


import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static com.didekin.tutor.crypto.vignere.EnglishLetter.b;
import static com.didekin.tutor.crypto.vignere.EnglishLetter.c;
import static com.didekin.tutor.crypto.vignere.EnglishLetter.doXor;
import static com.didekin.tutor.crypto.vignere.EnglishLetter.getAsciiDecimalFromStringChar;
import static com.didekin.tutor.crypto.vignere.EnglishLetter.getStringCharFromAsciiDec;
import static com.didekin.tutor.crypto.vignere.EnglishLetter.h;
import static com.didekin.tutor.crypto.vignere.EnglishLetter.j;
import static com.didekin.tutor.crypto.vignere.EnglishLetter.squareOriginalProb;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * User: pedro@didekin
 * Date: 19/09/17
 * Time: 11:16
 */
public class EnglishLetterTest {

    @Test
    public void test_squareOriginalProb()
    {
        assertThat(Math.abs(squareOriginalProb - 0.065) < 0.005, is(true));
    }

    @Test
    public void test_CharFromString()
    {
        assertThat(b.getCharFromLetter(), is('b'));
        System.out.printf("char b = %s", b.getCharFromLetter());
    }

    @Test
    public void test_GetStringFromAsciiDec() throws Exception
    {
        assertThat(getStringCharFromAsciiDec(98), is("b"));
        assertThat(getStringCharFromAsciiDec("c".chars().findFirst().getAsInt()), is(c.letter));
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

        assertThat(getAsciiDecimalFromStringChar("b"), is(98));
        assertThat(getAsciiDecimalFromStringChar("h"), is(104));
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
    public void test_DoProbDistFromText() throws Exception
    {
        String text = "abacccbbb";
        double[] dist = EnglishLetter.doProbArrayFromText(text);
        assertThat(dist[0], is(2.0 / text.length())); // 'a'.
        assertThat(dist[1], is(4.0 / text.length()));  // 'b'.
        assertThat(dist[2], is(3.0 / text.length()));   // 'c'.

        text = "ac";
        dist = EnglishLetter.doProbArrayFromText(text);
        assertThat(dist[0], is(1.0 / text.length()));   // 'a'.
        assertThat(dist[25], is(0.0));   // 'z'
    }
}