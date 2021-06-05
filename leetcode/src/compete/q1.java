package compete;


import java.util.*;

public class q1 {

    public static void main(String[] args) {
        q1 q = new q1();
        int order = q.longestBeautifulSubstring("sadd");
        System.out.println(order);


    }

    //[[0,0],[1,1],[0,0],[2,0],[2,2],[1,1],[2,1],[0,1],[0,1]]

    HashMap<Character, Character> mp = new HashMap<>();

    public int longestBeautifulSubstring(String word) {
        int max = 0;
        Tuple dp = new Tuple(0, new char[]{'a', '*'});

        mp.put('a', 'e');
        mp.put('e', 'i');
        mp.put('i', 'o');
        mp.put('o', 'u');
        mp.put('u', 'a');

        for(int i = 0; i < word.length(); i++) {
            if(dp.need[0] == word.charAt(i) || dp.need[1] == word.charAt(i)) {
                dp.len++;
                dp.need[0] = mp.get(word.charAt(i));
                dp.need[1] = word.charAt(i);

                if(word.charAt(i) == 'u') {
                    max = Math.max(max, dp.len);
                }
            } else {
                if(word.charAt(i) == 'a') {
                    dp.len = 1;
                    dp.need[0] = mp.get(word.charAt(i));
                    dp.need[1] = word.charAt(i);
                } else {
                    dp.len = 0;
                    dp.need[0] ='a';
                    dp.need[1] = '*';
                }
            }
        }

        return max;
    }
}

class Tuple {
    int len;
    char[] need;

    public Tuple(int len, char[] need) {
        this.len = len;
        this.need = need;
    }
}

// x [15,14,13,1,6,3,12,5,8,11,9,4,10,7,0,2]
//   [15,14,13,1,6,3,5,12,8,11,9,4,10,7,0,2]
