import java.time.LocalDate;
import java.time.Period;
import java.util.Scanner;

 class Person{

    String name;
    int day;
    int month;
    int year;
    float hight;
    float weight;
    String addr;

    
//    public Person(String[] nm,int d,int m,int y,float h,float w,String[] ad){
 //       this.name=nm;
 //       this.day=d;
 //       this.month=m;
 //       this.year=y;
 //       this.hight=h;
  //      this.weight=w;
 //       this.addr=ad;
 //   }

    public void setData(){

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the name:");
        name=sc.next();
        System.out.println("Enter the day of your date of birth:");
        day=sc.nextInt();
        System.out.println("Enter the month of your date of birth:");
        month=sc.nextInt();
        System.out.println("Enter the year of your date of birth:");
        year=sc.nextInt();
        System.out.println("Enter the hight:");
        hight=sc.nextFloat();
        System.out.println("Enter the weight:");
        weight=sc.nextFloat();
        System.out.println("Enter the address:");
        addr=sc.next();

    }

    public void getData(){
        System.out.println(" name:"+name);
        System.out.println(" hight:"+hight);
        System.out.println(" weight:"+weight);
        System.out.println(" address:"+addr);
    }


    public void Cal_Age(){

        LocalDate birthDate = LocalDate.of(year,month,day);

        LocalDate today = LocalDate.now();

        Period period = Period.between(birthDate, today);

        int age = period.getYears();

        System.out.println("Age: " + age);
    }

}

class Student extends Person{

    int roll_no;
    int marks[] = new int[5];

    public void s_data(){

        Scanner sc2 = new Scanner(System.in);

        System.out.println("Enter the roll number:");
        roll_no=sc2.nextInt();
        System.out.println("Enter the marks:");
        for(int i=0;i<5;i++){
            marks[i]=sc2.nextInt();
        }      

    }

    public void s_get(){
        System.out.println(" roll number:"+roll_no);
        System.out.print(" marks:");
        for(int j=0;j<5;j++){
            System.out.print(marks[j]+" ");
        } 
        System.out.println(" ");
    }

    float ag=0;
    float avg=0;
    public void cal_avg(){
        for(int j=0;j<5;j++){
            ag+=marks[j];
        } 
        avg=ag/5;

        System.out.println("Average mark : "+avg);
    }


}

class Employee extends Person{

    int Emp_ID;
    int salary;

    public void s_data(){

        Scanner sc3 = new Scanner(System.in);

        System.out.println("Enter the Employee ID:");
        Emp_ID=sc3.nextInt();
        System.out.println("Enter yearly salary:"); 
        salary=sc3.nextInt();

    }

    public void s_get(){
        System.out.println(" Employee ID:"+Emp_ID);
        System.out.print(" salary :"+salary);
    }
    
    int up_sal;
    int u;
    public void tax_on_salary(){
        u=(salary*10)/100;
        up_sal=salary-u;
        System.out.println("Salary after tax : "+up_sal);
    }

}

public class Inheritance{
    public static void main(String[] args){
        Student s = new Student();
        s.setData();
        s.getData();
        s.Cal_Age();
        s.s_data();
        s.s_get();
        s.cal_avg();

      Employee e = new Employee();
        e.setData();
        e.getData();
        e.Cal_Age();
        e.s_data();
        e.s_get();
        e.tax_on_salary();

    }
}
