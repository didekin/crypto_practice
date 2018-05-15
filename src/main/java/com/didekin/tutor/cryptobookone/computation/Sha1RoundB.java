package com.didekin.tutor.cryptobookone.computation;

import com.didekin.tutor.cryptobookone.api.FiveTuple;

import java.math.BigInteger;

import static java.math.BigInteger.valueOf;

/**
 * User: pedro
 * Date: 24/07/16
 * Time: 12:29
 */
public class Sha1RoundB {

    // Constant for stage one.
    static final BigInteger K1 = valueOf(0x5A827999);
    // Initial five 32-bit blocks.
    static final BigInteger a = valueOf(0x00000000);
    static final BigInteger b = valueOf(0x00000000);
    static final BigInteger c = valueOf(0x00000000);
    static final BigInteger d = valueOf(0x00000000);
    static final BigInteger e = valueOf(0x00000000);

    // Function for stage one.
    static BigInteger doFunctionOne()
    {
        return (b.and(c)).or(b.and(d));
    }

    static FiveTuple computeRound(BigInteger wordInRound)
    {
        return new FiveTuple(doAcomponentInRound(wordInRound), a, b.shiftLeft(30), c, d);
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

        word = valueOf(0x10000000);
        System.out.printf(computeRound(word).toString());
    }


}
