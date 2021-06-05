package lee;

import java.util.Arrays;
import java.util.Random;

public class qq {


    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5,6,7,8,1,2,3,4,0,1,2,5,12,12,14,10};
        int[] arr2 = {1,2,3,4,5,6,7,8,1,2,3,4,0,1,2,5,12,12,14,10};
        Arrays.sort(arr);

        System.out.println(quickSel(arr2, 4, 0, arr.length - 1));
        System.out.println(arr[arr.length - 5]);

    }

    static Random rand = new Random();
    static int quickSel(int[] scores, int x, int left, int right) {
        if(left >= right) {
            return left == x ? scores[left] : scores[right];
        }
        int l = left, r = right;
        int h = left + rand.nextInt(right - left + 1);

        swap(scores, left, h);
        while(l < r) {
            while (l < r && scores[l] >= scores[left]) l++;
            while(l < r && scores[r] < scores[left]) r--;

            swap(scores, l, r);
        }

        if(scores[l] < scores[left]) l--;
        swap(scores, left , l);

        if(x == l) return scores[l];
        if(l < x) return quickSel(scores, x, l + 1, right);
        else return quickSel(scores, x, left, l-1);
    }
    static void swap(int[] scores, int i, int j) {
        int tmp = scores[i];
        scores[i] = scores[j];
        scores[j] = tmp;
    }

}
