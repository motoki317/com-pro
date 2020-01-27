package main;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModCalculatorTest {
    @Test
    void testLargeMod() {
        ModCalculator calc = new ModCalculator(1_000_000_007L);

        assertEquals(30, calc.multiply(5, 6));
        assertEquals(37_789_891, calc.multiply(112_345_678, 981_736_581));

        assertEquals(1024, calc.power(2, 10));
        assertEquals(884_482_001, calc.power(12345, 12345));

        assertEquals(120, calc.factorial(5));
        assertEquals(579_592_771, calc.factorial(12345));

        assertEquals(20, calc.permutation(5, 2));
        assertEquals(967_387_674, calc.permutation(56789, 12345));

        assertEquals(10, calc.combination(5, 2));
        assertEquals(91_605_610, calc.combination(56789, 12345));
    }

    @Test
    void testSmallMod() {
        ModCalculator calc = new ModCalculator(7);

        assertEquals(6, calc.multiply(2, 3));
        assertEquals(2, calc.multiply(5, 6));

        assertEquals(4, calc.inverse(2));
        assertEquals(2, calc.inverse(4));
        assertEquals(5, calc.inverse(3));
        assertEquals(3, calc.inverse(5));

        assertEquals(6, calc.permutation(5, 2));
        assertEquals(3, calc.combination(5, 2));
    }
}
