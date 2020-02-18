package main;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BasicMathTest {
    @Test
    void combination() {
        assertEquals(10, BasicMath.combination(5, 2));
        assertEquals(10, BasicMath.combination(5, 3));
        assertEquals(21, BasicMath.combination(7, 2));
        assertEquals(21, BasicMath.combination(7, 5));
        assertEquals(1, BasicMath.combination(5, 0));
        assertEquals(1, BasicMath.combination(5, 5));
    }
}
