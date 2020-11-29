package com.mycompany.app;

import Compare.Compare;
import com.mycompany.BasicSimpleUniverse;
import com.sun.j3d.utils.applet.MainFrame;

import java.io.File;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
//
//        String oldpath = "C:\\Users\\liuce\\Desktop\\cpsc410_project1_team16(1)\\DSL-photoeditor\\src";
//        String newpath = "C:\\Users\\liuce\\Desktop\\cpsc410_project1_team16-master(2)\\DSL-photoeditor\\src";
//        //File file1 = new File("C:\\cpsc410_project1_team16-master\\DSL-photoeditor\\src");
        String oldpath = "/Users/difeisu/Desktop/cpsc410_project1_team161/DSL-photoeditor/src";
        String newpath = "/Users/difeisu/Desktop/cpsc410_project1_team162/DSL-photoeditor/src";
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
        new MainFrame(new BasicSimpleUniverse(cbi1, cbi2, compare, file1, file2), 700, 700);
    }






}




