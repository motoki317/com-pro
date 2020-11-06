package main;

import java.util.function.IntPredicate;
import java.util.function.LongPredicate;

class BinarySearch {
    /**
     * Examines the partitioned (as if by std::partition) range [first, last) and locates the end of the first partition,
     * that is, the first element that does not satisfy p or last if all elements satisfy p.
     * <br>Doc copied from c++ ref for std::partition_point
     * <br>https://en.cppreference.com/w/cpp/algorithm/partition_point
     * @param predicate unary predicate which returns true for the elements found in the beginning of the range.
     * @param first the partitioned range of elements to examine
     * @param last the partitioned range of elements to examine
     * @return The iterator past the end of the first partition within [first, last) or last if all elements satisfy p.
     */
    static long partitionPoint(LongPredicate predicate, long first, long last) {
        long left = first, right = last;
        while (left + 1 < right) {
            long mid = (left + right) / 2;
            if (predicate.test(mid)) {
                left = mid;
            } else {
                right = mid;
            }
        }
        return predicate.test(left) ? right : left;
    }

    static int partitionPoint(IntPredicate predicate, int first, int last) {
        int left = first, right = last;
        while (left + 1 < right) {
            int mid = (left + right) / 2;
            if (predicate.test(mid)) {
                left = mid;
            } else {
                right = mid;
            }
        }
        return predicate.test(left) ? right : left;
    }
}
