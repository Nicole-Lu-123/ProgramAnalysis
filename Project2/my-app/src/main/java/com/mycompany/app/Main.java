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
import com.mycompany.BasicSimpleUniverse;
import com.sun.j3d.utils.applet.MainFrame;
import inherited.inheritedClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {

        String oldpath = "C:\\Users\\liuce\\Desktop\\cpsc410_project1_team16(1)\\DSL-photoeditor\\src";
        String newpath = "C:\\Users\\liuce\\Desktop\\cpsc410_project1_team16-master(2)\\DSL-photoeditor\\src";
        //File file1 = new File("C:\\cpsc410_project1_team16-master\\DSL-photoeditor\\src");
        //File file2 = new File("/Users/tianyuxin/Downloads/tinyVarsProcSkeleton-3/src");
        File file1 = new File(oldpath);
        CombineInfo cbi1 = new CombineInfo(file1.getPath());

        File file2 = new File(newpath);
        CombineInfo cbi2 = new CombineInfo(file2.getPath());
        System.out.println("ClassLoopMethod of 1st commit: " + cbi1.getClassLoopinfo());
        System.out.println("ClassLoopMethod of 2nd commit: " + cbi2.getClassLoopinfo());
        System.out.println("Done first commit analysis!!!");

        Compare compare = new Compare(oldpath, newpath);
        List<String> addclass = compare.compareClass().get(0);
        List<String> deletclass = compare.compareClass().get(1);

        System.out.println("Add class: ");
        System.out.println(addclass);
        System.out.println("Delete Class: ");
        System.out.println(deletclass);

        System.out.println("Add methods: ");
        System.out.println(compare.methodAdd());
        System.out.println("Delete methods: ");
        System.out.println(compare.methodDelet());

        System.out.println("Add extension: ");
        System.out.println(compare.addextend());
        System.out.println("Delete extension: ");
        System.out.println(compare.removeextend());

        System.out.println("Add implementation: ");
        System.out.println(compare.addinterface());
        System.out.println("Delete implementation: ");
        System.out.println(compare.removeinterface());

        System.out.println("Add dependency: ");
        System.out.println(compare.adddep());
        System.out.println("Delete dependency: ");
        System.out.println(compare.removedep());


        // visualization
        new MainFrame(new BasicSimpleUniverse(cbi1, cbi2, compare), 700, 700);
    }


}




