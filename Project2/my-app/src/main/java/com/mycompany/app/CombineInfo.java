package com.mycompany.app;

import Dependency.Dependency;
import Scanner.CollectClass;
import Scanner.CollectName;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import inherited.inheritedClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CombineInfo {
    public  Map<String, List<String>> classmethodinfo;
    public  Map<String, List<String>> classextendinfo;
    public  Map<String, List<String>> classimpleinfo;
    public  Map<String, List<String>> classdependinfo;
    public  Map<String, Integer> classsizeinfo;
    public  List<String> classinfo;;
    public String path;

    public CombineInfo(String path) throws IOException {
        this.path = path;
        generate();
    }
    public void generate() throws IOException {
        classmethodinfo = new HashMap<>();
        classextendinfo = new HashMap<>();
        classimpleinfo = new HashMap<>();
        classdependinfo = new HashMap<>();
        File file1 = new File(this.path);
        CollectClass collectclass = new CollectClass(file1.getPath());
        CollectName cn = new CollectName();
        inheritedClass ic = new inheritedClass(file1.getPath());
        Dependency dep = new Dependency();
        List<String> lostr = collectclass.getClasslist();
        classinfo = lostr;
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
            classsizeinfo.put(s,cn.count(methods));
        }
    }

    public Map<String, List<String>> getmethodmap(){
        return classmethodinfo;
    }
    public Map<String, List<String>> getdependmap(){
        return classdependinfo;
    }
    public Map<String, List<String>> getextendmap(){
        return classextendinfo;
    }
    public Map<String, List<String>> getintermap(){
        return classimpleinfo;
    }

}
