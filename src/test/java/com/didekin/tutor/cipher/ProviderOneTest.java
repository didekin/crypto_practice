package com.didekin.tutor.cipher;

import org.junit.Before;
import org.junit.Test;

import java.security.Provider;
import java.util.List;
import java.util.Set;

import static java.security.Security.getProviders;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertThat;

/**
 * User: pedro@didekin
 * Date: 26/04/2018
 * Time: 11:46
 */
public class ProviderOneTest {

    private ProviderOne providerOne;
    private List<Provider> installedProvs;

    @Before
    public void setUp()
    {
        providerOne = new ProviderOne();
        installedProvs = asList(getProviders());
        assertThat(installedProvs, hasItem(hasProperty("name", is("BC"))));

        for (Provider provider : installedProvs) {
            System.out.printf("%s:  %s%n", provider.getName(), provider.getInfo());
        }
    }

    @Test
    public void testServices()
    {
        Set<Object> services = providerOne.getSecProvider().keySet();
        String key;
        String name;
        for (Object service : services) {
            key = (String) service;
            // an alias key refers to another key
            if (key.startsWith("Alg.Alias")) {
                key = key.substring("Alg.Alias".length() + 1);
            }
            name = key.substring(key.substring(0, key.indexOf('.')).length() + 1);
            System.out.printf("Service: %s%n",(key.substring(0, key.indexOf('.')) + ": " + name));
        }
    }
}