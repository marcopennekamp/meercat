package me.pennekamp.meercat;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

public class CatClassVisitor extends ClassVisitor {

    private Analyzer analyzer;

    public CatClassVisitor (int api, Analyzer analyzer) {
        super (api);
        this.analyzer = analyzer;
    }

    public CatClassVisitor (int api, ClassVisitor cv, Analyzer analyzer) {
        super (api, cv);
        this.analyzer = analyzer;
    }

    @Override
    public MethodVisitor visitMethod (int access, String name, String desc, String signature, String[] exceptions) {
        return new CatMethodVisitor (api, super.visitMethod (access, name, desc, signature, exceptions), analyzer);
    }

}
