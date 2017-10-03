package com.didekin.tutor.crypto;

import org.junit.Test;

import java.math.BigInteger;
import java.security.SecureRandom;

import static com.didekin.tutor.crypto.charhelper.ValidaPattern.PASSWORD;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * User: pedro@didekin
 * Date: 16/08/17
 * Time: 10:45
 */
public class SecureRandomTest {

    @Test
    public void test_ToString_1(){
        final BigInteger pswdInteger = new BigInteger(65, new SecureRandom());
        System.out.println(pswdInteger.toString(36));
        assertThat(PASSWORD.isPatternOk(pswdInteger.toString(36)), is(true));
    }

    @Test
    public void test_ToString_2(){
        for(int i = 0; i < 20000; ++i){
            final String fieldToCheck = new BigInteger(65, new SecureRandom()).toString(36);
//            System.out.println(fieldToCheck);
            assertThat(PASSWORD.isPatternOk(fieldToCheck), is(true));
            assertThat(fieldToCheck.toCharArray().length <= 13 & fieldToCheck.toCharArray().length >= 10, is(true));
        }
    }
}
