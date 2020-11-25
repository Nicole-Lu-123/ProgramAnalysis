package inherited;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;


public class inheritedClass extends VoidVisitorAdapter<Void> {

    public final String path;

    public inheritedClass(String path){

        this.path = path;
    }

    public void visit(ClassOrInterfaceDeclaration inter , Void v){
        super.visit(inter,v);
        System.out.println(" "+inter.getExtendedTypes());
    }

}
