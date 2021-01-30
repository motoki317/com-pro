package main;

/**
 * Binary Indexed Tree
 * https://www.geeksforgeeks.org/binary-indexed-tree-or-fenwick-tree-2/
 * https://scrapbox.io/pocala-kyopro/%E8%BB%A2%E5%80%92%E6%95%B0
 */
public class BIT {
    private final int n;
    private final int[] tree;

    /**
     * Constructs a new binary-indexed tree that can be treated as arr[n].
     * @param n Maximum n (exclusive).
     */
    public BIT(int n) {
        this.n = n;
        this.tree = new int[n+1];
    }

    /**
     * Returns the sum of arr[0..index].
     * @param i Index (inclusive)
     * @return The sum
     */
    public int sum(int i) {
        int sum = 0;
        // To 1-indexed
        for (int x = i+1; x > 0; x -= x & -x) sum += tree[x];
        return sum;
    }

    /**
     * Adds w to arr[i].
     * @param i Index.
     * @param w Weight to add.
     */
    public void add(int i, int w) {
        // To 1-indexed
        for (int x = i+1; x <= n; x += x & -x) tree[x] += w;
    }
}
