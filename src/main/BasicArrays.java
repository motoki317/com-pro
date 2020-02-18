package main;

class BasicArrays {
    static int[] reversed(int[] a) {
        int n = a.length;
        for (int i = 0; i < n / 2; i++) {
            int tmp = a[n - i - 1];
            a[n - i - 1] = a[i];
            a[i] = tmp;
        }
        return a;
    }
}
