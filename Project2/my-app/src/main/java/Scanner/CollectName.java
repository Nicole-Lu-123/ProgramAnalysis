package Scanner;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.visitor.VoidVisitor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

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
        System.out.println("number of for loop in this class: " + process(cu));


        return methodNames;

    }


    public static boolean process(Node node) {
        int complexity = 0;
        for (ForStmt forStmt : node.getChildNodesByType(ForStmt.class)) {
            // We found an for
                if (forStmt.isForStmt()) {
                    return true;
                }


        }
        return false;
    }


    public Integer count(List<String> number) {
        return number.size();
    }


}






