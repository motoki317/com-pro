package main;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class BasicArraysTest {
    @Test
    void reversed() {
        assert Arrays.equals(new int[]{5, 4, 3, 2, 1}, BasicArrays.reversed(new int[]{1, 2, 3, 4, 5}));
        assert Arrays.equals(new int[]{4, 3, 2, 1}, BasicArrays.reversed(new int[]{1, 2, 3, 4}));
    }
}
