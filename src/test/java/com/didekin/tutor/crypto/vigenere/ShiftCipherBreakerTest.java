package com.didekin.tutor.crypto.vigenere;

import org.junit.Test;

import static com.didekin.tutor.crypto.vigenere.ShiftCipherBreaker.decryptCipherFromShift;
import static com.didekin.tutor.crypto.vigenere.ShiftCipherBreaker.decryptCipheredText;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * User: pedro@didekin
 * Date: 20/09/17
 * Time: 14:24
 */
public class ShiftCipherBreakerTest {

    @Test
    public void test_DecryptCipherFromShift() throws Exception
    {
        assertThat(decryptCipherFromShift("hqq", 2), is("foo"));
        assertThat(decryptCipherFromShift("foo", 0), is("foo"));
        assertThat(decryptCipherFromShift("dmm", 24), is("foo"));
    }

    @Test
    public void test_decryptCipheredText() throws Exception
    {
        assertThat(decryptCipheredText("OVDTHUFWVZZPISLRLFZHYLAOLYL"),
                is("howmanypossiblekeysarethere"));
    }

}