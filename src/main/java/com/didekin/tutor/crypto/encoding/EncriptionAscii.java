package com.didekin.tutor.crypto.encoding;

import java.nio.charset.StandardCharsets;

import static java.lang.Character.getNumericValue;

/**
 * User: pedro@didekin
 * Date: 19/08/17
 * Time: 18:57
 */
public class EncriptionAscii {

    static final String plaintAsciiText_1 = "attack at dawn";
    static final String encryptedHexText_1 = "6c73d5240a948c86981bc294814d";
    private static final String binaryKey = xOrFromBinary(getBitsFromHexString(encryptedHexText_1), getBitsFromAsciiString(plaintAsciiText_1));

    static String getBitsFromHexString(String hexStr)
    {
        StringBuilder stringBuilder = new StringBuilder(hexStr.length() * 8);
        String numberStr;
        String binaryStr;
        int integerFromHex;

        for (int i = 0; i < hexStr.length(); i += 2) {
            numberStr = hexStr.substring(i, i + 2);
            integerFromHex = Integer.parseInt(numberStr, 16);
            getBitsFromInt(stringBuilder,integerFromHex);
        }

        return stringBuilder.toString();
    }

    static String getBitsFromAsciiString(String asciiStr)
    {
        byte[] bytesFromAscii = asciiStr.getBytes(StandardCharsets.US_ASCII);
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : bytesFromAscii) {
            int value = b;
            getBitsFromInt(stringBuilder, value);
        }
        return stringBuilder.toString();
    }

    static void getBitsFromInt(StringBuilder stringBuilder, int value)
    {
        for (int i = 0; i < 8; i++) {
            stringBuilder.append((value & 128) == 0 ? 0 : 1);
            value <<= 1;
        }
    }

    static String xOrFromBinary(String bitsStr, String bitsKey)
    {
        assert bitsKey.length() == bitsStr.length();

        StringBuilder stringBuilder = new StringBuilder();
        char[] bitsChar = bitsStr.toCharArray();
        char[] keyChar = bitsKey.toCharArray();
        for (int i = 0; i < bitsKey.length(); ++i) {
            stringBuilder.append(getNumericValue(bitsChar[i]) ^ getNumericValue(keyChar[i]));
        }
        return stringBuilder.toString();
    }

    static String xOrFromHexString(String hexStr1, String hexStr2)
    {
        String xorStrBinary = xOrFromBinary(getBitsFromHexString(hexStr1), getBitsFromHexString(hexStr2));
        return fromBinaryToHexInt(xorStrBinary);
    }

    static String toHexFromAscii(String asciiStr)
    {
        String encryptedBinaryStr = xOrFromBinary(getBitsFromAsciiString(asciiStr), binaryKey);
        return fromBinaryToHexInt(encryptedBinaryStr);
    }

    static String fromBinaryToHexInt(String binaryStr)
    {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < binaryStr.length(); i+=8){
            String eightBitsStr = binaryStr.substring(i, i + 8);
            int value = 0;
            for(int j = 7; j >= 0; --j){
                value += (eightBitsStr.charAt(j) == '1' ? (1 << (7 - j)) : 0);  // Proceed from right to left. 7-j updates the number of positions to shift.
            }
            if(value < 15){
                stringBuilder.append("0");
            }
            stringBuilder.append(Integer.toHexString(value));
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args)
    {
        String oneStr = "315c4eeaa8b5f8aaf9174145bf43e1784b8fa00dc71d885a804e5ee9fa40b16349c146fb778cdf2d3aff021dfff5b403b510d0d0455468aeb98622b137dae857553ccd8883a7bc37520e06e515d22c954eba5025b8cc57ee59418ce7dc6bc41556bdb36bbca3e8774301fbcaa3b83b220809560987815f65286764703de0f3d524400a19b159610b11ef3e";
        String twoStr = "234c02ecbbfbafa3ed18510abd11fa724fcda2018a1a8342cf064bbde548b12b07df44ba7191d9606ef4081ffde5ad46a5069d9f7f543bedb9c861bf29c7e205132eda9382b0bc2c5c4b45f919cf3a9f1cb74151f6d551f4480c82b2cb24cc5b028aa76eb7b4ab24171ab3cdadb8356f";
        System.out.printf("xOr 1 = %s%n", xOrFromHexString(oneStr, twoStr));

    }
}

