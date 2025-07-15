public class arrays_2d{

    public void rowAdditions(int[][] nums){
        for(int i=0;i<nums.length;i++){
            int add=0;
            for(int j=0;j<nums[i].length;j++){
                add+=nums[i][j];
            }
            System.out.println("sum of "+i+" row is "+add);
        }
    }

    public void colAdditions(int[][] nums){

        for(int i=0;i<nums[i].length;i++){
            int add=0;
            for(int j=0;j<nums.length;j++){
                add+=nums[j][i];
            }
            System.out.println("sum of "+i+" coloum is "+add);
        }
    }

    public void printCross(int[][] nums){

        for (int i = 0; i <3; i++) {
            for (int j = 0; j <3; j++) {
                if (i == j || i+j == 3-1) {
                    System.out.print(nums[i][j]+" ");
                }
                else{
                    System.out.print(" ");
                }
            }
            System.out.println();
        }

    }

    public static void main(String[] args) {

        int[][] a = {{1,2,3},{4,5,6},{7,8,9}};
        

        arrays_2d obj = new arrays_2d();
        obj.rowAdditions(a);
//        obj.colAdditions(a);
        obj.printCross(a);

    }
}
