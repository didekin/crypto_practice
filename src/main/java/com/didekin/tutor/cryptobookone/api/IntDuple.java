package com.didekin.tutor.cryptobookone.api;

/**
 * User: pedro@didekin
 * Date: 22/09/2017
 * Time: 11:59
 */
public class IntDuple {

    private final int x, y;

    public IntDuple(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof IntDuple) {
            return false;
        }
        IntDuple outDuple = IntDuple.class.cast(obj);
        return x == outDuple.x && y == outDuple.y;
    }

    @Override
    public int hashCode()
    {
        return 31 * x + y;
    }
}
