package com.didekin.tutor.crypto.pad;

import org.junit.Test;

import static com.didekin.tutor.crypto.pad.OneTimePad.deencryptString;
import static com.didekin.tutor.crypto.pad.OneTimePad.encryptBytesString;
import static com.didekin.tutor.crypto.pad.OneTimePad.generateByteArrayKey;
import static com.didekin.tutor.crypto.pad.OneTimePad.generateHexStringKey;
import static com.didekin.tutor.crypto.pad.OneTimePad.generateIntArrayKey;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * User: pedro@didekin
 * Date: 10/10/2017
 * Time: 14:39
 */
public class OneTimePadTest {

    @Test
    public void test_GetEncryptedBytesString() throws Exception
    {
        String plainText = "holacrypto";
        byte[] key = generateByteArrayKey(plainText.length());
        assertThat(deencryptString(encryptBytesString(plainText,key), key), is(plainText));
    }

    @Test
    public void test_GenerateIntArrayKey() throws Exception
    {
       assertThat(generateIntArrayKey(9).length, is(9));
    }

    @Test
    public void test_GenerateHexStringKey() throws Exception
    {
        assertThat(generateHexStringKey(12).length(), is(24));
    }
}