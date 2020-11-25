package Scanner;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.List;

public class SetOfName extends VoidVisitorAdapter<List<String>> {

    @Override
    public void visit(MethodDeclaration methodDec, List<String> collection_1) {
        super.visit(methodDec, collection_1);
        String str1 = methodDec.getNameAsString();
        collection_1.add(str1);
    }
}