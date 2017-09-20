package com.didekin.tutor.crypto.helper;


import org.junit.Test;

import static com.didekin.tutor.crypto.helper.EncriptionAscii.toHexFromAscii;
import static com.didekin.tutor.crypto.helper.EncriptionAscii.encryptedHexText_1;
import static com.didekin.tutor.crypto.helper.EncriptionAscii.fromBinaryToHexInt;
import static com.didekin.tutor.crypto.helper.EncriptionAscii.getBitsFromAsciiString;
import static com.didekin.tutor.crypto.helper.EncriptionAscii.getBitsFromInt;
import static com.didekin.tutor.crypto.helper.EncriptionAscii.getBitsFromHexString;
import static com.didekin.tutor.crypto.helper.EncriptionAscii.plaintAsciiText_1;
import static com.didekin.tutor.crypto.helper.EncriptionAscii.xOrFromBinary;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * User: pedro@didekin
 * Date: 20/08/17
 * Time: 13:29
 */
public class EncriptionAsciiTest {

    @Test
    public void test_fromBinaryToHexInt() throws Exception
    {
        assertThat(fromBinaryToHexInt("01000001"), is("41")); // A
    }

    @Test
    public void test_getBitsStringFromHexString() throws Exception
    {
       assertThat(getBitsFromHexString("41"), is("01000001")); // A.
    }

    @Test
    public void test_getBitsFromAsciiString() throws Exception
    {
        assertThat(getBitsFromAsciiString("A"), is("01000001"));  // A.
    }

    @Test
    public void test_getBitsFromInt() throws Exception
    {
        StringBuilder stringBuilder = new StringBuilder(8);
        getBitsFromInt(stringBuilder, 8);
        assertThat(stringBuilder.toString(), is("00001000"));
    }

    @Test
    public void test_xOrFromBinary() throws Exception
    {
        assertThat(xOrFromBinary("0011", "1110"), is("1101"));
    }

    @Test
    public void test_encryptHexFromAscii() throws Exception
    {
        assertThat(toHexFromAscii(plaintAsciiText_1), is(encryptedHexText_1));
    }

}