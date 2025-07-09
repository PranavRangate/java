public class second_max {
    
    public int findSecondMax(int[]  nums){
        int first_max=0;
        int sec_max=0;
        for(int i:nums){
            if(i>first_max){
                sec_max=first_max;
                first_max=i;
            }
            else if(i > sec_max && i != first_max){
                sec_max=i;
            }
        }
        return sec_max;
    }
    public static void main(String[] args) {
        second_max obj = new second_max();
        int[] arr = {100,20,70,30,50,40};
        System.out.println(obj.findSecondMax(arr));
    }

}
