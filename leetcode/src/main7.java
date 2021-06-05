import java.util.Scanner;

public class main7 {
    public static void main(String[] args) {


        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();

        System.out.println(find(n,m));
    }

    public static  int find(int n, int m) {
        int max = -1;
        int flag1 = 1;

        for(int i = 2; i <= m; i++) {
            while(m % i == 0) {
                m = m /i;
                if(max < i) {
                    max = i;
                    flag1 = 1;
                }else if(max == i) {
                    flag1++;
                }
            }
        }

        int flag2 = 0;
        int temp;

        for(int i = 1; i <= n; i++) {
            temp = i;

            while(temp % max == 0) {
                temp /= max;
                flag2++;
            }
        }
        flag2 = flag2 / flag1;
        return flag2;
    }
}
