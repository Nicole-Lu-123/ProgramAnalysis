package Scanner;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class CollectName {
    public List<String> getName(String path) throws FileNotFoundException {
        File file = new File(path);
        System.out.println("path for class = " + file.getPath());
//        String fPaths = file.getPath();
        VoidVisitor<? extends Object> NameOfMethod = new PrintMethod();
        CompilationUnit cu = StaticJavaParser.parse(new FileInputStream(file.getPath()));

        NameOfMethod.visit(cu, null);
        VoidVisitor<List<String>> methodNameCollector = new SetOfName();
        List<String> methodNames = new ArrayList<>();
        methodNameCollector.visit(cu, methodNames);
        return methodNames;

    }
}





