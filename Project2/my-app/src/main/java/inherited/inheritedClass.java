package inherited;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;
import java.util.List;


public class inheritedClass extends VoidVisitorAdapter<Void> {

    public final String path;

    public inheritedClass(String path){

        this.path = path;
    }

    public void visit(ClassOrInterfaceDeclaration inter , Void v){
        super.visit(inter,v);
        System.out.println(" "+inter.getExtendedTypes());
    }
    public List<String> getExtendName(VoidVisitor<Void> inher, CompilationUnit cu){
        List<String> los1 = new ArrayList<>();
        inher.visit(cu,null);
        ClassOrInterfaceDeclaration coid = (ClassOrInterfaceDeclaration) cu.getTypes().get(0);
        NodeList<ClassOrInterfaceType> extendlist = coid.getExtendedTypes();
        for (ClassOrInterfaceType ct : extendlist){
            los1.add(ct.getNameAsString());
        }
        return los1;
    }
    public List<String> getInterfaceName(VoidVisitor<Void> inher, CompilationUnit cu){
        List<String> los2 = new ArrayList<>();
//        inher.visit(cu,null);
        ClassOrInterfaceDeclaration coid = (ClassOrInterfaceDeclaration) cu.getTypes().get(0);
        NodeList<ClassOrInterfaceType> implementlist = coid.getImplementedTypes();
        for (ClassOrInterfaceType ct : implementlist){
            System.out.println(ct.getNameAsString());
            los2.add(ct.getNameAsString());
        }
        return los2;
    }
}
