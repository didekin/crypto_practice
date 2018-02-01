package com.didekin.tutor.numerical;

/**
 * User: pedro@didekin
 * Date: 10/12/2017
 * Time: 14:38
 */
public class EuclidAlg {

    final int greatest;
    final int least;

    public EuclidAlg(int greatest, int least)
    {
        this.greatest = greatest;
        this.least = least;
    }

    int getHighestCommonDivisor()
    {
        if (least > greatest){
            throw new IllegalArgumentException("Wrong magnitudes");
        }

        int remainder, dividend, divisor;
        dividend = greatest;
        divisor = least;
        do {
            remainder = dividend % divisor;
            dividend = divisor;
            divisor = remainder > 0 ? remainder : divisor;
        }
        while (remainder > 0);
        return  divisor;
    }

    public static void main(String[] args)
    {
        EuclidAlg instance = new EuclidAlg(355, 113);
        System.out.printf("HCF of %d and %d is: %d%n", instance.greatest, instance.least, instance.getHighestCommonDivisor());
    }
}
