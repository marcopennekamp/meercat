package me.pennekamp.meercat;

import me.pennekamp.meercat.data.CountableEntity;
import me.pennekamp.meercat.data.IntegerConstant;
import me.pennekamp.meercat.data.LongConstant;
import me.pennekamp.meercat.data.Operation;
import org.apache.commons.io.IOUtils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Analyzer {

    private Operation[] operations;
    private ConstantTable<Long, LongConstant> longConstantTable = new ConstantTable<> (new LongConstant.Generator ());
    private ConstantTable<Integer, IntegerConstant> integerConstantTable = new ConstantTable<> (new IntegerConstant.Generator ());

    public Analyzer () {
        initOperations ();
    }

    private void initOperations () {
        operations = new Operation[Operations.mnemonics.length];
        for (int i = 0; i < Operations.mnemonics.length; ++i) {
            operations[i] = new Operation (i, Operations.mnemonics[i]);
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

    public ConstantTable<Long, LongConstant> getLongConstantTable () {
        return longConstantTable;
    }

    public ConstantTable<Integer, IntegerConstant> getIntegerConstantTable () {
        return integerConstantTable;
    }

}
