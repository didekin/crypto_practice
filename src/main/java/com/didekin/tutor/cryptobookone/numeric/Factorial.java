package com.didekin.tutor.cryptobookone.numeric;

import java.math.BigInteger;

/**
 * User: pedro
 * Date: 13/07/16
 * Time: 20:44
 */
public class Factorial {

    private static BigInteger getFactorial(BigInteger number)
    {
        BigInteger factorial = number;
        for (int i = number.intValue()-1; i > 0; --i) {
            factorial = factorial.multiply(BigInteger.valueOf(i));
        }
        return factorial;
    }

    public static void main(String[] args)
    {
        BigInteger number = BigInteger.valueOf(22);
        System.out.printf("Factorial %d = %d %n", number, getFactorial(number));
    }
}
