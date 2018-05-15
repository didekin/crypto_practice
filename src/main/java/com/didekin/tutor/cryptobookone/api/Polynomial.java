package com.didekin.tutor.cryptobookone.api;

/**
 * User: pedro
 * Date: 20/07/16
 * Time: 18:51
 */
public interface Polynomial {
    int check(BigIntDuple point);

    int xCoefficient();

    int bCoefficient();
}
