package me.pennekamp.meercat.data;

import me.pennekamp.meercat.Operations;
import me.pennekamp.meercat.visual.CountableGenerator;

public class K3Gram extends CountableEntity {

    public static class Generator implements CountableGenerator<Integer, K3Gram> {

        @Override
        public K3Gram generate (Integer value) {
            int opcode0 = (value & 0x00FF0000) >> 16;
            int opcode1 = (value & 0x0000FF00) >> 8;
            int opcode2 = (value & 0x000000FF);
            return new K3Gram (opcode0, opcode1, opcode2);
        }

    }

    /**
     * Sequentially descending.
     */
    private int opcode0;
    private int opcode1;
    private int opcode2;

    public K3Gram (int opcode0, int opcode1, int opcode2) {
        this.opcode0 = opcode0;
        this.opcode1 = opcode1;
        this.opcode2 = opcode2;
    }

    @Override
    public int hashCode () {
        return getKey (opcode0, opcode1, opcode2);
    }

    public static int getKey (int opcode0, int opcode1, int opcode2) {
        final int byteMask = 0xFF;
        int key = 0xFF000000;
        key |= (opcode0 & byteMask) << 16;
        key |= (opcode1 & byteMask) << 8;
        key |= (opcode2 & byteMask);
        return key;
    }

    @Override
    public String toString () {
        return Operations.mnemonics[opcode0] + ", " + Operations.mnemonics[opcode1]
                + ", " + Operations.mnemonics[opcode2];
    }

    public int getOpcode0 () {
        return opcode0;
    }

    public int getOpcode1 () {
        return opcode1;
    }

    public int getOpcode2 () {
        return opcode2;
    }

}
