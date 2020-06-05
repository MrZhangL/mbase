package week1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Q3 {
    public List<Integer> peopleIndexes(List<List<String>> favoriteCompanies) {
        HashSet<Integer> sun = new HashSet<>();
        List<Integer> rt = new ArrayList<>();
        List<Set<String>> companiesSet = new ArrayList<>();

        Set<String> cSet = null;
        for(List<String> l : favoriteCompanies){
            cSet = new HashSet<>();
            for(String c : l){
                cSet.add(c);
            }
            companiesSet.add(cSet);
        }
        boolean containFlag = false;

        for (int i = 0; i < favoriteCompanies.size() - 1; i++) {
            for(int j = i + 1; j < favoriteCompanies.size(); j++){
                if(favoriteCompanies.get(i).size() == favoriteCompanies.get(j).size()){
                    continue;
                }
                else if(favoriteCompanies.get(i).size() > favoriteCompanies.get(j).size()){
                    containFlag = true;
                    for(String com : companiesSet.get(j)){
                        if(!companiesSet.get(i).contains(com)){
                            containFlag = false;
                            break;
                        }
                    }
                    if(!containFlag){
                        continue;
                    } else {
                        sun.add(j);
                    }
                }
                else {
                    containFlag = true;
                    for(String com : companiesSet.get(i)){
                        if(!companiesSet.get(j).contains(com)){
                            containFlag = false;
                            break;
                        }
                    }
                    if(!containFlag){
                        continue;
                    } else {
                        sun.add(i);
                    }
                }
            }
        }

        for (int i = 0; i < favoriteCompanies.size(); i++) {
            if(!sun.contains(i)){
                rt.add(i);
            }
        }

        return rt;
    }
}
