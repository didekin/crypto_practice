package com.didekin.tutor.crypto.paarpelzlbook;

import com.didekin.tutor.crypto.api.Duple;
import com.didekin.tutor.crypto.api.Polynomial;

import static java.math.BigInteger.valueOf;

/**
 * User: pedro
 * Date: 20/07/16
 * Time: 19:03
 */
class PolynomialOne implements Polynomial {

    final int xCoefficient;
    final int bCoefficient;

    public PolynomialOne(int xCoefficient, int bCoefficient)
    {
        this.xCoefficient = xCoefficient;
        this.bCoefficient = bCoefficient;
    }

    @Override
    public int check(Duple point)
    {
        final int modulo = 7;
        return point.y.pow(2).subtract(point.x.pow(3)).subtract(point.x.multiply(valueOf(xCoefficient))).subtract
                (valueOf(bCoefficient)).mod(valueOf(modulo)).intValue();
    }

    @Override
    public int xCoefficient()
    {
        return xCoefficient;
    }

    @Override
    public int bCoefficient()
    {
        return bCoefficient;
    }
}
