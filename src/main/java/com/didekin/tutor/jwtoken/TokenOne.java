package com.didekin.tutor.jwtoken;

import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwe.ContentEncryptionAlgorithmIdentifiers;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwe.KeyManagementAlgorithmIdentifiers;
import org.jose4j.keys.AesKey;
import org.jose4j.lang.ByteUtil;
import org.jose4j.lang.JoseException;

import java.security.Key;

/**
 * User: pedro@didekin
 * Date: 27/04/2018
 * Time: 15:09
 */
public class TokenOne {

    public static void main(String[] args)
    {
        Key key = new AesKey(ByteUtil.randomBytes(16));
        JsonWebEncryption jwe = new JsonWebEncryption();
        jwe.setPayload("Hello World!");
        jwe.setAlgorithmHeaderValue(KeyManagementAlgorithmIdentifiers.A128KW);
        jwe.setEncryptionMethodHeaderParameter(ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256);
        jwe.setKey(key);
        String serializedJwe = null;
        try {
            serializedJwe = jwe.getCompactSerialization();
        } catch (JoseException e) {
            e.printStackTrace();
        }
        System.out.println("Serialized Encrypted JWE: " + serializedJwe);
        jwe = new JsonWebEncryption();
        jwe.setAlgorithmConstraints(new AlgorithmConstraints(AlgorithmConstraints.ConstraintType.WHITELIST,
                KeyManagementAlgorithmIdentifiers.A128KW));
        jwe.setContentEncryptionAlgorithmConstraints(new AlgorithmConstraints(AlgorithmConstraints.ConstraintType.WHITELIST,
                ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256));
        jwe.setKey(key);
        try {
            jwe.setCompactSerialization(serializedJwe);
            System.out.println("Payload: " + jwe.getPayload());
        } catch (JoseException e) {
            e.printStackTrace();
        }
    }
}
