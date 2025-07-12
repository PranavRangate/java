public class duplicateNumber {
    public int findDouplicate(int[] nums){
        int slow = nums[0];
        int fast = nums[0];

        do { 
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while (slow!=fast);

        slow = nums[0];
        while (slow!=fast) { 
            slow = nums[slow];
            fast = nums[fast];
        }

        return slow;
    }
    public static void main(String[] args) {
        duplicateNumber obj = new duplicateNumber();
        int[] arr = {1,3,4,2,2};
        int result = obj.findDouplicate(arr);
        System.out.println(result);
    }
}
