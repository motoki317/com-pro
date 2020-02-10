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

    /**
     * Quickly populate factorial and inverse factorial up to n
     */
    private void populate(int n) {
        // i == next index
        int start = factorial.size();
        for (int i = start; i <= n; i++) {
            factorial.add(
                    multiply(factorial.get(i - 1), i)
            );
            // temporarily add
            inverseFactorial.add(0L);
        }
        // avoid inverse calculation by filling inverseFactorial in the inverse direction
        inverseFactorial.set(n, inverse(factorial.get(n)));
        for (int i = n; i > start; i--) {
            inverseFactorial.set(i - 1, multiply(inverseFactorial.get(i), i));
        }
    }

    // a + b (mod m)
    long add(long a, long b) {
        return (a + b) % mod;
    }

    // a - b (mod m)
    long subtract(long a, long b) {
        return add(add(a, -b), mod);
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
     * Calculates inverse factorial value in mod.
     */
    long inverseFactorial(int n) {
        populate(n);
        return inverseFactorial.get(n);
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
