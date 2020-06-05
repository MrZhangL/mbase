package week1;

import java.util.Arrays;
import java.util.Comparator;

public class Q2 {
    public String arrangeWords(String text) {
        text = text.toLowerCase();
        String[] txt = text.split(" ");

        Arrays.sort(txt, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.length() - o2.length();
            }
        });

        txt[0] = txt[0].substring(0,1).toUpperCase() + txt[0].substring(1, txt[0].length()) ;

        StringBuilder rt = new StringBuilder();
        for (int i = 0; i < txt.length - 1; i++) {
            rt.append(txt[i] + " ");
        }
        rt.append(txt[txt.length - 1]);

        return rt.toString();
    }

}
