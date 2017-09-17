package com.didekin.tutor.crypto.paarpelzlbook;

import com.didekin.tutor.crypto.api.Duple;
import com.didekin.tutor.crypto.api.Polynomial;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.pow;
import static java.math.BigInteger.ZERO;
import static java.math.BigInteger.valueOf;
import static com.didekin.tutor.crypto.paarpelzlbook.EllipticCurveSum.getOrderPoint;

/**
 * User: pedro
 * Date: 20/07/16
 * Time: 10:21
 */
public class CheckEllipticCurve {

    private static List<Duple> getPoints(int modulo)
    {
        List<Duple> pares = new ArrayList<>((int) pow(modulo, 2));
        for (int i = 0; i < modulo; i++) {
            for (int j = 0; j < modulo; j++) {
                pares.add(new Duple(valueOf(i), valueOf(j)));
            }
        }
        return pares;
    }

    private static void getPointsInPolynomial(Polynomial polynomial, List<Duple> pares, BigInteger modulo)
    {
        List<Duple> checkedPoints = new ArrayList<>();
        for (Duple par : pares) {
            if (polynomial.check(par) == 0) {
                checkedPoints.add(par);
                Duple inversePair = new Duple(par.x, par.y.negate().mod(modulo));
                System.out.printf("(%d,%d) - inverse (%d, %d) %n", par.x, par.y, inversePair.x, inversePair.y);
            }
        }
        System.out.printf("Order of group or cardinality = %d%n", checkedPoints.size() + 1);
    }

    public static void main(String[] args)
    {
        BigInteger modulo = valueOf(7);
        List<Duple> pares = getPoints(modulo.intValue());
        Polynomial polynomial = new PolynomialOne(3, 2);
        getPointsInPolynomial(polynomial, pares, modulo);
        System.out.printf("Order (0,3): %d%n", getOrderPoint(new Duple(ZERO, valueOf(3)), polynomial, modulo));
    }

}
