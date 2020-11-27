package com.mycompany.app;

import Compare.Compare;
import Dependency.Dependency;
import Scanner.CollectClass;
import Scanner.CollectName;
//
import Dependency.Dependency;
import Scanner.CollectClass;
import Scanner.CollectName;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
//import com.mycompany.BasicSimpleUniverse;
//import com.sun.j3d.utils.applet.MainFrame;
//import inherited.inheritedClass;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {

        //File file1 = new File("C:\\cpsc410_project1_team16-master\\DSL-photoeditor\\src");
        //File file2 = new File("/Users/tianyuxin/Downloads/tinyVarsProcSkeleton-3/src");
//        File file1 = new File("C:\\Users\\liuce\\Desktop\\cpsc410_project1_team16-master\\DSL-photoeditor\\src");
//        CombineInfo cbi1 = new CombineInfo(file1.getPath());
        // cbi1.generate(); // delete, add this to CombineInfo constructor

//        System.out.println("Done first commit analysis!!!");
//        for(String s :cbi1.classdependinfo.get("PhotoeditorParser")){
//            System.out.println(s);
//        }
//        CombineInfo cbi2 = new CombineInfo(file1.getPath());
        String oldpath = "C:\\Users\\卢晓萱\\Desktop\\cpsc410_project1_team16-master\\DSL-photoeditor\\src";
        String newpath = "C:\\Users\\卢晓萱\\Desktop\\cpsc410_project1_team16-9953fa8b1a4ac0d26668bd2a1a5fb09ec5a806a7\\DSL-photoeditor\\src";
        Compare compare = new Compare(oldpath,newpath);
        Map<String, List<String>> add = compare.compareExtend().get(0);
        Map<String, List<String>> delet = compare.compareExtend().get(1);

        System.out.println("############################");
        for(String s :delet.keySet()){
            System.out.println(s);
            System.out.println(delet.get(s));
        }


        // visualization
//        new MainFrame(new BasicSimpleUniverse(cbi1), 700, 700);
    }

}
