package com.mycompany.app;

import Scanner.CollectClass;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.nodeTypes.NodeWithIdentifier;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import inherited.inheritedClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Hello world!
 *
 */
public class App
{

    public static Map<String, List<String>> classmethodinfo;
    public static Map<String, List<String>> classextendinfo;
    public static Map<String, List<String>> classimpleinfo;
    public static Map<String, List<String>> classdependinfo;
    public static void main(String[] args) throws Exception {
        classmethodinfo = new HashMap<>();
        classextendinfo = new HashMap<>();
        classimpleinfo = new HashMap<>();
        classdependinfo = new HashMap<>();
        File file1 = new File("C:/cpsc410_project1_team16-master/DSL-photoeditor/src");
        CollectClass collectclass = new CollectClass(file1.getPath());

        inheritedClass ic = new inheritedClass(file1.getPath());


        List<String> lostr = collectclass.getClasslist();
        for (String s : lostr) {
            String path0 = collectclass.pairs.get(s);
            String path = path0 + "/" + s + ".java";
            CompilationUnit cu = StaticJavaParser.parse(new FileInputStream(path));

            VoidVisitor<Void> inherts = ic;

            App app = new App();
            List<String> extendname = app.getExtendName(inherts,cu);
            List<String> implename = app.getInterfaceName(inherts,cu);
            List<String> methods = app.getName(path);
            List<String> dependencies = app.clean(app.getDependency(cu),lostr);

            classmethodinfo.put(s,methods);
            classextendinfo.put(s,extendname);
            classimpleinfo.put(s,implename);
            classdependinfo.put(s,dependencies);

        }

    }
    public List<String> clean(List<String> dirty, List<String> need){
        List<String> res = new ArrayList<>();
        for (String d : dirty){
            if (need.contains(d)){
                res.add(d);
                System.out.println(d);
            }
        }
        return res;
    }
    public List<String> getExtendName(VoidVisitor<Void> inher, CompilationUnit cu){
        List<String> los1 = new ArrayList<>();
        inher.visit(cu,null);
        ClassOrInterfaceDeclaration coid = (ClassOrInterfaceDeclaration) cu.getTypes().get(0);
        NodeList<ClassOrInterfaceType> extendlist = coid.getExtendedTypes();
        for (ClassOrInterfaceType ct : extendlist){
            los1.add(ct.getNameAsString());
        }
        return los1;
    }
    public List<String> getInterfaceName(VoidVisitor<Void> inher, CompilationUnit cu){
        List<String> los2 = new ArrayList<>();
//        inher.visit(cu,null);
        ClassOrInterfaceDeclaration coid = (ClassOrInterfaceDeclaration) cu.getTypes().get(0);
        NodeList<ClassOrInterfaceType> implementlist = coid.getImplementedTypes();
        for (ClassOrInterfaceType ct : implementlist){
            System.out.println(ct.getNameAsString());
            los2.add(ct.getNameAsString());
        }
        return los2;
    }
    public List<String> getDependency(CompilationUnit cu){
        List<Node> nodes = cu.getTypes().get(0).getChildNodes();
        List<String> result = new ArrayList<>();
        for(Node n : nodes){
            if (n instanceof MethodDeclaration)
            {
                result.addAll(finddependency(n));
            }
        }
        return result;
    }
    public List<String> finddependency(Node node){
        List<String> result = new ArrayList<>();

        if (node instanceof ClassOrInterfaceType){
            return Collections.singletonList(((ClassOrInterfaceType) node).getNameAsString());
        }
        if (node.getChildNodes().size() == 0){
            return null;
        }
        for(Node n : node.getChildNodes()){
            // only go deep if node is block{} or expression or variable declaration
            if (n instanceof BlockStmt || n instanceof ExpressionStmt
                    || n instanceof VariableDeclarationExpr || n instanceof VariableDeclarator) {
                result.addAll(finddependency(n));
            }
        }
        return result;
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
//
//        System.out.printf("%-28s %-12s %s%n", "Node_class", "Identifier", "Node");
//        System.out.printf("%-28s %-12s %s%n", "==========", "==========", "====");
//        cu.walk(astNode -> {
//            String iden = "";
//            if (astNode instanceof NodeWithIdentifier)
//                iden = ((NodeWithIdentifier<? extends Object>) astNode).getIdentifier();
//            System.out.printf("%-28s %-12s %s%n",
//                    astNode.getClass().getSimpleName(),
//                    iden,
//                    astNode.toString().replaceFirst("(?s)\\R.*", "..."));
//        });

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

