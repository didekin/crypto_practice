package com.didekin.tutor.crypto.pad;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

/**
 * User: pedro@didekin
 * Date: 30/09/2017
 * Time: 15:09
 */
public class OneTimePad {

    private static String generateHexStringKey(int lengthKey){

         byte[] keyArray = new byte[lengthKey];
         int[] keyArrayInt = new int[keyArray.length];
         new SecureRandom().nextBytes(keyArray);
         for (int i = 0; i < keyArray.length; ++i){
             keyArrayInt[i] = keyArray[i] >= 0 ? keyArray[i]: -keyArray[i];
         }
        return IntStream.of(keyArrayInt).mapToObj(Integer::toHexString).map(String::toUpperCase)
                .map(string -> string.length() == 1 ? "0".concat(string) : string)
                .peek(stringHex -> {
                    if (stringHex.length() != 2){
                        throw new IllegalArgumentException();
                    }
                })
                .collect(joining());
    }

    public static void main(String[] args)
    {
        System.out.printf("KEY: %s%n", generateHexStringKey(12));
    }

}
