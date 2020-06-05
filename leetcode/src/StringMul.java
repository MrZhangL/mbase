public class StringMul {

    public static void main(String[] args) {
        char s = '2';

        int a = 1 + '0';
        System.out.println(a);
    }

    public String multiply(String num1, String num2) {
        String result = "0";
        String eveResult = null;
        for(int i = num2.length() - 1; i >= 0; i--){
            eveResult = multOne(num1, num2.charAt(i));
            result = add(result, eveResult, num2.length() - 1 - i);
        }
        StringBuilder str = new StringBuilder(result);
        int i = str.length();
        for(;i >1; i--){
            if(str.charAt(i-1) != '0'){
                break;
            }
        }
        if(i < str.length()) {
            str.delete(i, str.length());
        }
        return str.reverse().toString();
    }

    public String multOne(String s, char c){
        StringBuilder result = new StringBuilder();
        int forward = 0;
        int num = 0;
        for(int i = s.length() - 1; i >= 0; i--){
            num = (Integer.valueOf(s.charAt(i)) - Integer.valueOf('0'))*(Integer.valueOf(c) - Integer.valueOf('0')) + forward;
            forward = num/10;
            result.append((char)(num%10 + Integer.valueOf('0')));
        }

        if(forward != 0){
            result.append(forward);
        }

        return result.toString();
    }

    public String add(String s1, String s2, int zeroCount){
        int maxSize = Math.max(s1.length(), s2.length() + zeroCount);
        StringBuilder result = new StringBuilder();
        int forward = 0;
        int num = 0;

        for(int i = 0; i < maxSize; i++){
            num = forward;
            if(i < s1.length()){
                num += Integer.valueOf(s1.charAt(i)) - Integer.valueOf('0');
            }
            if(i >= zeroCount && i < s2.length() + zeroCount){
                num += Integer.valueOf(s2.charAt(i - zeroCount)) - Integer.valueOf('0');
            }


            forward = num/10;
            result.append((char)(num%10 + Integer.valueOf('0')));
        }

        return result.toString();
    }
}
