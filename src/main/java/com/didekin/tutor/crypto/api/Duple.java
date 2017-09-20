package com.didekin.tutor.crypto.api;

import java.math.BigInteger;

import static com.didekin.tutor.crypto.computation.EllipticCurveSum.sumPoints;

/**
 * User: pedro
 * Date: 20/07/16
 * Time: 18:50
 */
public class Duple {

    public final BigInteger x, y;

    public Duple(BigInteger x, BigInteger y)
    {
        this.x = x;
        this.y = y;
    }

    public Duple sumPoint(Duple point2, Polynomial polynomial, BigInteger modulo){
        return sumPoints(this, point2, polynomial, modulo);
    }

    @Override
    public boolean equals(Object obj)
    {
        Duple dupleIn = (Duple) obj;
        return x.equals(dupleIn.x) && y.equals(dupleIn.y);
    }

    @Override
    public int hashCode()
    {
        int result;
        result = x.hashCode();
        return 31 * result + y.hashCode();
    }
}
