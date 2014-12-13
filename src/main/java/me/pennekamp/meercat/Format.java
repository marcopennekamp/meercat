package me.pennekamp.meercat;

import java.io.PrintWriter;
import java.util.Arrays;

public class Format {

    public static void printTable (PrintWriter writer, String[] headings, int[] widths, Object[][] cells) {
        int fullWidth = 0;
        for (int width : widths) {
            fullWidth += width;
        }

        /* Print headings. */
        for (int i = 0; i < headings.length && i < widths.length; ++i) {
            printCell (writer, headings[i], widths[i]);
        }
        writer.println ();

        /* Print division line. */
        writer.println (fillString (fullWidth, '-'));

        /* Print contents. */
        for (int i = 0; i < cells.length; ++i) {
            Object[] row = cells[i];
            for (int j = 0; j < row.length && j < widths.length; ++j) {
                printCell (writer, row[j].toString (), widths[j]);
            }
            writer.println ();
        }
    }

    private static void printCell (PrintWriter writer, String str, int width) {
        int diff = width - str.length ();
        if (diff < 1) diff = 1;
        writer.print (str);
        writer.print (fillString (diff, ' '));
    }

    private static String fillString (int length, char c) {
        char[] data = new char[length];
        Arrays.fill (data, c);
        return new String (data);
    }

}
