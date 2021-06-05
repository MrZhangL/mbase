import java.util.Scanner;

public class main5 {

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
/*        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] arr = new int[n];
        for(int i = 0; i < n; i++){
            arr[i] = sc.nextInt();
        }

        int mx = 0;
        int idx = 0;


        OUT:
        while(idx < n) {
            for(; idx < n; idx++) {
                if(idx == n - 1) break OUT;
            }
            if(arr[idx] < arr[idx + 1]) {
                break;
            }

            int start = idx;
            int i = idx + 1;
            for(; i < n; i++) {
                if(i == n -1) break OUT;
                if(arr[i] > arr[i + 1]) {
                    break;
                } else if(arr[i] == arr[i + 1]) {
                    idx = i + 1;
                    continue OUT;
                }
            }

            i++;
            for(; i < n; i++) {
                if(i == n -1 || arr[i] <= arr[i + 1]) break;
            }

            mx = Math.max(mx , i - start + 1);
            idx = 1;
        }

        System.out.println(mx);
    }*/
}
