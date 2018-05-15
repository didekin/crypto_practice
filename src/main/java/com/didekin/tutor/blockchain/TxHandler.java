package com.didekin.tutor.blockchain;

import java.util.ArrayList;
import java.util.HashSet;

import static java.util.Arrays.copyOf;

@SuppressWarnings({"WeakerAccess", "unused"})
public class TxHandler {

    private final UTXOPool utxoPool;

    /**
     * Creates a public ledger whose current com.didekin.tutor.blockchain.UTXOPool (collection of unspent transaction outputs) is
     * {@code utxoPool}. This should make a copy of utxoPool by using the com.didekin.tutor.blockchain.UTXOPool(com.didekin.tutor.blockchain.UTXOPool uPool)
     * constructor.
     */
    public TxHandler(UTXOPool up)
    {
        utxoPool = new UTXOPool(up);
    }


    /**
     * Returns true if
     * INPUTS:
     * (1) no com.didekin.tutor.blockchain.UTXO is claimed multiple times by tx
     * (2) all outputs claimed by tx are in the current com.didekin.tutor.blockchain.UTXO pool
     * (3) the signatures on each input of tx are valid. The raw data that are used to verify the signature are obtained from the getRawDataToSign(int index) method.
     * OUTPUTS:
     * (4) all of tXs output values are non-negative
     * INPUTS/OUTPUTS:
     * (5) the sum of tXs input values is greater than or equal to the sum of its output values.
     * <p>
     * // assuming all UTXOs required by this transaction will be in utxo pool
     * <p>
     * To verify a signature, you will use the verifySignature() method included in the provided file com.didekin.tutor.blockchain.Crypto.java
     */

    public boolean isValidTx(Transaction txToValidate)
    {
        // VALIDATION of inputs.
        HashSet<UTXO> utxosSeen = new HashSet<>();
        double totalOutputClaimByInputsInTX = 0;
        for (int i = 0; i < txToValidate.getInputs().size(); i++) {
            Transaction.Input eachInputInTx = txToValidate.getInput(i);
            UTXO uTXOforInputInTx = new UTXO(eachInputInTx.prevTxHash, eachInputInTx.outputIndex);
            // Check for duplicates UTXOs in the inputs of the TX.
            if (!utxosSeen.add(uTXOforInputInTx)) {
                return false;
            }
            // If no duplicates, check the output claimed by the input is in the UTXOPool associated to TX.
            Transaction.Output outPutClaimByInput = utxoPool.getTxOutput(uTXOforInputInTx);
            if (outPutClaimByInput == null)
                return false;
            // Check signature of the input.
            if (!Crypto.verifySignature(outPutClaimByInput.address, txToValidate.getRawDataToSign(i), eachInputInTx.signature)) {
                return false;
            }
            // If every check for the input is OK, add the value of the output claimed by the input to the total output value accumulated for the TX.
            totalOutputClaimByInputsInTX += outPutClaimByInput.value;
        }

        // VALIDATION of outPuts.
        double totalOutputInTx = 0;
        ArrayList<Transaction.Output> txOutputs = txToValidate.getOutputs();
        for (Transaction.Output eachOutputInTx : txOutputs) {
            // Check every output is >= 0.
            if (eachOutputInTx.value < 0) {
                return false;
            }
            totalOutputInTx += eachOutputInTx.value;
        }

        // VALIDATION of invariant total inputs value >= total output value. If the difference > 0, its value is what is called 'transaction fee'.
        return (totalOutputClaimByInputsInTX >= totalOutputInTx);
    }

    private boolean inPool(Transaction tx)
    {
        ArrayList<Transaction.Input> allInputsTx = tx.getInputs();
        // Input whose output (outPutIndex) is to be ckecked in UTXOPool.
        for (Transaction.Input inpuntToBeChecked : allInputsTx) {
            if (!utxoPool.contains(new UTXO(inpuntToBeChecked.prevTxHash, inpuntToBeChecked.outputIndex)))
                return false;
        }
        return true;
    }

