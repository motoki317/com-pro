package main;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntPredicate;

import static org.junit.jupiter.api.Assertions.*;

class BinarySearchTest {
    @Test
    void partitionPoint() {
        class TestCase {
            private IntPredicate predicate;
            private int first;
            private int last;
            private int assertion;

            private TestCase(IntPredicate predicate, int first, int last, int assertion) {
                this.predicate = predicate;
                this.first = first;
                this.last = last;
                this.assertion = assertion;
            }
        }

        List<TestCase> cases = new ArrayList<>();

        {
            int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9};
            cases.add(new TestCase(
                    i -> arr[i] < 5, 0, arr.length, 4
            ));
        }

        {
            int[] arr = {2, 4, 6, 8, 1, 3, 5, 7, 9};
            cases.add(new TestCase(
                    i -> arr[i] % 2 == 0, 0, arr.length, 4
            ));
        }

        {
            int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9};
            cases.add(new TestCase(
                    // i -> false
                    i -> arr[i] < 0, 0, arr.length, 0
            ));
        }

        {
            int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9};
            cases.add(new TestCase(
                    // i -> true
                    i -> arr[i] < 10, 0, arr.length, arr.length
            ));
        }

        for (TestCase c : cases) {
            assertEquals(c.assertion, BinarySearch.partitionPoint(c.predicate, c.first, c.last));
        }
    }
}
