package lee;

import java.util.Arrays;

public class Q452 {

    public static void main(String[] args) {
        Q452 q = new Q452();
        System.out.println(q.findMinArrowShots(new int[][]{{-2147483646, -2147483645}, {2147483646, 2147483647}}));
    }

    public int findMinArrowShots(int[][] points) {
        int index = 0;
        int right;
        int num = 0;

        Arrays.sort(points, (p1, p2) -> p1[0] < p2[0]? -1 : 1);

        while(index < points.length) {
            right = points[index][1];
            index++;
            while(index < points.length && points[index][0] <= right) {
                if(points[index][1] < right) {
                    right = points[index][1];
                }

                index++;
            }

            num++;
        }

        return num;
    }
}
