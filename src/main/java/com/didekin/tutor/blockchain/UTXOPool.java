package com.didekin.tutor.blockchain;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * com.didekin.tutor.blockchain.UTXOPool class represents the current set of outstanding UTXOs and contains a map from each com.didekin.tutor.blockchain.UTXO to its corresponding
 * transaction output. This class contains constructors to create a new empty com.didekin.tutor.blockchain.UTXOPool or a copy of a given com.didekin.tutor.blockchain.UTXOPool, and
 * methods to add and remove UTXOs from the pool, get the output corresponding to a given com.didekin.tutor.blockchain.UTXO, check if a com.didekin.tutor.blockchain.UTXO is in the pool,
 * and get a list of all UTXOs in the pool.
 */
public class UTXOPool {

    /**
     * The current collection of UTXOs, with each one mapped to its corresponding transaction output
     */
    private HashMap<UTXO, Transaction.Output> uXTOmapToTxOutput;

    /**
     * Creates a new empty com.didekin.tutor.blockchain.UTXOPool
     */
    public UTXOPool()
    {
        uXTOmapToTxOutput = new HashMap<>();
    }

    /**
     * Creates a new com.didekin.tutor.blockchain.UTXOPool that is a copy of {@code uPool}
     */
    UTXOPool(UTXOPool uPool)
    {
        uXTOmapToTxOutput = new HashMap<>(uPool.uXTOmapToTxOutput);
    }

    /**
     * Adds a mapping from com.didekin.tutor.blockchain.UTXO {@code utxo} to transaction output @code{txOut} to the pool
     */
    public void addUTXO(UTXO utxo, Transaction.Output txOut)
    {
        uXTOmapToTxOutput.put(utxo, txOut);
    }

    /**
     * Removes the com.didekin.tutor.blockchain.UTXO {@code utxo} from the pool
     */
    public void removeUTXO(UTXO utxo)
    {
        uXTOmapToTxOutput.remove(utxo);
    }

    /**
     * @return the transaction output corresponding to com.didekin.tutor.blockchain.UTXO {@code utxo}, or null if {@code utxo} is
     * not in the pool.
     */
    public Transaction.Output getTxOutput(UTXO ut)
    {
        return uXTOmapToTxOutput.get(ut);
    }

    /**
     * @return true if com.didekin.tutor.blockchain.UTXO {@code utxo} is in the pool and false otherwise
     */
    public boolean contains(UTXO utxo)
    {
        return uXTOmapToTxOutput.containsKey(utxo);
    }

    /**
     * Returns an {@code ArrayList} of all UTXOs in the pool
     */
    public ArrayList<UTXO> getAllUTXO()
    {
        return new ArrayList<>(uXTOmapToTxOutput.keySet());
    }
}
