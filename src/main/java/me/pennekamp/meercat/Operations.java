package me.pennekamp.meercat;

public class Operations {

    private static String FREE = "(no name)";

    /**
     * Sorted by id, starting from 0x00, ending at 0xFF
     */
    public static String[] mnemonics = new String[] {
            /* 0x00 - 0x0F */
            "nop", "aconst_null", "iconst_m1", "iconst_0", "iconst_1", "iconst_2", "iconst_3", "iconst_4",
            "iconst_5", "lconst_0", "lconst_1", "fconst_0", "fconst_1", "fconst_2", "dconst_0", "dconst_1",

            /* 0x10 - 0x1F */
            "bipush", "sipush", "ldc/ldc_w/ldc2_w", "ldc_w", "ldc2_w", "iload", "lload", "fload",
            "dload", "aload", "iload_0", "iload_1", "iload_2", "iload_3", "lload_0", "lload_1",

            /* 0x20 - 0x2F */
            "lload_2", "lload_3", "fload_0", "fload_1", "fload_2", "fload_3", "dload_0", "dload_1",
            "dload_2", "dload_3", "aload_0", "aload_1", "aload_2", "aload_3", "iaload", "laload",

            /* 0x30 - 0x3F*/
            "faload", "daload", "aaload", "baload", "caload", "saload", "istore", "lstore",
            "fstore", "dstore", "astore", "istore_0", "istore_1", "istore_2", "istore_3", "lstore_0",

            /* 0x40 - 0x4F */
            "lstore_1", "lstore_2", "lstore_3", "fstore_0", "fstore_1", "fstore_2", "fstore_3", "dstore_0",
            "dstore_1", "dstore_2", "dstore_3", "astore_0", "astore_1", "astore_2", "astore_3", "iastore",

            /* 0x50 - 0x5F */
            "lastore", "fastore", "dastore", "aastore", "bastore", "castore", "sastore", "pop",
            "pop2", "dup", "dup_x1", "dup_x2", "dup2", "dup2_x1", "dup2_x2", "swap",

            /* 0x60 - 0x6F */
            "iadd", "ladd", "fadd", "dadd", "isub", "lsub", "fsub", "dsub",
            "imul", "lmul", "fmul", "dmul", "idiv", "ldiv", "fdiv", "ddiv",

            /* 0x70 - 0x7F */
            "irem", "lrem", "frem", "drem", "ineg", "lneg", "fneg", "dneg",
            "ishl", "lshl", "ishr", "lshr", "iushr", "lushr", "iand", "land",

            /* 0x80 - 0x8F */
            "ior", "lor", "ixor", "lxor", "iinc", "i2l", "i2f", "i2d",
            "l2i", "l2f", "l2d", "f2i", "f2l", "f2d", "d2i", "d2l",

            /* 0x90 - 0x9F */
            "d2f", "i2b", "i2c", "i2s", "lcmp", "fcmpl", "fcmpg", "dcmpl",
            "dcmpg", "ifeq", "ifne", "iflt", "ifge", "ifgt", "ifle", "if_icmpeq",

            /* 0xA0 - 0xAF */
            "if_icmpne", "if_icmplt", "if_icmpge", "if_icmpgt", "if_icmple", "if_acmpeq", "if_acmpne", "goto",
            "jsr", "ret", "tableswitch", "lookupswitch", "ireturn", "lreturn", "freturn", "dreturn",

            /* 0xB0 - 0xBF */
            "areturn", "return", "getstatic", "putstatic", "getfield", "putfield", "invokevirtual", "invokespecial",
            "invokestatic", "invokeinterface", "invokedynamic", "new", "newarray", "anewarray", "arraylength", "athrow",

            /* 0xC0 - 0xCF */
            "checkcast", "instanceof", "monitorenter", "monitorexit", "wide", "multianewarray", "ifnull", "ifnonnull",
            "goto_w", "jsr_w", "breakpoint", FREE, FREE, FREE, FREE, FREE,

            /* 0xD0 - 0xDF */
            FREE, FREE, FREE, FREE, FREE, FREE, FREE, FREE,
            FREE, FREE, FREE, FREE, FREE, FREE, FREE, FREE,

            /* 0xE0 - 0xEF */
            FREE, FREE, FREE, FREE, FREE, FREE, FREE, FREE,
            FREE, FREE, FREE, FREE, FREE, FREE, FREE, FREE,

            /* 0xF0 - 0xFF */
            FREE, FREE, FREE, FREE, FREE, FREE, FREE, FREE,
            FREE, FREE, FREE, FREE, FREE, FREE, "impdep1", "impdep2"
    };

}
