package Scanner;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class PrintMethod extends VoidVisitorAdapter<Void> {
    public int countStates;
    public int countNumMethod1;

    @Override
    public void visit(MethodDeclaration MethodDec, Void v) {
        super.visit(MethodDec, v);
        SimpleName sn = MethodDec.getName();
        countNumMethod1++;
        System.out.println("Method used in this class: " + sn);
        System.out.println("number of for method in this class: " + countNumMethod1);
//         System.out.println("#loop: " + countStates);

    }

//     public void visit(ForStmt f, Void arg) {
//         super.visit(f, arg);
//         countStates++;
//     }






}
