package com.didekin.tutor.crypto.paarpelzlbook;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.valueOf;

/**
 * User: pedro
 * Date: 16/07/16
 * Time: 19:48
 */
public class Elgamal {

    static BigInteger encrypt(BigInteger p, BigInteger alpha, BigInteger beta, BigInteger i, BigInteger x)
    {
        // Initialize ephemeral key to be used in decryption.
        BigInteger encryptionKey = beta.modPow(i, p);
        return x.multiply(encryptionKey).mod(p);
    }

    static BigInteger decrypt(BigInteger y, BigInteger ephemeralKey, BigInteger d, BigInteger p)
    {
        BigInteger decryptionKey = (ephemeralKey.modPow(d, p)).modInverse(p);
        return y.multiply(decryptionKey).mod(p);
    }

    public static void main(String[] args)
    {
        BigInteger x = valueOf(33);
//        BigInteger i = valueOf(123);
        BigInteger d = valueOf(105);

        BigInteger p = valueOf(467);
        BigInteger alpha = valueOf(2);
        BigInteger beta = alpha.modPow(d, p);

        Set ciphers = new HashSet();
        BigInteger ephemeralKey;
        BigInteger encryptedText;

        for (int i = 2; i < p.subtract(ONE).intValue(); i++) {
            ephemeralKey = alpha.modPow(valueOf(i), p);
            encryptedText = encrypt(p, alpha, beta, valueOf(i), x);
            if (!ciphers.contains(encryptedText)){
                ciphers.add(encryptedText);
            }
        }
        System.out.printf("ciphers length = %d%n", ciphers.size());
        System.out.printf("ciphers = %s%n", ciphers);
    }
}
