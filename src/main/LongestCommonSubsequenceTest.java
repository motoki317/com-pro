package main;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LongestCommonSubsequenceTest {
    @Test
    void testLCS() {
        LongestCommonSubsequence LCS = new LongestCommonSubsequence("AGCAT", "GAC");
        assertEquals(2, LCS.LCSLength());
        String[] LCSs = new String[]{"AC", "GC", "GA"};
        String calculated = LCS.LCS();
        for (String l : LCSs) {
            if (calculated.equals(l)) {
                return;
            }
        }
        System.out.println(calculated);
        assert false;
    }
}
