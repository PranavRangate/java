public class find_single_no{

    public int singleNumber(int[] nums) {
        int n=0;
        for(int i:nums){
            n ^= i; //XOR
        }
        return n;
    }

    public static void main(String[] args) {

        find_single_no obj = new find_single_no();
        int[] num = {4, 1, 2, 1, 2};

        System.out.println("Single number: " + obj.singleNumber(num));
    }

}