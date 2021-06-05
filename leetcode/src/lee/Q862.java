package lee;

import java.awt.*;
import java.util.Arrays;

public class Q862 {
    public static void main(String[] args) {
        Q862 q= new Q862();
        System.out.println(q.maxProfitAssignment(new int[]{23, 30, 35, 35, 43, 46, 47, 81, 83, 98},
                new int[]{8, 11, 11, 20, 33, 37, 60, 72, 87, 95},
                new int[]{95, 46, 47, 97, 11, 35, 99, 56, 41, 92}));

        System.out.println(Arrays.binarySearch(new int[]{23, 30, 35, 35, 43, 46, 47, 81, 83, 98}, 35));
    }

    public int maxProfitAssignment(int[] difficulty, int[] profit, int[] worker) {
        int N = difficulty.length;
        Point[] jobs = new Point[N];
        for (int i = 0; i < N; ++i)
            jobs[i] = new Point(difficulty[i], profit[i]);
        Arrays.sort(jobs, (a, b) -> a.x - b.x);
        Arrays.sort(worker);

        int ans = 0, i = 0, best = 0;
        for (int skill: worker) {
            while (i < N && skill >= jobs[i].x)
                best = Math.max(best, jobs[i++].y);
            ans += best;
        }

        return ans;
    }

}
