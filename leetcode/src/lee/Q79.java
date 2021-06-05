package lee;

import java.util.PriorityQueue;

public class Q79 {

    public static void main(String[] args) {
        Q79 q = new Q79();
        System.out.println(q.exist(new char[][]{{'A', 'B', 'C', 'E'}, {'S', 'F', 'C', 'S'}, {'A', 'D', 'E', 'E'}}, "ABCCED"));
    }

    int[][] dirs = {{1,0}, {-1,0}, {0,1}, {0,-1}};
    boolean[][] visited;
    char[][] board;

    public boolean exist(char[][] board, String word) {
        visited = new boolean[board.length][board[0].length];
        this.board = board;

        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[0].length; j++) {
                if(dfs(0, 0, word, 0)) return true;
            }
        }

        return false;
    }

    private boolean dfs(int x, int y, String word, int index) {
        if(index == word.length() - 1) return word.charAt(index) == board[x][y];
        if(word.charAt(index) != board[x][y]) return false;
        visited[x][y] = true;
        for(int[] dir : dirs) {
            int newx = dir[0] + x;
            int newy = dir[1] + y;
            if(check(newx, newy) && !visited[x][y] && dfs(newx, newy, word, index + 1)) return true;
        }

        visited[x][y] = false;
        return false;
    }

    private boolean check(int x, int y) {
        return x >= 0 && x < board.length && y >= 0 && y < board[0].length;
    }
}
