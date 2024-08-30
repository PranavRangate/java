import java.util.Scanner;

 class Employee{

    String name;
    String addr;
    float salary;
    String Job_Title;

/*     Employee(String name,String addr, float salary, String Job_Title){
        this.name=name;
        this.addr=addr;
        this.salary=salary;
        this.Job_Title=Job_Title;
    }

    public void getData(){
        System.out.println("name="+name);
        System.out.println("address="+addr);
        System.out.println("salary="+salary);
        System.out.println("Job_Title="+Job_Title);
    }

*/


}

class Manager extends Employee{
        Manager(String name,String addr, float salary, String Job_Title){
        this.name=name;
        this.addr=addr;
        this.salary=salary;
        this.Job_Title=Job_Title;
    }

    public void m_get(){
        System.out.println("Manager name = "+name);
        System.out.println("Manager address = "+addr);
        System.out.println("Manager salary = "+salary);
        System.out.println("Manager Job_Title = "+Job_Title+"\n");
    }



}

class Developer extends Employee{
    Developer(String name,String addr, float salary, String Job_Title){
        this.name=name;
        this.addr=addr;
        this.salary=salary;
        this.Job_Title=Job_Title;
    }
    public void d_get(){
        System.out.println("Developer name = "+name);
        System.out.println("Developer address = "+addr);
        System.out.println("Developer salary = "+salary);
        System.out.println("Developer Job_Title = "+Job_Title+"\n");
    }
    


}

class Programmer extends Employee{
    Programmer(String name,String addr, float salary, String Job_Title){
        this.name=name;
        this.addr=addr;
        this.salary=salary;
        this.Job_Title=Job_Title;
    }

    public void p_get(){
        System.out.println("Programmer name = "+name);
        System.out.println("Programmer address = "+addr);
        System.out.println("Programmer salary = "+salary);
        System.out.println("Programmer Job_Title = "+Job_Title+"\n");
    }
    


}

public class Inheritance_1{
    public static void main(String[] args){
      Manager M = new Manager("pranav", "korochi",10000 , "sales_manager");
      M.m_get();
      Developer D = new Developer("saptarshi", "ichalkaranji",5000 , "frontend_developer");
      D.d_get();
      Programmer P = new Programmer("amay", "sangli",7000 , "backend_programmer");
      P.p_get();

    }
}
