public class sum_all {
    public void cal_sum(int[] nums){
        int sum=0;
        for(int i:nums){
            sum=sum+i;
        }
        System.out.println("sum="+sum);
    }
    public static void main(String[] args) {
        sum_all obj = new sum_all();
        int[] arr = {10,20,40,30,50};
        obj.cal_sum(arr);
    }
}
