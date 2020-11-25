package inherited;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;


public class inheritedClass extends VoidVisitorAdapter<Void> {

    public final String path;

    public inheritedClass(String path){

        this.path = path;
    }

    public void visit(ClassOrInterfaceDeclaration cid , Void arg){
        super.visit(cid,arg);
        //I guess you just want to print the inherited class's name at the console
        System.out.println(""+cid.getExtendedTypes());
    }

}
