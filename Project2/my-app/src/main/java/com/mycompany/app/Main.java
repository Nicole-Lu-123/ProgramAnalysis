package com.mycompany.app;

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

        File file1 = new File("C:\\cpsc410_project1_team16-master\\DSL-photoeditor\\src");
//        File file2 = new File("/Users/tianyuxin/Downloads/tinyVarsProcSkeleton-3/src");
        CombineInfo cbi = new CombineInfo(file1.getPath());
        cbi.generate();

    }

}
