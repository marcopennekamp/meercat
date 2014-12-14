package me.pennekamp.meercat;

import me.pennekamp.meercat.data.*;
import me.pennekamp.meercat.visual.CountableTable;
import me.pennekamp.meercat.visual.Format;
import org.apache.commons.io.IOUtils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;

import java.io.*;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Analyzer {

    private static final int MAX_OP_QUEUE = 3;

    private Operation[] operations;
    private List<Integer> lastOperations = new LinkedList<> ();
    private CountableTable<Integer, K2Gram> k2Grams = new CountableTable<Integer, K2Gram> (new K2Gram.Generator ()) {{
        widths[0] = 60;
    }};
    private CountableTable<Integer, K3Gram> k3Grams = new CountableTable<Integer, K3Gram> (new K3Gram.Generator ()) {{
        widths[0] = 60;
    }};
    private CountableTable<Long, LongConstant> longConstantTable = new CountableTable<> (new LongConstant.Generator ());
    private CountableTable<Integer, IntegerConstant> integerConstantTable = new CountableTable<> (new IntegerConstant.Generator ());

    public Analyzer () {
        initOperations ();
    }

    private void initOperations () {
        operations = new Operation[Operations.mnemonics.length];
        for (int i = 0; i < Operations.mnemonics.length; ++i) {
            operations[i] = new Operation (i, Operations.mnemonics[i]);
        }
    }

    public void processOperation (int opcode) {
        operations[opcode].incrementCount ();

        /* Add to last operations. */
        if (lastOperations.size () > MAX_OP_QUEUE) {
            lastOperations.remove (0);
        }
        lastOperations.add (opcode);

        /* Fill 2-gram. */
        int k2GramOffset = lastOperations.size () - 2;
        if (k2GramOffset >= 0) {
            int opcode0 = lastOperations.get (k2GramOffset);
            int opcode1 = lastOperations.get (k2GramOffset + 1);
            int key = K2Gram.getKey (opcode0, opcode1);
            k2Grams.count (key);
        }

        /* Fill 3-gram. */
        int k3GramOffset = lastOperations.size () - 3;
        if (k3GramOffset >= 0) {
            int opcode0 = lastOperations.get (k3GramOffset);
            int opcode1 = lastOperations.get (k3GramOffset + 1);
            int opcode2 = lastOperations.get (k3GramOffset + 2);
            int key = K3Gram.getKey (opcode0, opcode1, opcode2);
            k3Grams.count (key);
        }
    }

    public String report () {
        StringWriter stringWriter = new StringWriter ();
        PrintWriter writer = new PrintWriter (stringWriter);

        /* Sort operations by count. */
        ArrayList<Operation> sortedOperations = new ArrayList<> (Arrays.asList (operations));
        Collections.sort (sortedOperations, new CountableEntity.CountComparator ());

        String[] headings = new String[] { "Mnemonic", "Count", "%" };
        int[] widths = new int[] { 30, 30, 30 };
        List<Object[]> rows = new ArrayList<> ();

        computeOperationPercentages ();

        for (final Operation operation : sortedOperations) {
            if (operation.isValidInClass ()) {
                rows.add (new Object[] { operation.getMnemonic (), operation.getCount (),
                        operation.getPercentageFormatted () });
            }
        }

        Format.printTable (writer, headings, widths, rows.toArray (new Object[rows.size ()][]));
        writer.println ();

        writer.println (k2Grams.report ());
        writer.println (k3Grams.report ());
        writer.println (longConstantTable.report ());
        writer.println (integerConstantTable.report ());

        return stringWriter.toString ();
    }

    private void computeOperationPercentages () {
        int total = 0;
        for (Operation operation : operations) {
            total += operation.getCount ();
        }

        for (Operation operation : operations) {
            operation.setPercentage (((double) operation.getCount () / (double) total) * 100.0);
        }
    }

    public void loadJar (File file) {
        try {
            JarFile jarFile = new JarFile (file);

            Enumeration<JarEntry> entries = jarFile.entries ();
            while (entries.hasMoreElements ()) {
                JarEntry entry = entries.nextElement ();
                if (entry.getName ().endsWith (".class")) {
                    InputStream in = jarFile.getInputStream (entry);
                    byte[] data = IOUtils.toByteArray (in);

                    // System.out.println ("Visiting class: " + entry.getName ());
                    CatClassVisitor catVisitor = new CatClassVisitor (Opcodes.ASM5, this);
                    ClassReader classReader = new ClassReader (data);
                    classReader.accept (catVisitor, 0);
                }
            }
        } catch (IOException e) {
            e.printStackTrace ();
        }
    }


    public Operation[] getOperations () {
        return operations;
    }

    public CountableTable<Long, LongConstant> getLongConstantTable () {
        return longConstantTable;
    }

    public CountableTable<Integer, IntegerConstant> getIntegerConstantTable () {
        return integerConstantTable;
    }

}
