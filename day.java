public class day{

    public static void main(String args[]){
        int i;
        int dd=0;
        int mm=0;
        int yy=0;
        for(i=0;i<args.length;i++){
             dd=Integer.parseInt(args[0]);
             mm=Integer.parseInt(args[1]);
             yy=Integer.parseInt(args[2]);
        }

        int mont_days[]={0,31,28,31,30,31,30,31,31,30,31,30,31};

        int day_passed=0;
        for(i=0;i<mm;i++){
             day_passed+=mont_days[i];
        }

        day_passed = dd+day_passed;

        if (yy%4 == 0 && mm>=2) {
            day_passed+=1;
        }
        
        yy=yy-1;

        int remain_y=yy%4;

        int fives_m=remain_y/100;

        int rem_y=remain_y%100;

        int start=yy-rem_y+1;

        int ly=0;

        for(i=start;i<=rem_y;i++){
            if (i%4==0 && i%100!=0 || i%400 == 0) {
                ly+=1;
            }
           
        }

        int odd_days= ( day_passed+(fives_m*5)+(ly*2)+(rem_y-ly) )%7;

        switch ((odd_days)) {
            case 0:
                System.out.println("Sunday");
                break;

                case 1:
                System.out.println("Monday");
                break;
            
                case 2:
                System.out.println("Tuesday");
                break;

                case 3:
                System.out.println("Wenesday");
                break;

                case 4:
                System.out.println("Thrusday");
                break;

                case 5:
                System.out.println("Friday");
                break;

                case 6:
                System.out.println("Saturday");
                break;
        
            default:
                break;
        }
        

    }
}