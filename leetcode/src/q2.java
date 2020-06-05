import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class q2 {

    public static void main(String[] args) {
        String s = "ibpbhixfiouhdljnjfflpapptrxgcomvnb";
        System.out.println(s.length());

        q2 q = new q2();

        System.out.println(q.maxVowels(s, 33));
    }
    public int maxVowels(String s, int k) {
        int max = 0;
        int pre = 0;
        HashSet<Character> set = new HashSet<>();
        set.add('a');
        set.add('e');
        set.add('i');
        set.add('o');
        set.add('u');

        for(int i = 0; i < k; i++){
            if(set.contains(s.charAt(i))){
                pre++;
            }
        }
        max = pre;
        for (int i = 1; i <= s.length() - k ; i++) {
            if(set.contains(s.charAt(i-1))){
                pre--;
            }
            if(set.contains(s.charAt(i+k-1))){
                pre++;
            }

            max = Math.max(pre, max);
        }

        return max;
    }
}
