package lee;

public class Q415 {

    public static void main(String[] args) {
        Q415 q = new Q415();
        System.out.println(q.addStrings("0", "0"));
    }

    public String addStrings(String num1, String num2) {
        StringBuilder sb = new StringBuilder();
        int jin = 0;
        int sum;

        int pre = num1.length() - 1;
        int ne = num2.length() - 1;

        for(; pre >= 0 && ne >= 0; pre--, ne--) {
            sum = num1.charAt(pre) + num2.charAt(ne) + jin - 2*'0';
            if(sum > 9) {
                jin = 1;
                sum -= 10;
            } else {
                jin = 0;
            }

            sb.append(sum);
        }

        while(pre >= 0) {
            sum = num1.charAt(pre) + jin - 2*'0';
            if(sum > 9) {
                jin = 1;
                sum -= 10;
            } else {
                jin = 0;
            }
            pre--;

            sb.append(sum);
        }
        while(ne >= 0) {
            sum = num2.charAt(ne) + jin - 2*'0';
            if(sum > 9) {
                jin = 1;
                sum -= 10;
            } else {
                jin = 0;
            }
            ne--;

            sb.append(sum);
        }

        if(jin != 0) {
            sb.append(1);
        }

        return sb.reverse().toString();
    }
}
