package Compare;

import Scanner.CollectClass;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Compare {
    private String pathold;
    private String pathnew;
    public Compare(String path1, String path2){
        File oldfile = new File(path1);
        File newfile = new File(path2);
        this.pathold = oldfile.getPath();
        this.pathnew = newfile.getPath();
    }
    public List<List<String>> compareclass() throws IOException {
        CollectClass collectold = new CollectClass(pathold);
        CollectClass collectnew = new CollectClass(pathnew);
        List<String> lostrold = collectold.getClasslist();
        List<String> lostrnew = collectnew.getClasslist();
        List<List<String>> adclass = new ArrayList<>();
        adclass.add(addclass(lostrold,lostrnew));
        adclass.add(deleteclass(lostrold,lostrnew));
        return adclass;
    }
    public List<String> addclass(List<String> oldone, List<String> newone){
        List<String> result = new ArrayList<>();
        for (String n : newone){
            if (!oldone.contains(n)){
                result.add(n);
            }
        }
        return result;
    }
    public List<String> deleteclass(List<String> oldone, List<String> newone){
        List<String> result = new ArrayList<>();
        for (String n : oldone){
            if (!newone.contains(n)){
                result.add(n);
            }
        }
        return result;
    }

}
