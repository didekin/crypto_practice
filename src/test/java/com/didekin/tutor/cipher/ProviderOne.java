package com.didekin.tutor.cipher;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Provider;

/**
 * User: pedro@didekin
 * Date: 26/04/2018
 * Time: 11:36
 */
public class ProviderOne {

    private final Provider secProvider;

    static {
        java.security.Security.addProvider(new BouncyCastleProvider());
    }

    public ProviderOne()
    {
        this.secProvider = new BouncyCastleProvider();
    }

    public Provider getSecProvider()
    {
        return secProvider;
    }
}
