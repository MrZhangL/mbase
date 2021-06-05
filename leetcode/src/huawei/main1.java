package huawei;

import java.awt.*;
import java.util.*;

public class main1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();

        int length = str.length();
        char[] s = str.toCharArray();
        LinkedList<Integer> postion = new LinkedList<>();

        for(int i = 0; i < length; i++) {
            if(s[i] == '(') {
                postion.addLast(i);
            }else if(s[i] == ')') {
                Integer idx = postion.pollLast();
                int l = idx + 1;
                int r = i - 1;
                while(l < r) {
                    char tmp = s[l];
                    s[l] = s[r];
                    s[r] = tmp;
                    l++;
                    r--;
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < length; i++) {
            if(s[i] != '(' && s[i] != ')') {
                sb.append(s[i]);
            }
        }
        System.out.println(sb);
    }
}