package Scanner;

import com.github.javaparser.JavaParser;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class CollectClass {
    private final String path;
    private List<String> classlist;
    public Map<String,String> pairs;
    public CollectClass(String path){
        this.classlist = new ArrayList<>();
        this.pairs = new HashMap<>();
        this.path = path;
    }
    public List<String> getClasslist() throws IOException { //throw an exception(see scanstore)
        System.out.println("The package is "+ path);
        return scanstore(path);
    }
    public List<String> scanstore(String filepath) throws IOException { // throw exception if can't find the file/package
        // find the file in the package
        File dir = new File(filepath);
        if (dir.isFile() && dir.getName().endsWith("java")){
            // substring get name(.java)
            int end = dir.getName().length() -5;
            String classname = dir.getName().substring(0,end);
//            System.out.println(classname);
            classlist.add(classname);
            pairs.put(classname,dir.getPath());
            return null;
        }

        if (dir.listFiles() == null) {
            return null;
        }
        // f is directory and exists
        // f is not empty
        for (File f : dir.listFiles()) {
            if (f.isDirectory()) {
//                System.out.println(f.getName());
                if (!classlist.containsAll(scanstore(f.getPath()))) {
                    classlist.addAll(scanstore(f.getPath()));
                }
            }
            else if (f.isFile() && f.getName().endsWith("java")){
                // substring get name(.java)
                int end = f.getName().length() -5;
                String classname = f.getName().substring(0,end);
//                System.out.println(classname);
                classlist.add(classname);
                pairs.put(classname,dir.getPath());
            }
        }
        return classlist;
    }
}
