package com.didekin.tutor.crypto.paarpelzlbook;

import java.math.BigInteger;

import static java.math.BigInteger.valueOf;

/**
 * User: pedro
 * Date: 16/07/16
 * Time: 19:48
 */
public class DiffieHellmanOne {

    final BigInteger prvKeyOne;
    final BigInteger prvKeyTwo;
    final BigInteger compositeKey;

    public DiffieHellmanOne(BigInteger p, BigInteger alpha, BigInteger a, BigInteger b)
    {
        prvKeyOne = alpha.modPow(a,p);
        prvKeyTwo = alpha.modPow(b,p);
        compositeKey = prvKeyOne.modPow(b,p);
        if (!compositeKey.equals(prvKeyTwo.modPow(a,p))){
            throw new IllegalStateException("Wrong composite key");
        }
    }

    public static void main(String[] args)
    {
        DiffieHellmanOne diffieHellmanOne = new DiffieHellmanOne(valueOf(467),valueOf(2),valueOf(3),valueOf(5));
        System.out.printf("prvKeyTwo = %d%n", diffieHellmanOne.prvKeyOne);
        System.out.printf("prvKeyTwo = %d%n", diffieHellmanOne.prvKeyTwo);
        System.out.printf("composite = %d%n", diffieHellmanOne.compositeKey);
    }
}
