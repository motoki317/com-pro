package main;

import java.util.Arrays;

public class Matrix {
    private static double[][] multiply(double[][] a, double[][] b) {
        int rows = a.length;
        int columns = b[0].length;
        if (a[0].length != b.length) {
            throw new Error("Warning: matrix dimensions do not match (multiplication)");
        }
        double[][] ret = new double[rows][columns];
        for (int i=0; i < rows; i++) {
            for (int j=0; j < columns; j++) {
                for (int k=0; k < b.length; k++) {
                    ret[i][j] += a[i][k] * b[k][j];
                }
            }
        }

        return ret;
    }

    private static double[][] subtract(double[][] a, double[][] b) {
        if (a.length != b.length || a[0].length != b[0].length) {
            throw new Error("Warning: matrix dimensions do not match (subtraction)");
        }
        double[][] ret = deepCopy(a);
        for (int i=0; i < a.length; i++) {
            for (int j=0; j < a[0].length; j++) {
                ret[i][j] -= b[i][j];
            }
        }
        return ret;
    }

    private static double[][] deepCopy(double[][] a) {
        double[][] ret = new double[a.length][a[0].length];
        for (int i=0; i < a.length; i++) {
            ret[i] = Arrays.copyOf(a[i], a[i].length);
        }
        return ret;
    }
}
