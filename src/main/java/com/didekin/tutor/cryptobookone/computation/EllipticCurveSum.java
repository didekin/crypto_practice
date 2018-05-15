package com.didekin.tutor.cryptobookone.computation;

import com.didekin.tutor.cryptobookone.api.BigIntDuple;
import com.didekin.tutor.cryptobookone.api.Polynomial;

import java.math.BigInteger;

import static java.math.BigInteger.valueOf;

/**
 * User: pedro
 * Date: 20/07/16
 * Time: 18:48
 */
public class EllipticCurveSum {

    public static BigIntDuple sumPoints(BigIntDuple point1, BigIntDuple point2, Polynomial polynomial, BigInteger modulo)
    {
        BigInteger s;

        if (point1.equals(point2)) {
            s = getSForDouble(point1, polynomial, modulo);
        } else {
            s = getS(point1, point2, polynomial, modulo);
        }

        BigInteger newX = s.pow(2).mod(modulo).subtract(point1.x).mod(modulo).subtract(point2.x).mod(modulo);
        BigInteger newY = s.multiply(point1.x.subtract(newX).mod(modulo)).mod(modulo).subtract(point1.y).mod(modulo);
        return new BigIntDuple(newX, newY);
    }

    static BigInteger getS(BigIntDuple point1, BigIntDuple point2, Polynomial polynomial, BigInteger modulo)
    {
        return point2.y.subtract(point1.y)
                .mod(modulo)
                .multiply(point2.x.subtract(point1.x).mod(modulo).modInverse(modulo))
                .mod(modulo);
    }

    static BigInteger getSForDouble(BigIntDuple point1, Polynomial polynomial, BigInteger modulo)
    {
        return valueOf(3).multiply(point1.x.pow(2))
                .mod(modulo)
                .add(valueOf(polynomial.xCoefficient()))
                .mod(modulo)
                .multiply(valueOf(2).multiply(point1.y).mod(modulo).modInverse(modulo))
                .mod(modulo);
    }

    static int getOrderPoint(BigIntDuple point, Polynomial polynomial, BigInteger modulo)
    {
        int order = 1;
        BigIntDuple sum = point;

        do {
            System.out.printf("(%d,%d)%n", sum.x, sum.y);
            sum = sum.sumPoint(point, polynomial, modulo);
            ++order;
        } while (!sum.x.equals(point.x) || !sum.y.equals(point.y.negate().mod(modulo)));
        System.out.printf("(%d,%d)%n", sum.x, sum.y);
        return ++order;
    }

    static BigIntDuple fastMultiplication(BigIntDuple point, Polynomial polynomial, BigInteger modulo, BigInteger factor)
    {
        String bitString = factor.toString(2);
        BigIntDuple multiplication = point;
        for (int i = 1; i < bitString.length(); i++) {
            multiplication = multiplication.sumPoint(multiplication, polynomial, modulo);
            if (bitString.charAt(i) == '1') {
                multiplication = multiplication.sumPoint(point, polynomial, modulo);
            }
        }
        return multiplication;
    }
}
