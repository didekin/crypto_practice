package com.didekin.tutor.blockchain;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.ArrayList;

import static java.nio.ByteBuffer.allocate;
import static java.util.Arrays.copyOf;

/**
 * A transaction consists of a list of inputs, a list of outputs and a unique ID.
 */
@SuppressWarnings({"unused", "ForLoopReplaceableByForEach"})
public class Transaction {

    /**
     * hash of the transaction, its unique ID.
     */
    private byte[] hash;
    private ArrayList<Input> inputs;
    private ArrayList<Output> outputs;
    public Transaction()
    {
        inputs = new ArrayList<>();
        outputs = new ArrayList<>();
    }
    public Transaction(Transaction tx)
    {
        hash = tx.hash.clone();
        inputs = new ArrayList<>(tx.inputs);
        outputs = new ArrayList<>(tx.outputs);
    }

    public void addInput(byte[] prevTxHash, int outputIndex)
    {
        Input in = new Input(prevTxHash, outputIndex);
        inputs.add(in);
    }

    public void addOutput(double value, PublicKey address)
    {
        Output op = new Output(value, address);
        outputs.add(op);
    }

    public void removeInput(int index)
    {
        inputs.remove(index);
    }

    public void removeInput(UTXO ut)
    {
        for (int i = 0; i < inputs.size(); i++) {
            Input in = inputs.get(i);
            UTXO u = new UTXO(in.prevTxHash, in.outputIndex);
            if (u.equals(ut)) {
                inputs.remove(i);
                return;
            }
        }
    }

    /**
     * Return an array of bytes with the input to be signed and ALL the outputs of the TX containing the input.
     */
    public byte[] getRawDataToSign(int indexInputToBeSigned)
    {
        // ith input and all outputs
        if (indexInputToBeSigned > inputs.size()) {
            return null;
        }

        // List of bytes (one TX input + all TX outputs) to be signed.
        ArrayList<Byte> bytesListToBeSigned = new ArrayList<>();

        // Input to be signed.
        Input inputTxToBeSigned = inputs.get(indexInputToBeSigned);
        // Add prevTxHashInput to the bytes list to be signed.
        byte[] prevTxHashInInput = inputTxToBeSigned.prevTxHash;
        if (prevTxHashInInput != null) {
            for (int i = 0; i < prevTxHashInInput.length; i++)
                bytesListToBeSigned.add(prevTxHashInInput[i]);
        }
        // Add index of output in a previous TX to the bytes list to be signed.
        byte[] outputIndexInInput = allocate(Integer.SIZE / 8).putInt(inputTxToBeSigned.outputIndex).array();
        for (int i = 0; i < outputIndexInInput.length; i++) {
            bytesListToBeSigned.add(outputIndexInInput[i]);
        }

        // ALL outputs of the TX containing the input to be signed.
        for (Output outPutInTx : outputs) {
            // -- Process each of the individual outputs in TX. --
            // Add the value of each output, as an array of bytes, to the bytes list to be signed.
            byte[] outPutValue = allocate(Double.SIZE / 8).putDouble(outPutInTx.value).array();
            for (int i = 0; i < outPutValue.length; i++) {
                bytesListToBeSigned.add(outPutValue[i]);
            }
            // Add the address of each output, as an array of bytes, to the bytes list to be signed.
            byte[] addressBytes = outPutInTx.address.getEncoded();
            for (int i = 0; i < addressBytes.length; i++) {
                bytesListToBeSigned.add(addressBytes[i]);
            }
        }

        // Array of bytes representing the input and outputs to be signed.
        byte[] sigD = new byte[bytesListToBeSigned.size()];
        int i = 0;
        for (Byte sb : bytesListToBeSigned) {
            sigD[i++] = sb;
        }
        return sigD;
    }

    public void addSignature(byte[] signature, int index)
    {
        inputs.get(index).addSignature(signature);
    }

