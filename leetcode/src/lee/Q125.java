package lee;

public class Q125 {

    public static void main(String[] args) {
        Q125 q = new Q125();
        System.out.println(q.isPalindrome("A man, a plan, a canal: Panama"));
    }

    public boolean isPalindrome(String s) {
        int j = s.length() - 1;
        for(int i = 0; i < s.length() && i <= j; i++) {
            if(!judgeValid(s.charAt(i))) continue;
            while(j > i && !judgeValid(s.charAt(j))) j--;

            if(!equal(s.charAt(i), s.charAt(j))) return false;
            j--;
        }

        return true;
    }

    private boolean judgeValid(char c) {
        return Character.isDigit(c) || Character.isLetter(c);
    }

    private boolean equal(char c1, char c2) {
        if(c1 == c2) return true;
        if(c1 < c2 && Character.isLowerCase(c1) && c2 - c1 == 32) return true;
        if(c1 > c2 && Character.isLowerCase(c2) && c1 - c2 == 32) return true;

        return false;
    }
}
