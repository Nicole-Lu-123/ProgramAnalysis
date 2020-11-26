package Compare;

import Scanner.CollectClass;
import com.mycompany.app.CombineInfo;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Compare {
    public CombineInfo cbi1;
    public CombineInfo cbi2;
    public List<String> lostrold;
    public List<String> lostrnew;
    public Map<String, List<String>> cbi1MethodMap;
    public Map<String, List<String>> cbi2MethodMap;
    public List<String> SameClassBetweenTwoCommit = new ArrayList<>();

    public Compare(String path1, String path2) throws IOException {
        File oldfile = new File(path1);
        File newfile = new File(path2);
        this.cbi1 = new CombineInfo(oldfile.getPath());
        this.cbi2 = new CombineInfo(newfile.getPath());
        cbi1.generate();
        cbi2.generate();
        lostrold = cbi1.classinfo;
        lostrnew = cbi2.classinfo;
        cbi1MethodMap = cbi1.getmethodmap();
        cbi2MethodMap = cbi2.getmethodmap();
        for (String r : lostrnew) {
            if (lostrold.contains(r))
            SameClassBetweenTwoCommit.add(r);
        }
    }

    public List<List<String>> compareClass(){
        List<List<String>> changedclass = new ArrayList<>();
        changedclass.add(addclass(lostrold, lostrnew));
        changedclass.add(deleteclass(lostrold, lostrnew));
        return changedclass;
    }
    public List<Map<String, List<String>>> compareMethod(){
        List<Map<String, List<String>>> changedmethods = new ArrayList<>();
        changedmethods.add(methodAdd());
        changedmethods.add(methodDelet());
        return changedmethods;
    }
    public List<Map<String, List<String>>> compareExtend(){
        List<Map<String, List<String>>> changedextend = new ArrayList<>();
        changedextend.add(addextend());
        changedextend.add(removeextend());
        return changedextend;
    }
    public List<Map<String, List<String>>> compareInterface(){
        List<Map<String, List<String>>> changedinterface = new ArrayList<>();
        changedinterface.add(addinterface());
        changedinterface.add(removeinterface());
        return changedinterface;
    }
    public List<Map<String, List<String>>> compareDep(){
        List<Map<String, List<String>>> changeddep = new ArrayList<>();
        changeddep.add(adddep());
        changeddep.add(removedep());
        return changeddep;
    }

    public List<String> addclass(List<String> oldone, List<String> newone) {
        List<String> result = new ArrayList<>();
        for (String n : newone) {
            if (!oldone.contains(n)) {
                result.add(n);
            }
        }

        return result;
    }

    public List<String> deleteclass(List<String> oldone, List<String> newone) {
        List<String> result = new ArrayList<>();
        for (String n : oldone) {
            if (!newone.contains(n)) {
                result.add(n);
            }
        }
        return result;
    }

    public Map<String, List<String>> methodAdd() {
        Map<String, List<String>> MwthodAlreadyAdd = new HashMap<>();
        List<String> result = new ArrayList<>();
        //go through the same class between two commit
        for (String str : SameClassBetweenTwoCommit) {
            //get the method name in the second commit of same class
            for (String str2Commit : cbi2MethodMap.get(str)) {
                // if the method in first commit did not contain the method for second in same class
                if (!cbi1MethodMap.get(str).contains(str2Commit)) {
                    result.add(str2Commit);
                }
                MwthodAlreadyAdd.put(str, result);
            }
        }
        return MwthodAlreadyAdd;

    }

    public Map<String, List<String>> methodDelet() {
        Map<String, List<String>> MethodAlreadyDelete = new HashMap<>();
        List<String> result = new ArrayList<>();

        //go through the same class between two commit
        for (String str : SameClassBetweenTwoCommit) {
            //get the method name in the first commit of same class
            for (String str1Commit : cbi1MethodMap.get(str)) {
                // if the method in second commit did not contain the method for first in same class
                if (!cbi2MethodMap.get(str).contains(str1Commit)) {
                    result.add(str1Commit);
                }
                MethodAlreadyDelete.put(str, result);
            }
        }
        return MethodAlreadyDelete;

    }

    public Map<String, List<String>> addextend() {
        Map<String, List<String>> addedextend = cbi2.classextendinfo;
        for (String common : addedextend.keySet()) {
            if (lostrold.contains(common)) {
                List<String> newextend = cbi2.classextendinfo.get(common);
                for (String e : newextend) {
                    if (cbi1.classextendinfo.get(common).contains(e)) {
                        addedextend.get(common).remove(e);
                    }
                }
            }
        }
        return addedextend;
    }

    public Map<String, List<String>> removeextend() {
        Map<String, List<String>> removedextend = new HashMap<>();
        for (String common : SameClassBetweenTwoCommit) {
            List<String> oldextend = cbi1.classextendinfo.get(common);
            List<String> newextend = cbi2.classextendinfo.get(common);
            List<String> store = new ArrayList<>();
            for (String e : oldextend) {
                if (!newextend.contains(e)) {
                    store.add(e);
                }
            }
            removedextend.put(common,store);
        }
        return removedextend;
}

    public Map<String, List<String>> addinterface() {
        Map<String, List<String>> addedinter = cbi2.classimpleinfo;
        for (String common : addedinter.keySet()) {
            if (lostrold.contains(common)) {
                List<String> newextend = cbi2.classimpleinfo.get(common);
                for (String e : newextend) {
                    if (cbi1.classimpleinfo.get(common).contains(e)) {
                        addedinter.get(common).remove(e);
                    }
                }
            }
        }
        return addedinter;
    }

    public Map<String, List<String>> removeinterface() {
        Map<String, List<String>> removeinter = new HashMap<>();
        for (String common : SameClassBetweenTwoCommit) {
            List<String> oldextend = cbi1.classimpleinfo.get(common);
            List<String> newextend = cbi2.classimpleinfo.get(common);
            List<String> store = new ArrayList<>();
            for (String e : oldextend) {
                if (!newextend.contains(e)) {
                    store.add(e);
                }
            }
            removeinter.put(common,store);
        }
        return removeinter;
    }

    public Map<String, List<String>> adddep() {
        Map<String, List<String>> addeddep = cbi2.classdependinfo;
        for (String common : addeddep.keySet()) {
            if (lostrold.contains(common)) {
                List<String> newextend = cbi2.classdependinfo.get(common);
                for (String e : newextend) {
                    if (cbi1.classdependinfo.get(common).contains(e)) {
                        addeddep.get(common).remove(e);
                    }
                }
            }
        }
        return addeddep;
    }

    public Map<String, List<String>> removedep() {
        Map<String, List<String>> removedep = new HashMap<>();
        for (String common : SameClassBetweenTwoCommit) {
            List<String> oldextend = cbi1.classdependinfo.get(common);
            List<String> newextend = cbi2.classdependinfo.get(common);
            List<String> store = new ArrayList<>();
            for (String e : oldextend) {
                if (!newextend.contains(e)) {
                    store.add(e);
                }
            }
            removedep.put(common,store);
        }
        return removedep;
    }

}
