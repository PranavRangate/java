import java.util.Scanner;

class Mat{
    int[][] a;
    int[][] b;
    int rows, cols;

    Scanner sc = new Scanner(System.in);

    Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        a = new int[rows][cols];
        b = new int[rows][cols];
    }

    void setMatrices() {
        System.out.println("Enter 1st matrix elements:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                a[i][j] = sc.nextInt();
            }
        }

        System.out.println("Enter 2nd matrix elements:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                b[i][j] = sc.nextInt();
            }
        }
    }

    void printMatrices() {
        System.out.println("1st matrix:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(a[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("2nd matrix:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(b[i][j] + " ");
            }
            System.out.println();
        }
    }

    void addMatrices() {
        System.out.println("Addition of matrices:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int c = a[i][j] + b[i][j];
                System.out.print(c + " ");
            }
            System.out.println();
        }
    }

    void subtractMatrices() {
        System.out.println("Subtraction of matrices:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int c = a[i][j] - b[i][j];
                System.out.print(c + " ");
            }
            System.out.println();
        }
    }

    void multiplyMatrices() {
        System.out.println("Multiplication of matrices:");
        int[][] c = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                c[i][j] = 0;
                for (int k = 0; k < cols; k++) {
                    c[i][j] += a[i][k] * b[k][j];
                }
                System.out.print(c[i][j] + " ");
            }
            System.out.println();
        }
    }
}

public class MatOp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of rows: ");
        int rows = sc.nextInt();
        System.out.print("Enter the number of columns: ");
        int cols = sc.nextInt();

        Matrix m = new Matrix(rows, cols);
        m.setMatrices();
        m.printMatrices();
        m.addMatrices();
        m.subtractMatrices();
        m.multiplyMatrices();
    }
}
