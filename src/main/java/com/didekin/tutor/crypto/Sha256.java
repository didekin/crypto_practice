package com.didekin.tutor.crypto;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * User: pedro@didekin
 * Date: 13/10/16
 * Time: 11:41
 */
public class Sha256 {
    public static void main(String[] args) throws NoSuchAlgorithmException
    {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        String rsaFingerPrint = "18:3d:ca:cf:6a:85:ea:d1:cf:73:14:9c:ac:a5:03:94";
        String dsaFingerPrint = "3b:27:37:c8:f2:92:47:8b:3b:59:6f:c7:0c:15:3f:1a";
//        String ecdsaFingerPrint = "TV5V1okK9ln9SX1Ji55RfeyDonQ+lpbV2aOcWjw3Hgw";
//        String ecdsaFingerPrint = "f9:58:ae:d5:92:78:47:45:d8:06:d7:e8:ac:64:d3:c2";
        String ecdsaFingerPrint = "AAAAE2VjZHNhLXNoYTItbmlzdHAyNTYAAAAIbmlzdHAyNTYAAABBBN7slji8EEhbsP33ujvsq+AXkQx5h5oiASk0OAPGdpqbBCwuM8cT1yFHTUrgT11Ti5zOoTDUcMX+WKntMjVDv2A=";
        byte[] hash = digest.digest(rsaFingerPrint.getBytes(StandardCharsets.UTF_8));
        System.out.printf("RSA Sha256: %s%n", hash.toString());
        hash = digest.digest(dsaFingerPrint.getBytes(StandardCharsets.UTF_8));
        System.out.printf("DSA Sha256: %s%n", hash);
        hash = digest.digest(ecdsaFingerPrint.getBytes(StandardCharsets.UTF_8));
        System.out.printf("ECDSA Sha256: %s%n", hash);
    }
}
