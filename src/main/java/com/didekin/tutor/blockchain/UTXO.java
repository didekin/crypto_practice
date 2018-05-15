package com.didekin.tutor.blockchain;

import java.util.Arrays;

/**
 *  com.didekin.tutor.blockchain.UTXO class represents an unspent transaction output. It contains:
 *  -- the hash of the transaction from which it originates.
 *  -- the outputIndexInTx of the corresponding output within that transaction.
 *  We have included equals, hashCode, and compareTo functions in com.didekin.tutor.blockchain.UTXO
 *  that allow the testing of equality and comparison between two UTXOs
 *  based on their indices and the contents of their txHash arrays.
 */
public class UTXO implements Comparable<UTXO> {

    /** Hash of the transaction from which this com.didekin.tutor.blockchain.UTXO originates */
    private byte[] txHash;

    /** Index of the corresponding output in said transaction */
    private int outputIndexInTx;

    /**
     * Creates a new com.didekin.tutor.blockchain.UTXO corresponding to the output with outputIndexInTx <outputIndexInTx> in the transaction whose
     * hash is {@code txHash}
     */
    UTXO(byte[] txHash, int outPutIndex) {
        this.txHash = Arrays.copyOf(txHash, txHash.length);
        this.outputIndexInTx = outPutIndex;
    }

    /**
     * Compares this com.didekin.tutor.blockchain.UTXO to the one specified by {@code other}, considering them equal if they have
     * {@code txHash} arrays with equal contents and equal {@code outputIndexInTx} values
     */
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (getClass() != other.getClass()) {
            return false;
        }

        UTXO utxo = (UTXO) other;
        byte[] outputTxHash = utxo.txHash;
        int in = utxo.outputIndexInTx;
        if (outputTxHash.length != txHash.length || outputIndexInTx != in)
            return false;
        for (int i = 0; i < outputTxHash.length; i++) {
            if (outputTxHash[i] != txHash[i])
                return false;
        }
        return true;
    }

    /**
     * Simple implementation of a com.didekin.tutor.blockchain.UTXO hashCode that respects equality of UTXOs // (i.e.
     * utxo1.equals(utxo2) => utxo1.hashCode() == utxo2.hashCode())
     */
    public int hashCode() {
        int hash = 1;
        hash = hash * 17 + outputIndexInTx;
        hash = (hash * 31) + Arrays.hashCode(txHash);
        return hash;
    }

    /** Compares this com.didekin.tutor.blockchain.UTXO to the one specified by {@code utxo} */
    public int compareTo(UTXO utxo) {
        byte[] hash = utxo.txHash;
        int in = utxo.outputIndexInTx;
        if (in > outputIndexInTx)
            return -1;
        else if (in < outputIndexInTx)
            return 1;
        else {
            int len1 = txHash.length;
            int len2 = hash.length;
            if (len2 > len1)
                return -1;
            else if (len2 < len1)
                return 1;
            else {
                for (int i = 0; i < len1; i++) {
                    if (hash[i] > txHash[i])
                        return -1;
                    else if (hash[i] < txHash[i])
                        return 1;
                }
                return 0;
            }
        }
    }
}
