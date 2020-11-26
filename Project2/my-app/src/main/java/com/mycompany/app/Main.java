package com.mycompany.app;

import Dependency.Dependency;
import Scanner.CollectClass;
import Scanner.CollectName;
package com.mycompany.app;

import Dependency.Dependency;
import Scanner.CollectClass;
import Scanner.CollectName;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import inherited.inheritedClass;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Main {
    public static Map<String, List<String>> classmethodinfo;
    public static Map<String, List<String>> classextendinfo;
    public static Map<String, List<String>> classimpleinfo;
    public static Map<String, List<String>> classdependinfo;

    public static Map<String, List<String>> classmethodinfo1;
    public static Map<String, List<String>> classextendinfo1;
    public static Map<String, List<String>> classimpleinfo1;
    public static Map<String, List<String>> classdependinfo1;

    public static void main(String[] args) throws Exception {
        classmethodinfo = new HashMap<>();
        classextendinfo = new HashMap<>();
        classimpleinfo = new HashMap<>();
        classdependinfo = new HashMap<>();

        classmethodinfo1 = new HashMap<>();
        classextendinfo1 = new HashMap<>();
        classimpleinfo1 = new HashMap<>();
        classdependinfo1 = new HashMap<>();
        File file1 = new File("/Users/tianyuxin/Downloads/project1/DSL-photoeditor/src");
        File file2 = new File("/Users/tianyuxin/Downloads/tinyVarsProcSkeleton-3/src");

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

        CollectClass collectclass = new CollectClass(file1.getPath());
        CollectName cn = new CollectName();
        inheritedClass ic = new inheritedClass(file1.getPath());
        Dependency dep = new Dependency();
        List<String> lostr = collectclass.getClasslist();
        for (String s : lostr) {
            String path0 = collectclass.pairs.get(s);
            String path = path0 + "/" + s + ".java";
            CompilationUnit cu = StaticJavaParser.parse(new FileInputStream(path));
            List<String> extendname = ic.getExtendName(ic, cu);
            List<String> implename = ic.getInterfaceName(ic, cu);
            List<String> methods = cn.getName(path);
            List<String> dependencies = dep.clean(dep.getDependency(cu), lostr, s);
            classmethodinfo.put(s, methods);
            classextendinfo.put(s, extendname);
            classimpleinfo.put(s, implename);
            classdependinfo.put(s, dependencies);
        }

        CollectClass collectclass2 = new CollectClass(file2.getPath());
        CollectName cn2 = new CollectName();
        inheritedClass ic2 = new inheritedClass(file2.getPath());
        Dependency dep2 = new Dependency();
        List<String> lostr2 = collectclass2.getClasslist();
        for (String s1 : lostr2) {
            String path0 = collectclass2.pairs.get(s1);
            String path = path0 + "/" + s1 + ".java";
            CompilationUnit cu = StaticJavaParser.parse(new FileInputStream(path));
            List<String> extendname = ic2.getExtendName(ic2, cu);
            List<String> implename = ic2.getInterfaceName(ic2, cu);
            List<String> methods = cn2.getName(path);
            List<String> dependencies = dep2.clean(dep2.getDependency(cu), lostr2, s1);
            classmethodinfo1.put(s1, methods);
            classextendinfo1.put(s1, extendname);
            classimpleinfo1.put(s1, implename);
            classdependinfo1.put(s1, dependencies);
        }


//        doFunction(file1);
//        doFunction(file2);


    }

}
