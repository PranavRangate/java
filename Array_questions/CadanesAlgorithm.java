//Find the Largest Sum Contiguous Subarray (Kadane’s Algorithm) 
public class CadanesAlgorithm {

    public void Find_larg_cong_subarray(int[] nums){
        int maxSum=nums[0];
        int currSum=0;
        for(int i:nums){
            currSum+=i;
            if(currSum>maxSum){
                maxSum=currSum;
            }
            if(currSum<0){
                currSum=0;
            }
        }
        System.out.println(maxSum);
    }
    public static void main(String[] args) {
        CadanesAlgorithm obj = new CadanesAlgorithm();
        int[] arr = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        obj.Find_larg_cong_subarray(arr);
    }
}