    private void updatePool(Transaction tx)
    {
        for (int i = 0; i < tx.getInputs().size(); i++) {
            // Outputs claimed/spent in input of TX is considered spent and removed from com.didekin.tutor.blockchain.UTXOPool.
            utxoPool.removeUTXO(new UTXO(tx.getInput(i).prevTxHash, tx.getInput(i).outputIndex));
        }
        for (int i = 0; i < tx.getOutputs().size(); i++) {
            // Outputs in TX are considered unspent and their values are added to com.didekin.tutor.blockchain.UTXOPool.
            // Total input value (output claimed/spent) - total outputs values is NOT considered unspent value but transaction fee.
            utxoPool.addUTXO(new UTXO(tx.getHash(), i), tx.getOutput(i));
        }
    }

    /**
     * Handles each epoch by receiving an unordered array of proposed transactions, checking each
     * transaction for correctness, returning a mutually valid array of accepted transactions, and
     * updating the current com.didekin.tutor.blockchain.UTXO pool as appropriate.
     * <p>
     * Based on the transactions it has chosen to accept, handleTxs() should also update its internal com.didekin.tutor.blockchain.UTXOPool to reflect
     * the current set of unspent transaction outputs, so that future calls to handleTxs() and isValidTx() are able to
     * correctly process/validate transactions that claim outputs from transactions that were accepted in a previous call to handleTxs().
     */
    public Transaction[] handleTxs(Transaction[] allTxToBeHandledArr)
    {
        // Defensive copy of the original TXs in the array used in each handling round.
        Transaction[] poolTXsToBeHandled = copyOf(allTxToBeHandledArr, allTxToBeHandledArr.length);

        // Initialization of the array of transactions successfully handled in successive rounds.
        Transaction[] successfullHandledTxs = new Transaction[poolTXsToBeHandled.length];
        // Array for transactions remaining, after each round, for futher handling rounds.
        Transaction[] txWithoutUTXOArr;
        // Number of transactions without a UXTO counterpart after eacha processing round, pending for further handling.
        int tXwithoutUXTOCounterInRound;
        // Counter for TXs successfully handled along the series of processing rounds.
        int updatedSuccessCounterAfterRound = 0;

        while (true) {
            boolean change = false;
            txWithoutUTXOArr = new Transaction[poolTXsToBeHandled.length];
            tXwithoutUXTOCounterInRound = 0;
            // Handle each transaction in the TXs array passed to the method.
            for (Transaction tXsToBeHandled : poolTXsToBeHandled) {
                // Check if there is a com.didekin.tutor.blockchain.UTXO in the ledger (com.didekin.tutor.blockchain.UTXOPool) associated to the output of each of the inputs in the TX.
                if (inPool(tXsToBeHandled)) {
                    // If there are the necessary com.didekin.tutor.blockchain.UTXO, check if the TX is valid.
                    if (isValidTx(tXsToBeHandled)) {
                        change = true;
                        // Updates com.didekin.tutor.blockchain.UTXO pool: Remove the UTXOs associated to all the inputs in TX and add one com.didekin.tutor.blockchain.UTXO for each of the outputs in TX.
                        updatePool(tXsToBeHandled);
                        // Copy TX to array of successfully handled TXs and increment counter of successfully handled TXs.
                        successfullHandledTxs[updatedSuccessCounterAfterRound++] = tXsToBeHandled;
                    }
                } else {
                    // TXs without enough UTXOs for their inputs are copied to a temporal array of TXs.
                    txWithoutUTXOArr[tXwithoutUXTOCounterInRound++] = tXsToBeHandled;
                }
            }

            // Once I handled successfully a first bunch of TXs, with matching com.didekin.tutor.blockchain.UTXO in pool, fresh new UXTOs may have been added to the pool, corresponding to the outputs of successful handled TXs.
            // New rounds of handling of unsuccessful handled TXs are done, until 'change = false': not more valid transactions have been handle which may have updated com.didekin.tutor.blockchain.UTXO in the pool.
            if (change) {
                poolTXsToBeHandled = copyOf(txWithoutUTXOArr, txWithoutUTXOArr.length);
            } else {
                break;
            }
        } // END WHILE.

        return copyOf(successfullHandledTxs, successfullHandledTxs.length);
    }
}