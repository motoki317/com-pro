package main;

class BasicMath {
    static int combination(int n, int r) {
        if (n == 0 && r == 0) return 1;
        if (n < r || n < 0) return 0;

        int ret = 1;
        for (int i = n; i > n - r; i--) {
            ret *= i;
        }
        for (int i = 1; i <= r; i++) {
            ret /= i;
        }
        return ret;
    }
}
