package Scanner;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class PrintMethod extends VoidVisitorAdapter<Void> {

    @Override
    public void visit(MethodDeclaration MethodDec, Void v) {
        super.visit(MethodDec, v);
        SimpleName sn = MethodDec.getName();
        System.out.println("Method used in this class: " + sn);
    }
}