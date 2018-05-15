package com.didekin.tutor.cryptobookone.computation;

import java.util.Arrays;

/**
 * User: pedro@didekin
 * Date: 29/03/16
 * Time: 10:15
 */
public class DecryptAffine {

    static final String abecedarioStr = "abcdefghijklmnopqrstuvwxyz";
    static final char[] abecedario = abecedarioStr.toCharArray();
    static final int modulus = abecedario.length;

    final int inverseShift;
    final int jump;

    public DecryptAffine(int inverseShift, int jump)
    {
        this.inverseShift = inverseShift;
        this.jump = jump;
    }

    String decrypt(String encryptedStr)
    {
        char[] decryptedChars = new char[encryptedStr.length()];
        char charInProcess;
        int encryptPosition;
        int decrypPosition;
        char decryptedChar;

        for (int i = 0; i < encryptedStr.length(); ++i) {
            charInProcess = encryptedStr.charAt(i);
            encryptPosition = Arrays.binarySearch(abecedario, encryptedStr.charAt(i));
            decrypPosition = (inverseShift * (encryptPosition - jump)) % modulus;
            decrypPosition = decrypPosition >= 0 ? decrypPosition : decrypPosition + modulus;
            decryptedChar = abecedario[decrypPosition];
            decryptedChars[i] = decryptedChar;
            System.out.printf("%s", charInProcess);
        }
        String decryptedStr = new String(decryptedChars);
        System.out.printf("%n%s", decryptedStr);
        return decryptedStr;
    }
}
