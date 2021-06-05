import  java.util.*;

public class main4 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        String s = String.valueOf(n);

        if(s.indexOf('1') != -1 || s.indexOf('0') != -1) {
            System.out.println(n);
        } else {
            for(int i = 1; i < 100; i++) {
                int k = i * n;
                if(isValid(k)) {
                    System.out.println(k);

                }
            }
        }

    }

    public static boolean isValid(int n) {
        String s = String.valueOf(n);
        Map<Character,Integer> map = new HashMap<>();
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(c != '1' && c != '0') {
                map.put(c,map.getOrDefault(c,0) + 1);
                int size = map.size();
                if(size >= 2) {
                    return false;
                }
            }
        }
        return true;
    }
}
