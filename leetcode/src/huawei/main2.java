package huawei;

import java.util.*;

public class main2 {
    public static void main(String[] args) {

        Stack<Integer> st = new Stack<>();
        Stack<Integer> st1 = new Stack<>();
        st.push(1);
        st1.push(2);
        st1.push(3);
        st1.push(4);

        st.addAll(st1);
        while(!st.isEmpty()) {
            System.out.println(st.pop());
        }
    }
}
