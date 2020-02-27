package main;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static main.BasicMath.*;

class BasicMathTest {
    @Test
    void testCombination() {
        assertEquals(10, combination(5, 2));
        assertEquals(10, combination(5, 3));
        assertEquals(21, combination(7, 2));
        assertEquals(21, combination(7, 5));
        assertEquals(1, combination(5, 0));
        assertEquals(1, combination(5, 5));
    }

    @Test
    void testGcd() {
        assertEquals(1, gcd(5, 7));
        assertEquals(1, gcd(7, 5));
        assertEquals(2, gcd(2, 4));
        assertEquals(2, gcd(4, 2));
        assertEquals(5, gcd(5, 5));
        assertEquals(3, gcd(6, 15));
        assertEquals(3, gcd(15, 6));
    }

    @Test
    void testLcm() {
        assertEquals(35, lcm(5, 7));
        assertEquals(35, lcm(7, 5));
        assertEquals(24, lcm(6, 8));
        assertEquals(24, lcm(8, 6));
    }
}
