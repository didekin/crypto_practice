package com.didekin.tutor.cryptobookone.computation;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * User: pedro@didekin
 * Date: 29/03/16
 * Time: 10:28
 */
public class DecryptAffineTest {

    @Test
    public void sortAbecedario()
    {
        final char[] beforeAbcd = DecryptAffine.abecedario;
        Arrays.sort(DecryptAffine.abecedario);
        assertThat(new String(DecryptAffine.abecedario), is(new String(beforeAbcd)));
    }

    @Test
    public void exercise_1_11()
    {
        DecryptAffine decryptor = new DecryptAffine(15, 22);
        String decrypted = decryptor.decrypt("falszztysyjzyjkywjrztyjztyynaryjkyswarztyegyyj");
        assertThat(decrypted.length() > 0, is(true));
    }
}