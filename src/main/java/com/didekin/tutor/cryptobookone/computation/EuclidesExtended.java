package com.didekin.tutor.cryptobookone.computation;

/**
 * User: pedro
 * Date: 09/07/16
 * Time: 18:38
 */
public class EuclidesExtended {

    Result gcd(int r0, int r1)
    {
        int divd = r0;
        int divs = r1;
        int remainder;
        int quotient;
        int s0 = 1;
        int t0 = 0;
        int s1 = 0;
        int t1 = 1;
        int s2, t2;

        while ((remainder = divd % divs) > 0) {
            quotient = divd / divs;
            s2 = s0 - quotient * s1;
            t2 = t0 - quotient * t1;
            divd = divs;
            divs = remainder;
            s0 = s1;
            s1 = s2;
            t0 = t1;
            t1 = t2;
        }
        return new Result(divs, s1, t1);
    }

    class Result {
        int gcd;
        int s;
        int t;

        public Result(int gcd, int s, int t)
        {
            this.gcd = gcd;
            this.s = s;
            this.t = t;
        }
    }

    public static void main(String[] args)
    {
        Result result = new EuclidesExtended().gcd(973, 301);
        System.out.printf("gcd = %d  s = %d  t = %d %n", result.gcd, result.s, result.t);
    }

}
