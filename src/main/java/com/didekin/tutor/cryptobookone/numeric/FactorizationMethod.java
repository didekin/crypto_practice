package com.didekin.tutor.cryptobookone.numeric;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;
import static java.math.BigInteger.valueOf;

/**
 * User: pedro
 * Date: 12/07/16
 * Time: 19:33
 * <p>
 * Factorization method based on Davenport page 34.
 */
public class FactorizationMethod {

    private static final BigInteger two = ONE.add(ONE);
    private List<BigInteger> factors;

    private FactorizationMethod()
    {
        factors = new ArrayList<>(2);
    }

    public static void main(String[] args)
    {
        FactorizationMethod factorization = new FactorizationMethod();
        factorization.factorOddInteger(valueOf(52));
        int i = 0;
        for (BigInteger factor : factorization.factors) {
            System.out.printf("factor %d : %d %n", ++i, factor);
        }
    }

    private void factorEvenInteger(final BigInteger number)
    {
        BigInteger[] quotientRemainder;
        BigInteger dividend = number;
        BigInteger remainder;
        do {
            factors.add(two);
            quotientRemainder = dividend.divideAndRemainder(two);
            dividend = quotientRemainder[0];
            remainder = quotientRemainder[1];
        } while (!remainder.equals(ZERO) && dividend.intValue() > 1);
        if (!dividend.equals(ONE)) {
            factorOddInteger(dividend);
        }
    }

    private void factorOddInteger(final BigInteger number)
    {
        if (number.equals(ONE)) {
            return;
        }

        if (number.remainder(two).equals(ZERO)) {
            factorEvenInteger(number);
            return;
        }

        BigInteger[] quotientRemainder;
        BigInteger roundsNumber = ONE;
        BigInteger sumQuotients = BigInteger.ZERO;
        BigInteger dividend;
        BigInteger divisor;
        BigInteger complementFactor;

        do {
            dividend = roundsNumber.multiply(number).subtract(roundsNumber.multiply(two).add(ONE).multiply
                    (sumQuotients));
            divisor = roundsNumber.multiply(two).add(ONE);
            quotientRemainder = dividend.divideAndRemainder(divisor);
            sumQuotients = sumQuotients.add(quotientRemainder[0]);
            roundsNumber = roundsNumber.add(ONE);
        } while (quotientRemainder[1].intValue() > 0);

        factors.add(divisor);
        complementFactor = number.divide(divisor);

        if (complementFactor.isProbablePrime(8) && !complementFactor.equals(ONE)) {
            factors.add(complementFactor);
        } else {
            factorOddInteger(complementFactor);
        }
    }
}
