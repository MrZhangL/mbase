package lee;

public class Q48 {

    public static void main(String[] args) {
        Q48 q = new Q48();
        q.rotate(new int[][]{{ 5, 1, 9,11},{ 2, 4, 8,10},{13, 3, 6, 7},{15,14,12,16}});
    }

    public void rotate(int[][] matrix) {
        int n = matrix.length;
        int tmp, tmp2, r, c;
        int st, b;
        for(int i = 0; i < n/2; i++) {
            st = n - 2*i - 1;
            b = n - i - 1;
            for(int j = i; j <= b - 1; j++) {
                tmp = matrix[i][j];
                // 顺时针走4次
                // 走n-i-1步
                r = i + st - (b - j);
                tmp2 = matrix[r][b];
                matrix[r][b] = tmp;
                tmp = tmp2;
                // 走n-i-1
                c = 2*b - st - r;
                tmp2 = matrix[b][c];
                matrix[b][c] = tmp;
                tmp = tmp2;
                // n-i-1
                r = b - (st - (c - i));
                tmp2 = matrix[r][i];
                matrix[r][i] = tmp;
                tmp = tmp2;
                // n - i - 1
                matrix[i][j] = tmp;
            }
        }
    }
}
