package com.didekin.tutor.crypto.paarpelzlbook;

import java.math.BigInteger;

/**
 * User: pedro
 * Date: 14/07/16
 * Time: 11:32
 */
public class FastExponentiation {

    final BigInteger exponent;
    final BigInteger modulus;
    final BigInteger base;

    public FastExponentiation(BigInteger exponentIn, BigInteger modulus, BigInteger base)
    {
        if (exponentIn.intValue() > 0) {
            this.exponent = exponentIn;
        } else {
            exponent = BigInteger.ZERO;
        }
        this.modulus = modulus;
        this.base = base;
    }

    BigInteger exponentiate()
    {
        String bitString = exponent.toString(2);
        BigInteger exponentiation = base;
        for (int i = 1; i < bitString.length(); i++) {
            exponentiation = exponentiation.modPow(BigInteger.valueOf(2), modulus);
            if (bitString.charAt(i) == '1') {
                exponentiation = exponentiation.multiply(base).mod(modulus);
            }
        }
        return exponentiation;
    }

    public static void main(String[] args)
    {
        BigInteger baseIn = BigInteger.valueOf(3);
        BigInteger exponentIn = BigInteger.valueOf(197);
        BigInteger modulusIn = BigInteger.valueOf(101);

        FastExponentiation exponentiation = new FastExponentiation(exponentIn, modulusIn, baseIn);
        System.out.printf("%nMy exponentiation = %d  javaExp = %d%n", exponentiation.exponentiate(),
                baseIn.modPow(exponentIn, modulusIn));
    }
}
