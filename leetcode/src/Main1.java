import java.util.*;


public class Main1 {

    public static void main(String[] args) {
        long[][] logs = new long[][]{{7,9},{9,16},{8,15}};
        long[] tss = new long[] {1,8,9};
        long i = 100000000000L;
        search_log(logs,tss);
        System.out.println(1);

    }
    public static int[][] search_log (long[][] logs, long[] tss) {
        // write code here
        int logsSize = logs.length;
        int tssSize = tss.length;

        int[][] res = new int[tssSize][];

        for(int i = 0; i < tssSize; i++) {
            int count = 0;
            for(int j = 0; j < logsSize; j++) {
                if(logs[j][0] <= tss[i] && tss[i] <= logs[j][1]) {
                    res[i][count] = j;
                    count++;
                }
            }
        }


        return res;
    }
}
