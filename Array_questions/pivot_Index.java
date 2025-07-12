public class pivot_Index {
    public int pivotIndex(int[] nums) {
        int totalSum = 0;
        for (int i : nums) {
            totalSum += i; 
        }

        int leftSum = 0;
        for (int i = 0; i < nums.length; i++) {
            
            if (leftSum == totalSum - leftSum - nums[i]) {
                return i; 
            }
            leftSum += nums[i];
        }

        return -1;
    }
    public static void main(String[] args) {
        pivot_Index obj = new pivot_Index();
        int[] arr = {1,7,3,6,5,6};
        int index = obj.pivotIndex(arr);
        System.out.println(index);
    }
}
