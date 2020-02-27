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

    static long gcd(long... a) {
        if (a.length == 0) return 1;
        long ret = a[0];
        for (int i = 1; i < a.length; i++) {
            ret = gcd(ret, a[i]);
        }
        return ret;
    }

    static long gcd(long a, long b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }

    static long lcm(long a, long b) {
        return (a / gcd(a, b)) * b;
    }
}
