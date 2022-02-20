package lab2;

import java.util.Random;

public class Matrix {
    private double[] data;
    private int rows;
    private int cols;

    Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;

        this.data = new double[rows * cols];
    }

    Matrix(double[][] d) {
        this.rows = d.length;
        this.cols = 0;
        for (double[] row : d) {
            if (this.cols < row.length) {
                this.cols = row.length;
            }
        }

        this.data = new double[this.rows * this.cols];

        for (int i = 0; i < d.length; i++) {
            System.arraycopy(d[i], 0, this.data, (i * this.cols), d[i].length);
        }
    }

    public static Matrix random(int rows, int cols) {
        Matrix m = new Matrix(rows, cols);
        Random r = new Random();

        for (int row = 0; row < m.rows; row++) {
            for (int col = 0; col < m.cols; col++) {
                m.set(row, col, r.nextDouble());
            }
        }

        return m;
    }

    public static Matrix eye(int n) {
        Matrix m = new Matrix(n, n);
        for (int i = 0; i < n; i++) {
            m.set(i, i, 1);
        }

        return m;
    }

    public double[][] asArray() {
        double[][] a = new double[this.rows][this.cols];
        for (int i = 0; i < this.data.length; i++) {
            a[i / this.cols][i % this.cols] = this.data[i];
        }
        return a;
    }

    public double get(int row, int col) {
        if (row >= this.rows || col >= this.cols) {
            throw new RuntimeException("Cannot get given field");
        }
        return this.data[(row * this.cols) + col];
    }

    public void set(int row, int col, double value) {
        if (row >= this.rows || col >= this.cols) {
            throw new RuntimeException("Cannot set given field");
        }
        this.data[(row * this.cols) + col] = value;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        double[][] arr = this.asArray();
        for (double[] row : arr) {
            stringBuilder.append("[ ");
            for (double v : row) {
                stringBuilder.append(v);
                stringBuilder.append(" ");
            }
            stringBuilder.append("]");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public void reshape(int newRows, int newCols) {
        if (this.rows * this.cols != newRows * newCols) {
            throw new RuntimeException(String.format("%d x %d matrix can't be reshaped to %d x %d", this.rows, this.cols, newRows, newCols));
        }

        this.rows = newRows;
        this.cols = newCols;
    }

    public int[] shape() {
        return new int[]{this.rows, this.cols};
    }

    public Matrix add(Matrix m) {
        if (this.rows != m.rows || this.cols != m.cols) {
            throw new RuntimeException(String.format("%d x %d matrix can't be added to %d x %d matrix", this.rows, this.cols, m.rows, m.cols));
        }

        Matrix result = new Matrix(this.rows, this.cols);

        for (int i = 0; i < result.data.length; i++) {
            result.data[i] = this.data[i] + m.data[i];
        }

        return result;
    }

    public Matrix add(double w) {
        Matrix result = new Matrix(this.rows, this.cols);
        for (int i = 0; i < result.data.length; i++) {
            result.data[i] = this.data[i] + w;
        }

        return result;
    }

    public Matrix sub(Matrix m) {
        if (this.rows != m.rows || this.cols != m.cols) {
            throw new RuntimeException(String.format("%d x %d matrix can't be subtracted from %d x %d matrix", this.rows, this.cols, m.rows, m.cols));
        }

        Matrix result = new Matrix(this.rows, this.cols);

        for (int i = 0; i < result.data.length; i++) {
            result.data[i] = this.data[i] - m.data[i];
        }

        return result;
    }

    public Matrix sub(double w) {
        Matrix result = new Matrix(this.rows, this.cols);
        for (int i = 0; i < result.data.length; i++) {
            result.data[i] = this.data[i] - w;
        }

        return result;
    }

    public Matrix mul(Matrix m) {
        if (this.rows != m.rows || this.cols != m.cols) {
            throw new RuntimeException(String.format("%d x %d matrix can't be multiplied by %d x %d matrix", this.rows, this.cols, m.rows, m.cols));
        }

        Matrix result = new Matrix(this.rows, this.cols);

        for (int i = 0; i < result.data.length; i++) {
            result.data[i] = this.data[i] * m.data[i];
        }

        return result;
    }

    public Matrix mul(double w) {
        Matrix result = new Matrix(this.rows, this.cols);
        for (int i = 0; i < result.data.length; i++) {
            result.data[i] = this.data[i] * w;
        }

        return result;
    }

    public Matrix div(Matrix m) {
        if (this.rows != m.rows || this.cols != m.cols) {
            throw new RuntimeException(String.format("%d x %d matrix can't be divided by %d x %d matrix", this.rows, this.cols, m.rows, m.cols));
        }

        Matrix result = new Matrix(this.rows, this.cols);

        for (int i = 0; i < result.data.length; i++) {
            result.data[i] = this.data[i] / m.data[i];
        }

        return result;
    }

    public Matrix div(double w) {
        Matrix result = new Matrix(this.rows, this.cols);
        for (int i = 0; i < result.data.length; i++) {
            result.data[i] = this.data[i] / w;
        }

        return result;
    }

    public Matrix dot(Matrix m) {
        if (this.cols != m.rows) {
            throw new RuntimeException(String.format("%d x %d matrix can't be multiplied by %d x %d matrix", this.rows, this.cols, m.rows, m.cols));
        }

        Matrix result = new Matrix(this.rows, m.cols);

        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < m.cols; col++) {
                for (int i = 0; i < this.cols; i++) {
                    result.data[(row * m.cols) + col] += this.asArray()[row][i] * m.asArray()[i][col];
                }
            }
        }

        return result;
    }

    public double frobenius() {
        double sum = 0;
        for (double v : this.data) {
            sum += Math.pow(v, 2);
        }

        return Math.sqrt(sum);
    }
}
