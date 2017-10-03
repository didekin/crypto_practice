package com.didekin.tutor.crypto.charhelper;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import static com.didekin.tutor.crypto.charhelper.CharManipulator.cipherXorWithHexArrayKey;
import static com.didekin.tutor.crypto.charhelper.CharManipulator.doXor;
import static com.didekin.tutor.crypto.charhelper.CharManipulator.getAsciiDecimalCharListFromHexCharsSeq;
import static com.didekin.tutor.crypto.charhelper.CharManipulator.getAsciiDecimalFromHexChar;
import static com.didekin.tutor.crypto.charhelper.CharManipulator.getAsciiDecimalFromSymbol;
import static com.didekin.tutor.crypto.charhelper.CharManipulator.getAsciiHexStrFromStrText;
import static com.didekin.tutor.crypto.charhelper.CharManipulator.getAsciiHexStrFromSymbolChar;
import static com.didekin.tutor.crypto.charhelper.CharManipulator.lowAsciiLetterCase;
import static com.didekin.tutor.crypto.charhelper.CharManipulator.xorCharArrayWithKeyArray;
import static com.didekin.tutor.crypto.vigenere.EnglishLetter.h;
import static com.didekin.tutor.crypto.vigenere.EnglishLetter.j;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * User: pedro@didekin
 * Date: 22/09/2017
 * Time: 11:01
 */
public class CharManipulatorTest {

    @Test
    public void test_GetAsciiCharFromAsciiDecimal() throws Exception
    {
        assertThat(CharManipulator.getAsciiCharFromAsciiDecimal(36), is('$'));
        assertThat(CharManipulator.getAsciiCharFromAsciiDecimal(56), is('8'));
        assertThat(CharManipulator.getAsciiCharFromAsciiDecimal(72), is('H'));
    }

    @Test
    public void test_LowAsciiLetterCase() throws Exception
    {
        assertThat((char) lowAsciiLetterCase('A'), is('a'));
        assertThat((char) lowAsciiLetterCase('Z'), is('z'));
        assertThat((char) lowAsciiLetterCase('x'), is('x'));
    }

    @Test
    public void test_GetAsciiDecimalFromCharHexChar() throws Exception
    {
        assertThat(getAsciiDecimalFromHexChar("70"), is(112));
    }

    @Test
    public void test_GetAsciiDecimalCharListFromHexCharSeq() throws Exception
    {
        assertThat(getAsciiDecimalCharListFromHexCharsSeq("424644"), is(Arrays.asList('B', 'F', 'D')));
    }

    @Test
    public void test_GetAsciiHexStrFromSymbolChar() throws Exception
    {
        assertThat(getAsciiHexStrFromSymbolChar("H"), is("48"));
    }

    @Test
    public void test_GetAsciiDecimalFromSymbol() throws Exception
    {
        assertThat(getAsciiDecimalFromSymbol("b"), is(98));
        assertThat(getAsciiDecimalFromSymbol("h"), is(104));
        assertThat(getAsciiDecimalFromSymbol("\n"), is(10));
    }

    @Test
    public void test_GetAsciiHexStrFromStrText() throws Exception
    {
        assertThat(getAsciiHexStrFromStrText("Hello!"), is("48656C6C6F21"));
    }

    // =================================== OPERATIONS ===================================

    @Test
    public void test_doXor() throws UnsupportedEncodingException
    {
        assertThat(doXor(0, 1), is(1));
        assertThat(doXor(0, 0), is(0));
        assertThat(doXor(12, 12), is(0));
        assertThat(doXor(12, 0), is(12));

        assertThat(h.doXor(j), is(2));
        assertThat(h.doXor(h), is(0));
    }

    @Test
    public void test_CipherXorWithIntArrayKey() throws Exception
    {

    }

    @Test
    public void test_XorCharArrayWithKeyArray() throws Exception
    {
        int[] sourceIntArray = new int[]{0, 1, (int) 'a', (int) 'b', 254};
        int[] keyIntArray = new int[]{(int) 'a'};
        assertThat(xorCharArrayWithKeyArray(sourceIntArray, keyIntArray)[0], is((int) 'a'));
        assertThat(xorCharArrayWithKeyArray(sourceIntArray, keyIntArray)[1], is(96));
        assertThat(xorCharArrayWithKeyArray(sourceIntArray, keyIntArray)[2], is(0));
        assertThat(xorCharArrayWithKeyArray(sourceIntArray, keyIntArray)[3], is(3));
        assertThat(xorCharArrayWithKeyArray(sourceIntArray,keyIntArray)[4], is(159));
    }

    @Test
    public void test_cipherXorWithHexArrayKey() throws Exception
    {
        String[] hexKey = new String[]{"A1", "2f"};
        assertThat(cipherXorWithHexArrayKey("Hello!", hexKey), is("E94ACD43CE0E"));
    }
}