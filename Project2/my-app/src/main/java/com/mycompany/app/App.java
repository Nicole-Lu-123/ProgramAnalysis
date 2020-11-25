package com.mycompany.app;

import Scanner.CollectClass;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
            List<String> methods = app.getName(path);
            classinfo.put(s,methods);
        }

    }
    public List<String> getName(String path) throws FileNotFoundException {
        File file = new File(path);
        System.out.println("path for class = " + file.getPath());
//        String fPaths = file.getPath();
        VoidVisitor<? extends Object> NameOfMethod = new App.printMethod();
        CompilationUnit cu = StaticJavaParser.parse(new FileInputStream(file.getPath()));

        NameOfMethod.visit(cu, null);
        VoidVisitor<List<String>> methodNameCollector = new App.SetOfName();
        List<String> methodNames = new ArrayList<>();
        methodNameCollector.visit(cu, methodNames);
//        methodNames.forEach(n -> System.out.println("Method Name Collected: " + n));
        
        System.out.printf("%-28s %-12s %s%n", "Node_class", "Identifier", "Node");
        System.out.printf("%-28s %-12s %s%n", "==========", "==========", "====");
        cu.walk(astNode -> {
            String iden = "";
            if (astNode instanceof NodeWithIdentifier)
                iden = ((NodeWithIdentifier<? extends Object>) astNode).getIdentifier();
            System.out.printf("%-28s %-12s %s%n",
                    astNode.getClass().getSimpleName(),
                    iden,
                    astNode.toString().replaceFirst("(?s)\\R.*", "..."));
        });

        return methodNames;
    }

    private static class printMethod extends VoidVisitorAdapter<Void> {

        @Override
        public void visit(MethodDeclaration MethodDec, Void v) {
            super.visit(MethodDec, v);
            SimpleName sn = MethodDec.getName();
            System.out.println("Method used in this class: " + sn);
        }
    }

    private static class SetOfName extends VoidVisitorAdapter<List<String>> {

        @Override
        public void visit(MethodDeclaration methodDec, List<String> collection_1) {
            super.visit(methodDec, collection_1);
            String str1 = methodDec.getNameAsString();
            collection_1.add(str1);
        }
    }

}

