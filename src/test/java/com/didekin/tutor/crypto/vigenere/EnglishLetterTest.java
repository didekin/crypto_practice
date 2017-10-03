package com.didekin.tutor.crypto.vigenere;


import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static com.didekin.tutor.crypto.vigenere.EnglishLetter.b;
import static com.didekin.tutor.crypto.vigenere.EnglishLetter.c;
import static com.didekin.tutor.crypto.vigenere.EnglishLetter.doProbArrayFromText;
import static com.didekin.tutor.crypto.vigenere.EnglishLetter.getLetterLowerCaseFromAsciiDec;
import static com.didekin.tutor.crypto.vigenere.EnglishLetter.h;
import static com.didekin.tutor.crypto.vigenere.EnglishLetter.squareOriginalProb;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * User: pedro@didekin
 * Date: 19/09/17
 * Time: 11:16
 */
public class EnglishLetterTest {


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

    @Test
    public void test_GetLetterLowerCaseFromAsciiDec() throws Exception
    {
        assertThat(getLetterLowerCaseFromAsciiDec(98), is("b"));
        assertThat(getLetterLowerCaseFromAsciiDec("c".chars().findFirst().getAsInt()), is(c.letter));
    }

    @Test
    public void test_squareOriginalProb()
    {
        assertThat(Math.abs(squareOriginalProb - 0.065) < 0.005, is(true));
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