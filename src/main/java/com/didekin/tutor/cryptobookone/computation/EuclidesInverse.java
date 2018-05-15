package com.didekin.tutor.cryptobookone.computation;

/**
 * User: pedro
 * Date: 09/07/16
 * Time: 19:05
 */
public class EuclidesInverse {

    public static void main(String[] args)
    {
        int r1 = 49;
        int mod = 640;
        Result result = new EuclidesInverse().gcd(mod, r1);
        System.out.printf("gcd = %d  inverse = %d %n", result.gcd, result.t > 0 ? result.t : result.t + mod);
        int verification = (r1 * result.t) % mod;
        System.out.printf("Verification = %d%n", verification > 0 ? verification : verification + mod);
    }

    Result gcd(int r0, int r1)
    {
        int divd = r0;
        int divs = r1;
        int remainder;
        int quotient;
        int t0 = 0;
        int t1 = 1;
        int t2;

        while ((remainder = divd % divs) > 0) {
            quotient = divd / divs;
            t2 = t0 - quotient * t1;
            divd = divs;
            divs = remainder;
            t0 = t1;
            t1 = t2;
        }
        if (divs == 1) {
            return new Result(divs, t1 % r0);
        } else {
            return new Result(divs, -9999);
        }

    }

    class Result {
        int gcd;
        int s;
        int t;

        public Result(int gcd, int t)
        {
            this.gcd = gcd;
            this.t = t;
        }
    }
}
