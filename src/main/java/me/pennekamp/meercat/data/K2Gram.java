package me.pennekamp.meercat.data;

import me.pennekamp.meercat.Operations;
import me.pennekamp.meercat.visual.CountableGenerator;

public class K2Gram extends CountableEntity {

    public static class Generator implements CountableGenerator<Integer, K2Gram> {

        @Override
        public K2Gram generate (Integer value) {
            int opcode0 = (value & 0x0000FF00) >> 8;
            int opcode1 = (value & 0x000000FF);
            return new K2Gram (opcode0, opcode1);
        }

    }

    /**
     * Sequentially descending.
     */
    private int opcode0;
    private int opcode1;

    public K2Gram (int opcode0, int opcode1) {
        this.opcode0 = opcode0;
        this.opcode1 = opcode1;
    }

    @Override
    public int hashCode () {
        return getKey (opcode0, opcode1);
    }

    public static int getKey (int opcode0, int opcode1) {
        final int byteMask = 0xFF;
        int key = 0xFFFF0000;
        key |= (opcode0 & byteMask) << 8;
        key |= (opcode1 & byteMask);
        return key;
    }

    @Override
    public String toString () {
        return Operations.mnemonics[opcode0] + ", " + Operations.mnemonics[opcode1];
    }

    public int getOpcode0 () {
        return opcode0;
    }

    public int getOpcode1 () {
        return opcode1;
    }

}
