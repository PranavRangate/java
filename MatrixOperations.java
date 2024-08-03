import java.util.Scanner;
class Matrix{
       
    int a[][] = new int[2][2];
    int b[][] = new int[2][2];

       int i,j;

       Scanner sc = new Scanner(System.in);

       void setMatrixs(){
            System.out.println("Enter 1st matrix elements:");
            for(i=0;i<2;i++){
               for(j=0;j<2;j++){
                   a[i][j]=sc.nextInt();
                  }
               }

            System.out.println("Enter 2nd matrix elements:");
               for(i=0;i<2;i++){
                  for(j=0;j<2;j++){
                      b[i][j]=sc.nextInt();
                     }
                }

            }
        
        void printMatrixs(){
            System.out.println("1st matrix :");
            for(i=0;i<2;i++){
               for(j=0;j<2;j++){
                   System.out.print(a[i][j]+"");
                  }
                  System.out.println();
               }

            System.out.println();

            System.out.println("2nd matrix :");
               for(i=0;i<2;i++){
                  for(j=0;j<2;j++){
                      System.out.print(b[i][j]+" ");
                     }
                     System.out.println();

                  }
                System.out.println();
           }

        void addMatrixs(){
            System.out.println("addition of matrixs :");
            for(i=0;i<2;i++){
               for(j=0;j<2;j++){

                   int c = a[i][j]+b[i][j];
                   System.out.print(c+" ");
                  }
                  System.out.println();
               }
        }

        void subMatrixs(){
            System.out.println("Subtraction of matrixs :");
            for(i=0;i<2;i++){
               for(j=0;j<2;j++){

                   int c = a[i][j]-b[i][j];
                   System.out.print(c+" ");
                  }
                  System.out.println();
               }
        }

        void mulMatrixs(){
            System.out.println("Multiplication of matrixs :");
            for(i=0;i<2;i++){
               for(j=0;j<2;j++){
                  int c=0;
                  for(int k=0;k<2;k++){
                     c += a[i][k]*b[k][j];
                  }
                   System.out.print(c+" ");
                  }
                  System.out.println();
               }
        }

      
    
}

class MatrixOperations{
    public static void main(String[] args) {
        
           Matrix m = new Matrix();
           m.setMatrixs();
           m.printMatrixs();
           m.addMatrixs();
           m.subMatrixs();
           m.mulMatrixs();

    }
}