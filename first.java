import java.util.*;

public class first{

    public void reverseArr(int[] nums){
        int left=0;
        int right=nums.length-1;
        
        while(left<right){
            int temp=nums[left];
            nums[left]=nums[right];
            nums[right]=temp;
            left++;
            right--;
        }
        System.out.println("Reversed array :");
        for (int i : nums) {
            System.out.print(i+" ");
        }
    }

    public static void main(String[] args) {
	
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int[] a = new int[n];
        for(int i=0;i<n;i++){

            a[i]=scan.nextInt();
            
        }
        scan.close();

        System.out.println();
        // Prints each sequential element in array a
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }

        first obj = new first();
        obj.reverseArr(a);
    }
}