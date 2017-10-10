package com.didekin.tutor.crypto.pad;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;

/**
 * User: pedro@didekin
 * Date: 30/09/2017
 * Time: 15:09
 */
class OneTimePad {

    static String generateHexStringKey(int lengthKey)
    {
        return IntStream.of(generateIntArrayKey(lengthKey)).mapToObj(Integer::toHexString).map(String::toUpperCase)
                .map(string -> string.length() == 1 ? "0".concat(string) : string)
                .peek(stringHex -> {
                    if (stringHex.length() != 2) {
                        throw new IllegalArgumentException();
                    }
                })
                .collect(joining());
    }

    static int[] generateIntArrayKey(int lengthKey)
    {
        int[] keyArrayInt = new int[lengthKey];
        SecureRandom secRandom = new SecureRandom();
        for (int i = 0; i < lengthKey; ++i) {
            keyArrayInt[i] = secRandom.nextInt();
        }
        return keyArrayInt;
    }

    static byte[] generateByteArrayKey(int lengthKey)
    {
        byte[] byteArrayKey = new byte[lengthKey];
        new SecureRandom().nextBytes(byteArrayKey);
        for (int i = 0; i < byteArrayKey.length; ++i) {
            byteArrayKey[i] = byteArrayKey[i] < 0 ? (byte) -byteArrayKey[i] : byteArrayKey[i];
        }
        return byteArrayKey;
    }

    static String encryptBytesString(String plainText) throws UnsupportedEncodingException
    {
        byte[] byteKey = generateByteArrayKey(plainText.length());
        return encryptBytesString(plainText, byteKey);
    }

    static String encryptBytesString(String plainText, byte[] byteArrayKey) throws UnsupportedEncodingException
    {
        byte[] plainBytes = plainText.getBytes("US-ASCII");
        byte[] encrytedBytes = new byte[plainBytes.length];
        for (int i = 0; i < plainBytes.length; ++i) {
            encrytedBytes[i] = (byte) (plainBytes[i] ^ byteArrayKey[i]);
        }
        return new String(encrytedBytes, "US-ASCII");
    }

    static String deencryptString(String encryptedText, byte[] byteArrayKey) throws UnsupportedEncodingException
    {
        byte[] encryptedBytes = encryptedText.getBytes("US-ASCII");
        byte[] deencrytedBytes = new byte[encryptedBytes.length];
        for (int i = 0; i < deencrytedBytes.length; ++i){
            deencrytedBytes[i] = (byte) (encryptedBytes[i] ^ byteArrayKey[i]);
        }
        return new String(deencrytedBytes, "US-ASCII");
    }

    public static void main(String[] args)
    {
    }
}
