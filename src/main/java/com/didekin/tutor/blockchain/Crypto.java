package com.didekin.tutor.blockchain;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

class Crypto {

    /**
     * This method takes a public key, a message and a signature, and returns true if and only signature
     * correctly verifies over message with the public key pubKey.
     *
     * @return true is {@code signature} is a valid digital signature of {@code message} under the
     *         key {@code pubKey}. Internally, this uses RSA signature, but the student does not
     *         have to deal with any of the implementation details of the specific signature
     *         algorithm
     */
    static boolean verifySignature(PublicKey pubKey, byte[] message, byte[] signature) {
        Signature sig = null;
        try {
            sig = Signature.getInstance("SHA256withRSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            sig.initVerify(pubKey);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        try {
            sig.update(message);
            return sig.verify(signature);
        } catch (SignatureException e) {
            e.printStackTrace();
        }
        return false;

    }
}
