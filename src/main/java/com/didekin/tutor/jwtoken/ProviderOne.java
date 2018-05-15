package com.didekin.tutor.jwtoken;

import java.security.Provider;

/**
 * User: pedro@didekin
 * Date: 26/04/2018
 * Time: 11:36
 */
class ProviderOne {

    static {
        // SunJSSE provider.
        java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
    }

    private final Provider secProvider;

    ProviderOne()
    {
        this.secProvider = new com.sun.net.ssl.internal.ssl.Provider();
    }

    Provider getSecProvider()
    {
        return secProvider;
    }
}
