public class check_sortArray {
    public boolean  check_array(int[] nums){
        for(int i=0;i<nums.length-1;i++){
            if(nums[i]>nums[i+1]){
                return false;
            }
            
        }
        return true;
    }
    public static void main(String[] args) {
        check_sortArray obj = new check_sortArray();
        int[] arr1 = {10,20,30,40,50};
        int[] arr2 = {10,20,30,50,40};
        System.out.println(obj.check_array(arr1));
        System.out.println(obj.check_array(arr2));

    }
}
