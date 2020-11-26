package com.mycompany.app;

import Compare.Compare;
import Dependency.Dependency;
import Scanner.CollectClass;
import Scanner.CollectName;

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

    public static void main(String[] args) throws Exception {

//        File file1 = new File("C:\\cpsc410_project1_team16-master\\DSL-photoeditor\\src");
//        File file2 = new File("/Users/tianyuxin/Downloads/tinyVarsProcSkeleton-3/src");
//        CombineInfo cbi1 = new CombineInfo(file1.getPath());
//        cbi1.generate();
//        for(String s :cbi1.classdependinfo.get("PhotoeditorParser")){
//            System.out.println(s);
//        }
//        CombineInfo cbi2 = new CombineInfo(file1.getPath());
        String oldpath = "C:\\cpsc410_project1_team16-master\\DSL-photoeditor\\src\\ast";
        String newpath = "C:\\cpsc410_project1_team16-master\\DSL-photoeditor\\src\\libs";
        Compare compare = new Compare(oldpath,newpath);
        List<String> addclass = compare.compareclass().get(0);
        List<String> deletclass = compare.compareclass().get(1);
        for(String s :addclass){
            System.out.println(s);
        }
        System.out.println("############################");
        for(String s :deletclass){
            System.out.println(s);
        }

    }

}
