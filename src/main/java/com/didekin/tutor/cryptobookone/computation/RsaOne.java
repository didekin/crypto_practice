package com.didekin.tutor.cryptobookone.computation;

import java.math.BigInteger;

import static java.math.BigInteger.valueOf;

/**
 * User: pedro
 * Date: 14/07/16
 * Time: 18:07
 */
public class RsaOne {

    static BigInteger encrypt(BigInteger prime1, BigInteger prime2, BigInteger pubK, BigInteger prK, BigInteger x)
    {
        BigInteger phi = prime1.subtract(BigInteger.ONE).multiply(prime2.subtract(BigInteger.ONE));
        BigInteger n = prime1.multiply(prime2);

        if (!pubK.equals(BigInteger.ZERO) && prK.equals(BigInteger.ZERO)) {
            // Encryption exponent e.
            return x.modPow(pubK, n);
        } else if (pubK.equals(BigInteger.ZERO) && !prK.equals(BigInteger.ZERO)) {
            // Encryption exponent inverse of d.
            return x.modPow(prK.modInverse(phi), n);
        } else {
            throw new IllegalStateException("Invalid d or e");
        }
    }

    static BigInteger decrypt(BigInteger prime1, BigInteger prime2, BigInteger pubK, BigInteger prK, BigInteger x)
    {
        return encrypt(prime1, prime2, prK, pubK, x);
    }

    public static void main(String[] args)
    {
        BigInteger encrypted5 = encrypt(
                valueOf(3),
                valueOf(11),
                BigInteger.ZERO,
                valueOf(7),
                valueOf(5));

        System.out.printf("Encrypted %d = %d%n", valueOf(5), encrypted5);

        BigInteger decrypted5 = decrypt(valueOf(3),
                valueOf(11),
                BigInteger.ZERO,
                valueOf(7),
                encrypted5);

        System.out.printf("Decrypted %d = %d%n", encrypted5, decrypted5);

        System.out.printf("Encrypted %d = %d%n", valueOf(9), encrypt(
                valueOf(5),
                valueOf(11),
                valueOf(3),
                BigInteger.ZERO,
                valueOf(9)));
    }
}
