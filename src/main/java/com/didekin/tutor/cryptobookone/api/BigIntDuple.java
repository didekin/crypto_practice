package com.didekin.tutor.cryptobookone.api;

import com.didekin.tutor.cryptobookone.computation.EllipticCurveSum;

import java.math.BigInteger;

/**
 * User: pedro
 * Date: 20/07/16
 * Time: 18:50
 */
public class BigIntDuple {

    public final BigInteger x, y;

    public BigIntDuple(BigInteger x, BigInteger y)
    {
        this.x = x;
        this.y = y;
    }

    public BigIntDuple sumPoint(BigIntDuple point2, Polynomial polynomial, BigInteger modulo){
        return EllipticCurveSum.sumPoints(this, point2, polynomial, modulo);
    }

    @SuppressWarnings("SuspiciousNameCombination")
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof BigIntDuple){
            return false;
        }
        BigIntDuple intDuple = (BigIntDuple) obj;
        return x.equals(intDuple.x) && y.equals(intDuple.y);
    }

    @Override
    public int hashCode()
    {
        int result;
        result = x.hashCode();
        return 31 * result + y.hashCode();
    }
}
