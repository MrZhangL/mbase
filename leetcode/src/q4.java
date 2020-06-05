import java.util.*;

public class q4 {

    public static void main(String[] args) {
        q4 q = new q4();
        System.out.println(q.restoreIpAddresses("255255255255"));

    }

    List<String> rtS = new ArrayList<>();

    public List<String> restoreIpAddresses(String s) {
        dfs(s, new StringBuilder(), 1);

        return rtS;
    }

    private boolean dfs(String s, StringBuilder sb, int n){
        if(s.length() < 5 - n || s.length() > 3*(5-n)){
            return false;
        }

        if(n == 4 && Integer.parseInt(s) <= 255 && !(s.length() > 1 && s.charAt(0) == '0')){
            rtS.add(sb.append(s).toString());
            sb.delete(sb.length() - s.length(), sb.length());
            return true;
        }

        String sub;
        boolean flag = false;
        for(int i = 1; i < 4 && i < s.length(); i++){
            sub = s.substring(0,i);
            if(Integer.parseInt(sub.toString()) > 255 || (i > 1 && sub.charAt(0) == '0')){
                return flag;
            }

            flag = dfs(s.substring(i, s.length()),
                    sb.append(sub + "."), n+1);

            sb.delete(sb.length() - i - 1, sb.length());
        }

        return flag;
    }
}
