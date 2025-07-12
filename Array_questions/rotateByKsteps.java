public class rotateByKsteps {
    public void rotateByKstep(int[] nums,int k){
        int n = nums.length;
        k = k % n;
        
        int[] arr = new int[n];
        int j = 0;

        for (int i = n - k; i < n; i++) {
            arr[j++] = nums[i];
        }

        for (int i = 0; i < n - k; i++) {
            arr[j++] = nums[i];
        }

        for (int i : arr) {
            System.out.print(i + " ");
        }
    }

    public  void rotate(int[] nums, int k) {
        int n = nums.length;
        k = k % n; 

        // 1: Reverse the whole array
        reverse(nums, 0, n - 1);
        //  2: Reverse first k elements
        reverse(nums, 0, k - 1);
        //  3: Reverse remaining n - k elements
        reverse(nums, k, n - 1);

        for (int i : nums) {
            System.out.print(i + " ");
        }
    }

    private static void reverse(int[] arr, int start, int end) {
        while (start < end) {
            int temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
            start++;
            end--;
        }
    }

    public static void main(String[] args) {
        rotateByKsteps obj = new rotateByKsteps();
        int[] nums1={1,2,3,4,5,6,7};
        int k1=3;
        obj.rotateByKstep(nums1,k1);

        System.out.println();
        
        int[] nums2={1,2,3,4,5,6,7};
        int k2=12;
        obj.rotate(nums2, k2);
    }
}
