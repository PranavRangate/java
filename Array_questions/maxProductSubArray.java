public class maxProductSubArray {
    public int maxProduct(int[] nums) {
        int maxProd = nums[0];
        int minProd = nums[0];
        int result = nums[0];

        for (int i = 1; i < nums.length; i++){
             int curr = nums[i];
             int tempMax = maxProd;
             int tempMin = minProd;
             if (curr > 0) {
                if (tempMax > 0)
                    maxProd = tempMax * curr;
                else
                    maxProd = curr;
                if (tempMin < 0)
                    minProd = tempMin * curr;
                else
                    minProd = curr;

             }
             else if (curr < 0) {
                if (tempMin < 0)
                    maxProd = tempMin * curr;
                else
                    maxProd = curr;
                if (tempMax > 0)
                    minProd = tempMax * curr;
                else
                    minProd = curr;

             }
             else{
                maxProd = 0;
                minProd = 0;
             }
             if (maxProd > result)
                result = maxProd;

        }
        return result;
    }
    public static void main(String[] args) {
        maxProductSubArray obj = new maxProductSubArray();
        int[] arr = {2,3,-2,4};
        int res = obj.maxProduct(arr);
        System.out.println(res);
    }
}
