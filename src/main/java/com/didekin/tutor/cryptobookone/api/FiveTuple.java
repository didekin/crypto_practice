package com.didekin.tutor.cryptobookone.api;

import java.math.BigInteger;

/**
 * User: pedro
 * Date: 24/07/16
 * Time: 12:38
 */
public class FiveTuple {

    private final BigInteger a, b, c, d, e;

    public FiveTuple(BigInteger a, BigInteger b, BigInteger c, BigInteger d, BigInteger e)
    {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
    }

    @Override
    public String toString()
    {
        return String.format("(%s,%s,%s,%s,%s)%n", a.toString(16),b.toString(16),c.toString(16),
                d.toString(16),e.toString(16));
    }
}
