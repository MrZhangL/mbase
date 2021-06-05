package lee;

public class Q424 {

    public static void main(String[] args) {
        Q424 q = new Q424();
        System.out.println(q.characterReplacement("AABABBA", 2));
    }

    public int characterReplacement(String s, int k) {
        int[] cnt = new int[26];

        int cm = s.charAt(0) - 'A';
        cnt[cm] = 1;

        int left = 0, right = 1;

        int max = 1;
        while(right < s.length()) {
            int cur = s.charAt(right) - 'A';
            cnt[cur]++;

            if(cnt[cur] > cnt[cm]) {
                cm = cur;
            }

            if(cnt[cm] + k >= right - left + 1) {
                max = Math.max(right - left + 1, max);
                right++;
            } else {
                if(s.charAt(left) - 'A' == cm) {
                    cnt[cm]--;
                    cm = findMax(cnt);
                } else {
                    cnt[s.charAt(left) - 'A']--;
                }
                left++;
            }
        }

        return max;
    }

    int findMax(int[] cnt) {
        int idx = 0;
        for(int i = 0; i < cnt.length; i++) {
            if(cnt[i] > cnt[idx]) {
                idx = i;
            }
        }

        return idx;
    }
}
