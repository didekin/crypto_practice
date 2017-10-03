package com.didekin.tutor.crypto.api;

import java.math.BigInteger;

import static com.didekin.tutor.crypto.computation.EllipticCurveSum.sumPoints;

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
        return sumPoints(this, point2, polynomial, modulo);
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
