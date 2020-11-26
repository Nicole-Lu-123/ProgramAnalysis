package Compare;

import Scanner.CollectClass;
import com.mycompany.app.CombineInfo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Compare {
    private String pathold;
    private String pathnew;
    public Map<String, List<String>> cbi1MethodMap;
    public Map<String, List<String>> cbi2MethodMap;
    public List<String> SameClassBetweenTwoCommit = new ArrayList<>();
    public Compare(String path1, String path2) throws IOException {
        File oldfile = new File(path1);
        File newfile = new File(path2);
        this.pathold = oldfile.getPath();
        this.pathnew = newfile.getPath();
        CombineInfo cbi1 = new CombineInfo(oldfile.getPath());
        cbi1.generate();
        CombineInfo cbi2 = new CombineInfo(newfile.getPath());
        cbi2.generate();
        cbi1MethodMap = cbi1.getmethodmap();
        cbi2MethodMap = cbi2.getmethodmap();

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
            SameClassBetweenTwoCommit.add(n);
            if (!oldone.contains(n)){
                result.add(n);

            }
        }
        for(String r: result){
            SameClassBetweenTwoCommit.remove(r);
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

    public Map<String, List<String>> methodAdd(){
        Map<String, List<String>> MwthodAlreadyAdd = new HashMap<>();
        List<String> result = new ArrayList<>();

        //go through the same class between two commit
        for(String str : SameClassBetweenTwoCommit){
            //get the method name in the second commit of same class
            for(String str2Commit : cbi2MethodMap.get(str)){
                // if the method in first commit did not contain the method for second in same class
                if(!cbi1MethodMap.get(str).contains(str2Commit)){
                    result.add(str2Commit);
                }
                MwthodAlreadyAdd.put(str,result);
            }
        }
        return MwthodAlreadyAdd;

    }

    public Map<String, List<String>> methodDelet(){
        Map<String, List<String>> MethodAlreadyDelete = new HashMap<>();
        List<String> result = new ArrayList<>();

        //go through the same class between two commit
        for(String str : SameClassBetweenTwoCommit){
            //get the method name in the first commit of same class
            for(String str1Commit : cbi1MethodMap.get(str)){
                // if the method in second commit did not contain the method for first in same class
                if(!cbi2MethodMap.get(str).contains(str1Commit)){
                    result.add(str1Commit);
                }
                MethodAlreadyDelete.put(str,result);
            }
        }
        return MethodAlreadyDelete;

    }

}
