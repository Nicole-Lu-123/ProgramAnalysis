package Dependency;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.type.ClassOrInterfaceType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dependency {
    public List<String> clean(List<String> dirty, List<String> need, String classname){
        List<String> temp = new ArrayList<>();
        for (String d : dirty){
            if (!temp.contains(d) && need.contains(d) && !d.equals(classname)){
                temp.add(d);
                System.out.println(d);
            }
        }
        return temp;
    }

    public List<String> getDependency(CompilationUnit cu){
        List<Node> nodes = cu.getTypes().get(0).getChildNodes();
        List<String> result = new ArrayList<>();
        for(Node n : nodes){
            if (n instanceof MethodDeclaration)
            {
                result.addAll(finddependency(n));
            }
        }
        return result;
    }
    public List<String> finddependency(Node node){
        List<String> result = new ArrayList<>();

        if (node instanceof ClassOrInterfaceType){
            return Collections.singletonList(((ClassOrInterfaceType) node).getNameAsString());
        }
        if (node.getChildNodes().size() == 0){
            return null;
        }
        for(Node n : node.getChildNodes()){
            // only go deep if node is block{} or expression or variable declaration
            if (n instanceof BlockStmt || n instanceof ExpressionStmt
                    || n instanceof VariableDeclarationExpr || n instanceof VariableDeclarator || n instanceof ClassOrInterfaceType) {
                result.addAll(finddependency(n));
            }
        }
        return result;
    }
}
