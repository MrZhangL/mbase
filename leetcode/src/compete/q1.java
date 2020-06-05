package compete;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class q1 {

    public int isPrefixOfWord(String sentence, String searchWord) {
        String[] s = sentence.split(" ");
        boolean flag = true;
        int index = -1;
        for (int i = 0; i < s.length; i++) {
            flag = true;
            if(searchWord.length() >= s[i].length()){
                continue;
            }
            for (int j = 0; j < searchWord.length(); j++) {
                if(s[i].charAt(j) != searchWord.charAt(j)){
                    flag = false;
                    break;
                }
            }
            if(flag){
                index = i + 1;
                break;
            }
        }

        return index;
    }
}
