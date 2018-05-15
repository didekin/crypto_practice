package com.didekin.tutor.cryptobookone.numeric;

import java.math.BigInteger;
import java.util.Hashtable;
import java.util.Map;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.valueOf;

/**
 * User: pedro
 * Date: 16/07/16
 * Time: 18:16
 * <p>
 * <p>
 * This class calculates the order of the elements of a prime field.
 */
public class OrderElement {

    private static Map<Integer, Integer> getOrderOfElements(BigInteger modulo)
    {
        Map<Integer, Integer> orderOfElements = new Hashtable<>(modulo.intValue() - 1);
        if (modulo.isProbablePrime(8)) {
            BigInteger exp = valueOf(1);
            for (int i = 1; i < modulo.intValue(); i++) {
                while (!valueOf(i).modPow(exp, modulo).equals(ONE)) {
                    exp = exp.add(ONE);
                }
                orderOfElements.put(i, exp.intValue());
                exp = valueOf(1);
            }

        } else {
            throw new IllegalStateException("No prime");
        }
        return orderOfElements;
    }

    public static void main(String[] args)
    {
        Map<Integer, Integer> orders = getOrderOfElements(valueOf(467));
        System.out.printf("%s%n", orders.toString());
    }
}
