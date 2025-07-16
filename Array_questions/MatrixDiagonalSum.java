public class MatrixDiagonalSum{

    public int diagonalSum(int[][] mat) {
        int sum=0;
        int n=mat.length;
        for (int i = 0; i <n; i++) {
            for (int j = 0; j <mat[i].length; j++) {
                if (i == j || i+j == n-1) {
                    sum+=mat[i][j];
                }
            }
        }
        return sum;
    }

    public int digSum(int[][] mat){
        int sum = 0;
        int n = mat.length;

        for (int i = 0; i < n; i++) {
            sum += mat[i][i];             
            sum += mat[i][n - 1 - i];     
        }

        if (n % 2 == 1) {
            sum -= mat[n / 2][n / 2];
        }

        return sum;
    }

    public static void main(String[] args) {

        MatrixDiagonalSum obj = new MatrixDiagonalSum();
        int[][] a = {{1,2,3},{4,5,6},{7,8,9}};

        int s = obj.diagonalSum(a);
        System.out.println(s);


    }
}