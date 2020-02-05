package main;

/**
 * Copy from here
 * (add "static" keyword as needed)
 */

class LongestCommonSubsequence {
    private String a;
    private String b;

    private int[][] table;

    LongestCommonSubsequence(String a, String b) {
        this.a = a;
        this.b = b;
        this.table = new int[a.length() + 1][b.length() + 1];
        this.calculate();
    }

    private void calculate() {
        for (int i = 1; i <= a.length(); i++) {
            for (int j = 1; j <= b.length(); j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    // If chars are the same, then append +1 to the LCS of [i-1][j-1]
                    table[i][j] = table[i - 1][j - 1] + 1;
                } else {
                    // Else, the longest of [i-1][j] and [i][j-1] applies
                    table[i][j] = Math.max(table[i - 1][j], table[i][j - 1]);
                }
            }
        }
    }

    /**
     * Get the LCS length of two strings.
     * @return LCS length.
     */
    int LCSLength() {
        return table[a.length()][b.length()];
    }

    /**
     * Get the actual LCS of two strings.
     * @return Longest Common Subsequence.
     */
    String LCS() {
        // Backtrack the table to get the LCS
        StringBuilder ret = new StringBuilder();
        int i = a.length();
        int j = b.length();
        while (i != 0 && j != 0) {
            if (a.charAt(i - 1) == b.charAt(j - 1)) {
                ret.insert(0, a.charAt(i - 1));
                i--;
                j--;
            } else {
                if (table[i - 1][j] > table[i][j - 1]) {
                    i--;
                } else {
                    j--;
                }
            }
        }
        return ret.toString();
    }
}
