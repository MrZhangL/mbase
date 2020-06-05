package week1;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Q4 {

    public static void main(String[] args) {
        int[][] points = {{-3,0},{3,0},{2,6},{5,4},{0,9},{7,8}};
        Q4 q = new Q4();
        System.out.println(q.numPoints(points, 5));
    }

    public int numPoints(int[][] points, int r) {
        List<Point> pointList = new ArrayList<>();
        Point p  = null;
        for (int i = 0; i < points.length; i++) {
            p = new Point(points[i][0], points[i][1]);
            pointList.add(p);
        }

        int size = pointList.size();
        Point p1;
        Point p2;
        double mmax1;
        double mmax2;
        r *= 2;
        for (int i = 0; i < size; i++) {
            int[] two = maxDisC(pointList);
            if(pointList.get(two[0]).dis(pointList.get(two[1])) <= r){
                return pointList.size();
            }
            p1 = pointList.get(two[0]);
            p2 = pointList.get(two[1]);
            if(two[0] > two[1]) {
                pointList.remove(two[0]);
                pointList.remove(two[1]);
            } else {
                pointList.remove(two[1]);
                pointList.remove(two[0]);
            }
            mmax1 = maxDisToG(p1, pointList);
            mmax2 = maxDisToG(p2, pointList);

            if(mmax1 > mmax2){
                if(mmax2 <= r){
                    pointList.add(p2);
                }
            } else if(mmax1 <= mmax2) {
                if(mmax1 <= r){
                    pointList.add(p1);
                }
            }
        }

        return 1;
    }

    private double maxDisToG(Point p, List<Point> pointList){
        double max = 0;
        for (int i = 0; i < pointList.size(); i++) {
            max = Math.max(p.dis(pointList.get(i)), max);
        }

        return max;
    }

    private int[] maxDisC(List<Point> pointList) {
        double maxdis = 0;
        int[] two = new int[2];
        for(int i = 0; i < pointList.size() - 1; i++){
            for(int j = i + 1; j < pointList.size(); j++){
                double dis = pointList.get(i).dis(pointList.get(j));
                if(dis > maxdis){
                    maxdis = dis;
                    two[0] = i;
                    two[1] = j;
                }
            }
        }
        return two;
    }

    class Point{
        int x;
        int y;
        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }

        public double dis(Point p){
            double dis = Math.sqrt((x - p.x)*(x - p.x) + (y - p.y)*(y - p.y));
            return dis;
        }
    }
}
