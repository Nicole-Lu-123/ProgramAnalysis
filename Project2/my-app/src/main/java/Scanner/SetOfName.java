package Scanner;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;
import java.util.List;

public class SetOfName extends VoidVisitorAdapter<List<String>> {
    public List<String> MethodWhichhasLoop;

    @Override
    public void visit(MethodDeclaration methodDec, List<String> collection_1) {
        MethodWhichhasLoop = new ArrayList<>();
        super.visit(methodDec, collection_1);
        String str1 = methodDec.getNameAsString();
        collection_1.add(str1);
        CollectName cn = new CollectName();

        if(cn.process(methodDec)== true) {
            MethodWhichhasLoop.add(methodDec.getNameAsString());
            System.out.println("contain for loop? " + str1);
        }
    }

    public List<String> getMethodWhichhasLoop() {
        return MethodWhichhasLoop;
    }
}
