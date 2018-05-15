package com.didekin.tutor.cryptobookone.computation;

import java.math.BigInteger;

import static java.math.BigInteger.valueOf;

/**
 * User: pedro
 * Date: 14/07/16
 * Time: 20:20
 */
public class DecryptionCRT {

    static BigInteger decrypt(BigInteger prime1, BigInteger prime2, BigInteger pubK, BigInteger encryptedY)
    {
        BigInteger phi = prime1.subtract(BigInteger.ONE).multiply(prime2.subtract(BigInteger.ONE));
        BigInteger n = prime1.multiply(prime2);
        BigInteger prK = pubK.modInverse(phi);

        // Modular representation of encrypted y.
        BigInteger y_prime1 = encryptedY.mod(prime1);
        BigInteger y_prime2 = encryptedY.mod(prime2);
        // Exponents for modular y's.
        BigInteger prK_Prime1 = prK.mod(prime1.subtract(BigInteger.ONE));
        BigInteger prK_Prime2 = prK.mod(prime2.subtract(BigInteger.ONE));
        // Modular decryptions
        BigInteger x_prime1 = y_prime1.modPow(prK_Prime1, prime1);
        BigInteger x_prime2 = y_prime2.modPow(prK_Prime2, prime2);
        // Modular weigths
        BigInteger w_Prime1 = prime2.modInverse(prime1);
        BigInteger w_Prime2 = prime1.modInverse(prime2);

        return (prime2.multiply(w_Prime1).multiply(x_prime1).add(prime1.multiply(w_Prime2).multiply(x_prime2))).mod(n);
    }

    public static void main(String[] args)
    {
        BigInteger decrypted15 = decrypt(
                valueOf(11),
                valueOf(13),
                valueOf(7),
                valueOf(15));

        System.out.printf("Decrypted %d = %d%n", valueOf(15), decrypted15);

        decrypted15 = RsaOne.decrypt(
                valueOf(11),
                valueOf(13),
                valueOf(7),
                BigInteger.ZERO,
                valueOf(15));

        System.out.printf("DecryptedRSA %d = %d%n", valueOf(15), decrypted15);
    }
}
