package me.pennekamp.meercat;

import java.io.File;

public class Console {

    public static void main (String[] args) {
        Analyzer analyzer = new Analyzer ();
        analyzer.loadJar (new File ("meercat-0.1-dev.jar"));
        for (String arg : args) {
            analyzer.loadJar (new File (arg));
        }

        /* Load all jars in /data. */
        File dataFolder = new File ("data");
        if (dataFolder.isDirectory ()) {
            File[] files = dataFolder.listFiles ();
            if (files != null) {
                for (File file : files) {
                    if (file.getName ().endsWith (".jar")) {
                        analyzer.loadJar (file);
                    }
                }
            }else {
                System.out.println ("Could not fetch file list of data folder.");
            }
        }else {
            System.out.println ("Data folder not found.");
        }

        System.out.println ();
        System.out.println (analyzer.report ());
    }

}
