package com.mycompany.app;

import Dependency.Dependency;
import Scanner.CollectClass;
import Scanner.CollectName;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import inherited.inheritedClass;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class Main
{
    public static Map<String, List<String>> classmethodinfo;
    public static Map<String, Integer> classsizeinfo;
    public static Map<String, List<String>> classextendinfo;
    public static Map<String, List<String>> classimpleinfo;
    public static Map<String, List<String>> classdependinfo;
    public static void main(String[] args) throws Exception {
        classmethodinfo = new HashMap<>();
        classsizeinfo = new HashMap<>();
        classextendinfo = new HashMap<>();
        classimpleinfo = new HashMap<>();
        classdependinfo = new HashMap<>();
        File file1 = new File("C:/cpsc410_project1_team16-master/DSL-photoeditor/src");
// (test for dependency) don't delete it !!!!!!!!!
//        File file2 = new File("C:/cpsc410_project1_team16-master/DSL-photoeditor/src/ast/PhotoeditorEvaluator.java");
//        CollectClass collectclass = new CollectClass(file2.getPath());
//        inheritedClass ic = new inheritedClass(file2.getPath());
//        CompilationUnit cu = StaticJavaParser.parse(new FileInputStream("C:/cpsc410_project1_team16-master/DSL-photoeditor/src/ast/PhotoeditorEvaluator.java"));
//        App app = new App();
//        List<String> lostr = new ArrayList<>();
//        lostr.add("PhotoeditorEvaluator");
//        List<String> dependencies = app.clean(app.getDependency(cu),lostr);
//        classdependinfo.put("PhotoeditorEvaluator",dependencies);
        //////////

        CollectClass collectclass = new CollectClass(file1.getPath());
        CollectName cn = new CollectName();
        inheritedClass ic = new inheritedClass(file1.getPath());
        Dependency dep = new Dependency();
        List<String> lostr = collectclass.getClasslist();
        for (String s : lostr) {
            String path0 = collectclass.pairs.get(s);
            String path = path0 + "/" + s + ".java";
            CompilationUnit cu = StaticJavaParser.parse(new FileInputStream(path));
            List<String> extendname = ic.getExtendName(ic,cu);
            List<String> implename = ic.getInterfaceName(ic,cu);
            List<String> methods = cn.getName(path);
            List<String> dependencies = dep.clean(dep.getDependency(cu),lostr,s);
            System.out.println(cn.count(methods));

            classmethodinfo.put(s,methods);
            classsizeinfo.put(s,cn.count(methods));
            classextendinfo.put(s,extendname);
            classimpleinfo.put(s,implename);
            classdependinfo.put(s,dependencies);

        }

    }


}

