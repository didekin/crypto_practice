package com.didekin.tutor.numerical;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static java.lang.StrictMath.sqrt;
import static java.util.Comparator.naturalOrder;

/**
 * User: pedro@didekin
 * Date: 01/02/2018
 * Time: 11:02
 */
public class CyclicGroupOne {

    private final BigInteger moduloGroupBig;
    private final BigInteger orderGroupBig;
    private final List<BigInteger> units;
    private final List<BigInteger> orderGroupDivisors;

    private CyclicGroupOne(int moduloGroup)
    {
        moduloGroupBig = new BigInteger(Integer.toString(moduloGroup, 10));
        units = getUnits(moduloGroup);
        orderGroupBig = new BigInteger(Integer.toString(units.size(), 10));
        orderGroupDivisors = getOrderDivisors();
    }

    public static void main(String[] args)
    {
        CyclicGroupOne groupOne = new CyclicGroupOne(28);
        System.out.printf("%d units:%n", groupOne.units.size());
        groupOne.units.forEach(System.out::println);
        System.out.printf("%d divisors:%n", groupOne.orderGroupDivisors.size());
        groupOne.orderGroupDivisors.forEach(System.out::println);

        List<BigInteger> generators = groupOne.getGenerators();
        System.out.printf("%d generators:%n", generators.size());
        generators.forEach(System.out::println);
    }

    private List<BigInteger> getUnits(int moduloGroup)
    {
        List<BigInteger> units = new ArrayList<>(moduloGroup);
        BigInteger element;
        for (int i = 0; i < moduloGroup; ++i) {
            element = new BigInteger(Integer.toString(i, 10));
            if (element.gcd(moduloGroupBig).equals(BigInteger.ONE)) {
                units.add(element);
            }
        }
        return units;
    }

    private List<BigInteger> getOrderDivisors()
    {
        List<BigInteger> divisors = new ArrayList<>();
        int orderGroup = orderGroupBig.intValue();
        int maxDivisor = (int) sqrt(orderGroup);
        for (int i = 1; i <= maxDivisor; ++i) {
            if (orderGroup % i == 0) {
                divisors.add(new BigInteger(Integer.toString(i, 10)));
                divisors.add(new BigInteger(Integer.toString(orderGroup / i)));
            }
        }
        divisors.sort(naturalOrder());
        return divisors;
    }

    private List<BigInteger> getGenerators()
    {
        List<BigInteger> generators = new ArrayList<>();

        for (BigInteger unit : units) {
            for (BigInteger divisor : orderGroupDivisors) {
                if (unit.modPow(divisor, moduloGroupBig).equals(BigInteger.ONE)
                        && !divisor.equals(orderGroupBig)) {
                    break;
                }
                if (divisor.equals(orderGroupBig)) {
                    generators.add(unit);
                }
            }
        }
        return generators;
    }
}
