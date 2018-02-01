package com.didekin.tutor.numerical;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * User: pedro@didekin
 * Date: 10/12/2017
 * Time: 14:54
 */
public class EuclidAlgTest {

    @Test
    public void test_GetHighestCommonDivisor()
    {
        EuclidAlg instance = new EuclidAlg(42, 12);
        assertThat(instance.getHighestCommonDivisor(), is(6));
        instance = new EuclidAlg(7200, 3132);
        assertThat(instance.getHighestCommonDivisor(), is(36));
    }
}