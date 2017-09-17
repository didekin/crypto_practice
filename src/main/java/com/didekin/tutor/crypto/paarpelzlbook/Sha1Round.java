package com.didekin.tutor.crypto.paarpelzlbook;

import java.math.BigInteger;

import static java.math.BigInteger.valueOf;

/**
 * User: pedro
 * Date: 24/07/16
 * Time: 12:29
 */
public class Sha1Round {

    // Constant for stage one.
    static final BigInteger K1 = valueOf(0x5A827999);
    // Initial five 32-bit blocks.
    static final BigInteger a = valueOf(0x67452301);
    static final BigInteger b = valueOf(0xEFCDAB89);
    static final BigInteger c = valueOf(0x98BADCFE);
    static final BigInteger d = valueOf(0x10325476);
    static final BigInteger e = valueOf(0xC3D2E1F0);

    // Function for stage one.
    static BigInteger doFunctionOne()
    {
        return (b.and(c)).or(b.and(d));
    }

    static FiveTuple computeRound(BigInteger wordInRound)
    {
         return new FiveTuple(doAcomponentInRound(wordInRound),a,b.shiftLeft(30),c,d);
    }

    // Computation for A element in the five tuple resulting from  a round.
    private static BigInteger doAcomponentInRound(BigInteger wordInRound)
    {
        return e.add(doFunctionOne()).add(a.shiftLeft(5)).add(wordInRound).add(K1);
    }

    public static void main(String[] args)
    {
        BigInteger word = valueOf(0x00000000);
        System.out.printf(computeRound(word).toString());
    }


}
