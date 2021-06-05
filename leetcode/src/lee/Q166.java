package lee;

import java.util.HashMap;

public class Q166 {

    public static void main(String[] args) {
        Q166 q = new Q166();
        q.fractionToDecimal(-1, -2147483648);
    }

    public String fractionToDecimal(int numerator, int denominator) {
        if(numerator == 0) return "0";

        StringBuilder sb = new StringBuilder();
        if((numerator > 0 && denominator < 0) || (numerator < 0 && denominator > 0)) {
            sb.append('-');
        }
        long num = Math.abs((long)numerator);
        long den = Math.abs((long)denominator);

        sb.append(String.valueOf(num/den));
        num %= den;
        if(num != 0) {
            sb.append('.');
        }

        num *= 10;
        HashMap<Long, Integer> mp = new HashMap<>();

        Integer idx;
        while(num != 0) {
            if((idx = mp.get(num)) != null) {
                // 遇到相同的被除数，后面会循环
                sb.append("))");
                for(int i = sb.length() - 1; i > idx; i--) {
                    sb.setCharAt(i, sb.charAt(i-1));
                }
                sb.setCharAt(idx, '(');

                break;
            } else {
                mp.put(num, sb.length());
            }
            if(num < den) {
                sb.append('0');
                num *= 10;
            } else {
                sb.append((char)((int)(num/den) + '0'));
                num = (num%den)*10;
            }
        }

        return sb.toString();
    }
}
