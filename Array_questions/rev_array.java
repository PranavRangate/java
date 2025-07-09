public class rev_array {

    public void reverse(int[] nums){
        int p=0;
        int[] arr = new int[nums.length];
        for(int i=nums.length-1;i>=0;i--){
            arr[p]=nums[i];
            p++;
        }
        for(int i=0;i<arr.length;i++){
            System.out.print(arr[i]);
        }
        System.out.println();

    }

    public void reverseArray(int[] nums){
        int start=0;
        int end=nums.length-1;
        //swap start and end to reverse array
        while(start<end){
            int temp=nums[start];
            nums[start]=nums[end];
            nums[end]=temp;
            start++;
            end--;
        }
        for(int i:nums){
            System.out.print(i);
        }
    }
    
    public static void main(String[] args) {

        rev_array obj = new rev_array();
        int[] nums={1,2,3,4,5};
        obj.reverse(nums);
        obj.reverseArray(nums);
        
    }
}
