package com.didekin.tutor.misc;

/**
 * User: pedro@didekin
 * Date: 30/11/2017
 * Time: 10:54
 */
public class ClassName {

    public static void main(String[] args)
    {
        System.out.printf("Simple name: %s%n", ClassName.class.getSimpleName());
        System.out.printf("Canonical name: %s%n", ClassName.class.getCanonicalName());
        System.out.printf("Name name: %s%n", ClassName.class.getName());
    }
}
