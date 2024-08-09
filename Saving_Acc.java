import java.util.Scanner;
public class Saving_Acc{

       private static Double yearly_interest;
       private  Double acc_balance;
       private Double m_interest;

       public Saving_Acc(Double balance){
              this.acc_balance=balance;
       }


       public static void Modify_yearly_interest(Double newRate){
              yearly_interest=newRate;
       }

       public void monthly_interest(){
             m_interest = ( (acc_balance*((yearly_interest)/100))/12 );
             acc_balance=acc_balance+m_interest;
       }

       public void print_balance(){
              
              System.out.printf("acc_balance =  %.2f \n " , acc_balance);
              
       }

       public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the yearly interest= ");
        Double inte = sc.nextDouble();

        System.out.print("Enter the balance in your account = ");
        Double bala = sc.nextDouble();
        System.out.print("Enter the balance in your account = ");
        Double b = sc.nextDouble();
        Saving_Acc cust1 = new Saving_Acc(bala);
        Saving_Acc cust2 = new Saving_Acc(b);

        cust1.Modify_yearly_interest(inte);

        cust1.monthly_interest();
        cust2.monthly_interest();
        cust1.print_balance();
        cust2.print_balance();

        System.out.print("Enter the modified yearly interest = ");
        Double cng = sc.nextDouble();

        cust2.Modify_yearly_interest(cng);
        
        cust1.monthly_interest();
        cust2.monthly_interest();
        cust1.print_balance();
        cust2.print_balance();
        
 
        
       }
 
}