package com.didekin.tutor.crypto.api;

/**
 * User: pedro
 * Date: 20/07/16
 * Time: 18:51
 */
public interface Polynomial {
    int check(Duple point);
    int xCoefficient();
    int bCoefficient();
}
