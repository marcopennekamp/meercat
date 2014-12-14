package me.pennekamp.meercat;

import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class CatMethodVisitor extends MethodVisitor {

    private Analyzer analyzer;

    public CatMethodVisitor (int api, Analyzer analyzer) {
        super (api);
        this.analyzer = analyzer;
    }

    public CatMethodVisitor (int api, MethodVisitor mv, Analyzer analyzer) {
        super (api, mv);
        this.analyzer = analyzer;
    }

    @Override
    public void visitInsn (int opcode) {
        analyzer.processOperation (opcode);

        /* Add int constants. */
        {
            int value = Integer.MIN_VALUE;
            switch (opcode) {
                case Opcodes.ICONST_M1:     value = -1; break;
                case Opcodes.ICONST_0:      value =  0; break;
                case Opcodes.ICONST_1:      value =  1; break;
                case Opcodes.ICONST_2:      value =  2; break;
                case Opcodes.ICONST_3:      value =  3; break;
                case Opcodes.ICONST_4:      value =  4; break;
            }

            if (value != Integer.MIN_VALUE) {
                analyzer.getIntegerConstantTable ().count (value);
                return;
            }
        }

        /* Add long constants. */
        {
            long value = Long.MIN_VALUE;
            switch (opcode) {
                case Opcodes.LCONST_0:      value = 0; break;
                case Opcodes.LCONST_1:      value = 1; break;
            }

            if (value != Long.MIN_VALUE) {
                analyzer.getLongConstantTable ().count (value);
                return;
            }
        }
    }

    @Override
    public void visitIntInsn (int opcode, int operand) {
        analyzer.processOperation (opcode);

        if (opcode != Opcodes.NEWARRAY) {
            analyzer.getIntegerConstantTable ().count (operand);
        }
    }

    @Override
    public void visitVarInsn (int opcode, int var) {
        if (var <= 3) {
            switch (opcode) {
                case Opcodes.ILOAD: analyzer.processOperation (0x1A + var); break; /* iload_0 */
                case Opcodes.LLOAD: analyzer.processOperation (0x1E + var); break; /* lload_0 */
                case Opcodes.FLOAD: analyzer.processOperation (0x22 + var); break; /* fload_0 */
                case Opcodes.DLOAD: analyzer.processOperation (0x26 + var); break; /* dload_0 */
                case Opcodes.ALOAD: analyzer.processOperation (0x2A + var); break; /* aload_0 */
                case Opcodes.ISTORE: analyzer.processOperation (0x3B + var); break; /* istore_0 */
                case Opcodes.LSTORE: analyzer.processOperation (0x3F + var); break; /* lstore_0 */
                case Opcodes.FSTORE: analyzer.processOperation (0x43 + var); break; /* fstore_0 */
                case Opcodes.DSTORE: analyzer.processOperation (0x47 + var); break; /* dstore_0 */
                case Opcodes.ASTORE: analyzer.processOperation (0x4B + var); break; /* astore_0 */
            }
        }else {
            analyzer.processOperation (opcode);
        }
    }

    @Override
    public void visitTypeInsn (int opcode, String type) {
        analyzer.processOperation (opcode);
    }

    @Override
    public void visitFieldInsn (int opcode, String owner, String name, String desc) {
        analyzer.processOperation (opcode);
    }

    @Override
    public void visitMethodInsn (int opcode, String owner, String name, String desc, boolean itf) {
        analyzer.processOperation (opcode);
    }

    @Override
    public void visitInvokeDynamicInsn (String name, String desc, Handle bsm, Object... bsmArgs) {
        analyzer.processOperation (Opcodes.INVOKEDYNAMIC);
    }

    @Override
    public void visitJumpInsn (int opcode, Label label) {
        analyzer.processOperation (opcode);
    }

    @Override
    public void visitLdcInsn (Object cst) {
        analyzer.processOperation (Opcodes.LDC);

        if (cst instanceof Long) {
            analyzer.getLongConstantTable ().count ((Long) cst);
        }else if (cst instanceof Integer) {
            analyzer.getIntegerConstantTable ().count ((Integer) cst);
        }
    }

    @Override
    public void visitIincInsn (int var, int increment) {
        analyzer.processOperation (Opcodes.IINC);
    }

    @Override
    public void visitTableSwitchInsn (int min, int max, Label dflt, Label... labels) {
        analyzer.processOperation (Opcodes.TABLESWITCH);
    }

    @Override
    public void visitLookupSwitchInsn (Label dflt, int[] keys, Label[] labels) {
        analyzer.processOperation (Opcodes.LOOKUPSWITCH);
    }

    @Override
    public void visitMultiANewArrayInsn (String desc, int dims) {
        analyzer.processOperation (Opcodes.MULTIANEWARRAY);
    }

}
