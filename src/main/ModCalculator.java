package main;

import java.util.ArrayList;
import java.util.List;

/*
  Copy from here
 */

class ModCalculator {
    private final long mod;

    // factorial in mod
    // factMod[1] = 1, factMod[2] = 2, ...
    private List<Long> factorial;
    // inverse factorial in mod
    private List<Long> inverseFactorial;

    ModCalculator(long mod) {
        this.mod = mod;
        factorial = new ArrayList<>();
        inverseFactorial = new ArrayList<>();
        factorial.add(1L);
        inverseFactorial.add(1L);
    }

    private void populate(long n) {
        // i == next index
        for (int i = factorial.size(); i <= n; i++) {
            factorial.add(
                    multiply(factorial.get(i - 1), i)
            );
            inverseFactorial.add(
                    multiply(inverseFactorial.get(i - 1), inverse(i))
            );
        }
    }

    // a + b (mod m)
    long add(long a, long b) {
        return (a + b) % mod;
    }

    // a * b (mod m)
    long multiply(long a, long b) {
        return (a * b) % mod;
    }

    // x^-1 (mod m)
    long inverse(long x) {
        return power(x, mod - 2);
    }

    // x^n (mod m)
    long power(long x, long n) {
        long ans = 1;
        while (n != 0) {
            if ((n & 1) == 1L) ans = multiply(ans, x);
            x = multiply(x, x);
            n = n >> 1;
        }
        return ans;
    }

    /**
     * Calculates factorial value in mod.
     */
    long factorial(int n) {
        populate(n);
        return factorial.get(n);
    }

    /**
     * Calculates combination value in mod.
     * <br>a.k.a
     * <br>aCb
     * <br> = n! / (n - r)! r!
     */
    long combination(int n, int r) {
        if (n == 0 && r == 0) return 1;
        if (n < r || n < 0) return 0;

        populate(n);
        return multiply(factorial.get(n), multiply(inverseFactorial.get(n - r), inverseFactorial.get(r)));
    }

    /**
     * Calculates permutation value in mod.
     * <br>a.k.a
     * <br>aPb
     * <br> = n! / (n - r)!
     */
    long permutation(int n, int r) {
        if (n == 0 && r == 0) return 0;
        if (n < r || n < 0) return 0;

        populate(n);
        return multiply(factorial.get(n), inverseFactorial.get(n - r));
    }
}
