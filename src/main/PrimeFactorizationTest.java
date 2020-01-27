package main;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PrimeFactorizationTest {
    private static class TestCase {
        private final long num;
        private final Map<Long, Integer> factorization;
        private final boolean isPrime;

        private TestCase(long num, Map<Long, Integer> factorization, boolean isPrime) {
            this.num = num;
            this.factorization = factorization;
            this.isPrime = isPrime;
        }
    }

    private static List<TestCase> prepareTestCases() {
        List<TestCase> ret = new ArrayList<>();
        {
            Map<Long, Integer> fact = new HashMap<>();
            fact.put(2L, 3);
            fact.put(3L, 1);
            fact.put(5L, 1);
            ret.add(new TestCase(120, fact, false));
        }
        {
            Map<Long, Integer> fact = new HashMap<>();
            fact.put(7L, 1);
            ret.add(new TestCase(7, fact, true));
        }
        {
            Map<Long, Integer> fact = new HashMap<>();
            fact.put(7L, 3);
            ret.add(new TestCase(343, fact, false));
        }
        {
            Map<Long, Integer> fact = new HashMap<>();
            fact.put(2L, 2);
            fact.put(28_628_591L, 1);
            ret.add(new TestCase(114_514_364, fact, false));
        }
        {
            Map<Long, Integer> fact = new HashMap<>();
            fact.put(1_000_000_007L, 1);
            ret.add(new TestCase(1_000_000_007L, fact, true));
        }
        return ret;
    }

    @Test
    void testFactorization() {
        for (TestCase testCase : prepareTestCases()) {
            PrimeFactorization.PrimeFact pf = new PrimeFactorization.PrimeFact(testCase.num);
            assertEquals(testCase.factorization, pf.getFactorization());
            assertEquals(testCase.isPrime, pf.isPrime());
        }
    }
}
