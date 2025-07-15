public class simple {

    public void addAll(int[] nums){
        int total=0;
        for(int i:nums){
            total+=i;
        }
        System.out.println("Total="+total);
    }

    public void findMinMax(int[] nums){
        int min=nums[0];
        int max=nums[0];
        for(int i:nums){
            if(i>max)max=i;
            if(i<min)min=i;
        }
        System.out.println("min="+min);
        System.out.println("max="+max);

    }

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
        System.out.println("Reversed array:");
        for (int i : nums) {
            System.out.print(i);
        }
    }

    public static void main(String[] args) {

        simple obj = new simple();

        int[] arr = {1, 2, 3, 4, 5};

        for (int i : arr) {
            System.out.print(i);
        }
        System.out.println();

        obj.addAll(arr);

        obj.findMinMax(arr);

        obj.reverseArr(arr);



    }
}