    private byte[] getRawTx()
    {
        ArrayList<Byte> rawTx = new ArrayList<>();
        for (Input in : inputs) {
            // ID of previous TX.
            byte[] prevTxHash = in.prevTxHash;
            ByteBuffer b = allocate(Integer.SIZE / 8);
            // Put in buffer index of output in a previous TX.
            b.putInt(in.outputIndex);
            byte[] outputIndex = b.array();
            byte[] signature = in.signature;
            if (prevTxHash != null)
                for (int i = 0; i < prevTxHash.length; i++)
                    rawTx.add(prevTxHash[i]);
            for (int i = 0; i < outputIndex.length; i++)
                rawTx.add(outputIndex[i]);
            if (signature != null)
                for (int i = 0; i < signature.length; i++)
                    rawTx.add(signature[i]);
        }
        for (Output op : outputs) {
            ByteBuffer b = allocate(Double.SIZE / 8);
            b.putDouble(op.value);
            byte[] value = b.array();
            byte[] addressBytes = op.address.getEncoded();
            for (int i = 0; i < value.length; i++) {
                rawTx.add(value[i]);
            }
            for (int i = 0; i < addressBytes.length; i++) {
                rawTx.add(addressBytes[i]);
            }

        }
        byte[] tx = new byte[rawTx.size()];
        int i = 0;
        for (Byte b : rawTx)
            tx[i++] = b;
        return tx;
    }

    public void finalize()
    {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(getRawTx());
            hash = md.digest();
        } catch (NoSuchAlgorithmException x) {
            x.printStackTrace(System.err);
        }
    }

    public byte[] getHash()
    {
        return hash;
    }

    public void setHash(byte[] h)
    {
        hash = h;
    }

    public ArrayList<Input> getInputs()
    {
        return inputs;
    }

    public ArrayList<Output> getOutputs()
    {
        return outputs;
    }

    public Input getInput(int index)
    {
        if (index < inputs.size()) {
            return inputs.get(index);
        }
        return null;
    }

    public Output getOutput(int index)
    {
        if (index < outputs.size()) {
            return outputs.get(index);
        }
        return null;
    }

    public int numInputs()
    {
        return inputs.size();
    }

    public int numOutputs()
    {
        return outputs.size();
    }

    /**
     * A transaction input consists of:
     * -- the hash of the (previous) transaction that contains the corresponding output.
     * -- the index of this output in that (previous) transaction (indices are simply integers starting from 0).
     * -- a digital signature.
     */
    public class Input {
        /**
         * hash of the com.didekin.tutor.blockchain.Transaction whose output is being claimed or used.
         */
        public byte[] prevTxHash;
        /**
         * used output's index by this input.
         */
        public int outputIndex;
        /**
         * The signature produced to check validity of the input. For the input to be valid, it has to have been signed by the SK associated to the PK in the output claimed by the input.
         * The signature, therefore, must validated over the current transaction with the public key in the spent/claimed output, which is the PK of the owner of this input.
         */
        public byte[] signature;

        Input(byte[] prevHash, int index)
        {
            if (prevHash == null)
                prevTxHash = null;
            else
                prevTxHash = copyOf(prevHash, prevHash.length);
            outputIndex = index;
        }

        void addSignature(byte[] sig)
        {
            if (sig == null)
                signature = null;
            else {
                signature = copyOf(sig, sig.length);
            }
        }
    }

    /**
     * A transaction output consists of: a value and a public key to which it is being paid.
     */
    public class Output {
        /**
         * value in bitcoins of the output
         */
        public double value;
        /**
         * the address or public key of the recipient. This is the PK corresponding to the SK used to sign the input which claimed this output.
         * The recipient of this output, owner of the output and this PK, signed with his SK over the TX with the input which claims/spends this output and the outputs which result of the spenditure of the input.
         */
        public PublicKey address;

        Output(double v, PublicKey addr)
        {
            value = v;
            address = addr;
        }
    }
}
