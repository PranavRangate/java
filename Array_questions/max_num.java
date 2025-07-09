public class max_num {

    public void find_man_no(int[] nums){
        int max=0;
        for(int i:nums){
            if(i>max){
                max=i;
            }
        }
        System.out.println("Maximum no in array is "+max);
    }

    public static void main(String[] args) {
        max_num obj = new max_num();

        int[] arr={1,5,6,7,2};
        obj.find_man_no(arr);

        
    }
}
