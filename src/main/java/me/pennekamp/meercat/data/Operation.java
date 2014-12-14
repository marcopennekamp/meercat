package me.pennekamp.meercat.data;

public class Operation extends CountableEntity {

    private int opcode;
    private String mnemonic;

    public Operation (int opcode, String mnemonic) {
        this.opcode = opcode;
        this.mnemonic = mnemonic;
    }

    public int getOpcode () {
        return opcode;
    }

    public String getMnemonic () {
        return mnemonic;
    }

    public boolean isValidInClass () {
        return opcode < 0xCA;
    }

}
