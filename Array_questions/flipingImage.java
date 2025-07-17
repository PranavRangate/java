public class flipingImage {

    public int[][] flipAndInvertImage(int[][] image) {
        
        for(int i=0;i<image.length;i++){
            int[] row = image[i];
            int n=row.length;
            
            for(int j=0;j<(n+1)/2;j++){
                int temp=row[j]^1;
                row[j]=row[n-1-j]^1;
                row[n-1-j]=temp;
            }
        }

    return image;
    };

    public static void main(String[] args) {

        int[][] a = {{1,1,0},{1,0,1},{0,0,0}};

        flipingImage obj = new flipingImage();

        int[][] img = obj.flipAndInvertImage(a);

        for(int i=0;i<img.length;i++){
            for(int j=0;j<img[i].length;j++){
                System.out.print(img[i][j]+" ");
            }
            System.out.println();
        }

    }
}
