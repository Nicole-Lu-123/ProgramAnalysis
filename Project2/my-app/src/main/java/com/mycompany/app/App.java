package com.mycompany.app;

import Scanner.CollectClass;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App
{
    public static Map<String, List<String>> classinfo;
    public static void main(String[] args) throws Exception {
        classinfo = new HashMap<>();
        File file1 = new File("C:/cpsc410_project1_team16-master/DSL-photoeditor/src");
        CollectClass collectclass = new CollectClass(file1.getPath());
        List<String> lostr = collectclass.getClasslist();
        for (String s : lostr) {
            String path0 = collectclass.pairs.get(s);
            String path = path0 + "/" + s + ".java";
            App app = new App();
            List<String> methods = app.printMethods(path);
            classinfo.put(s,methods);
        }

    }
    public List<String> printMethods(String path) throws FileNotFoundException {
        File file = new File(path);
        System.out.println("path = " + file.getPath());
        String FILE_PATH = file.getPath();
        CompilationUnit cu = StaticJavaParser.parse(new FileInputStream(FILE_PATH));

        VoidVisitor<?> methodNameVisitor = new App.MethodNamePrinter();
        methodNameVisitor.visit(cu, null);

        List<String> methodNames = new ArrayList<>();
        VoidVisitor<List<String>> methodNameCollector = new App.MethodNameCollector();
        methodNameCollector.visit(cu, methodNames);
//        methodNames.forEach(n -> System.out.println("Method Name Collected: " + n));
        return methodNames;
    }

    private static class MethodNamePrinter extends VoidVisitorAdapter<Void> {

        @Override
        public void visit(MethodDeclaration md, Void arg) {
            super.visit(md, arg);
            System.out.println("Method Name Printed: " + md.getName());
        }
    }

    private static class MethodNameCollector extends VoidVisitorAdapter<List<String>> {

        @Override
        public void visit(MethodDeclaration md, List<String> collector) {
            super.visit(md, collector);
            collector.add(md.getNameAsString());
        }
    }

}
