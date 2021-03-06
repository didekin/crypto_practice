package com.didekin.tutor.cryptobookone.computation;

/**
 * User: pedro
 * Date: 09/07/16
 * Time: 17:02
 */
public class EuclidesOne {

    public static void main(String[] args)
    {
        System.out.printf("gcd = %d%n", new EuclidesOne().gcd(49, 640));
    }

    int gcd(int r0, int r1)
    {
        int divd = r0;
        int divs = r1;
        int remainder;
        do {
            remainder = divd % divs;
            divd = divs;
            divs = remainder;
        } while (remainder > 0);
        return divd;
    }
}
