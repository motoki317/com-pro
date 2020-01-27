package main;

import java.util.*;

/**
 * For copy and paste to Main.java
 */

class PrimeFactorization {
    /*
      Copy from here
     */

    static class PrimeFact {
        private final long orig;
        private Map<Long, Integer> factorization;

        PrimeFact(long num) {
            orig = num;
            factorization = factorize(num);
        }

        PrimeFact(int num) {
            orig = num;
            factorization = factorize(num);
        }

        /**
         * Returns a list of divisors, including.
         * @return List of divisors.
         */
        public List<Long> divisors() {
            return getDivisors(this.factorization);
        }

        /**
         * Checks if the internal factorization map contains only one value,
         * and it is num itself & corresponding power equals 1.
         * @return {@code true} if prime.
         */
        boolean isPrime() {
            return this.factorization.size() == 1
                    && this.factorization.entrySet().stream().findFirst().get().getValue() == 1;
        }

        /**
         * Returns a factorization map inside.
         */
        public Map<Long, Integer> getFactorization() {
            return factorization;
        }
    }

    private static Map<Long, Integer> factorize(final long num) {
        Map<Long, Integer> fact;

        if (num == 1L) return new HashMap<>();

        long sqrt = (long) Math.floor(Math.sqrt(num));
        if (sqrt > primes.getLast()) {
            generatePrimes(sqrt);
        }

        for (long p : primes) {
            if (p > sqrt) break;
            if (num % p == 0) {
                fact = factorize(num / p);
                if (fact.containsKey(p)) fact.put(p, fact.get(p) + 1);
                else fact.put(p, 1);
                return fact;
            }
        }

        // given num is a prime number
        fact = new HashMap<>();
        fact.put(num, 1);
        return fact;
    }

    private static LinkedList<Long> primes;

    static {
        primes = new LinkedList<>();
        primes.add(2L);
    }

    /**
     * Generates primes and stores it into static `primes` field.
     * Stops if the last generated prime is larger than the given `min`.
     * @param min Minimum number. Usually sqrt of the number that we want to factorize.
     */
    private static void generatePrimes(long min) {
        long p = primes.getLast() + 1;
        while (true) {
            if (isPrime(p)) {
                primes.add(p);
                if (p > min) {
                    return;
                }
            }
            p++;
        }
    }

    /**
     * Checks if the number is a prime using the current `primes` cache.
     * Stops when the next prime is larger than floor(sqrt(num)).
     * @param num Number to check.
     * @return {@code true} if num is prime.
     */
    private static boolean isPrime(long num) {
        int sqrt = (int) Math.floor(Math.sqrt(num));

        for (long p : primes) {
            if (p > sqrt) break;
            if (num % p == 0) return false;
        }

        return true;
    }

    // ------------------------
    // Get Divisors List
    // ------------------------

    /**
     * Returns a list of divisors, including 1 and number itself.
     * @param fact Factorization map.
     * @return List of divisors.
     */
    private static List<Long> getDivisors(Map<Long, Integer> fact) {
        List<Long> ret = new ArrayList<>();
        if (fact.isEmpty()) {
            ret.add(1L);
            return ret;
        }

        for (Map.Entry<Long, Integer> e : fact.entrySet()) {
            Map<Long, Integer> copy = clone(fact);
            copy.remove(e.getKey());
            List<Long> divs = getDivisors(copy);
            for (long div : divs) {
                for (int i = 0; i <= e.getValue(); i++) {
                    ret.add(div * (long) Math.pow(e.getKey(), i));
                }
            }
            break;
        }
        return ret;
    }

    /**
     * Clones map h into a new HashMap.
     * @param m Map to clone.
     * @return Cloned map.
     */
    private static Map<Long, Integer> clone(Map<Long, Integer> m) {
        Map<Long, Integer> ret = new HashMap<>();
        for (Map.Entry<Long, Integer> e : m.entrySet()) {
            ret.put(e.getKey(), e.getValue());
        }
        return ret;
    }
}
