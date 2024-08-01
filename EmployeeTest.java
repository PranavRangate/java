import java.util.Scanner;
class Employee{

    
    String first_name;
    String last_name;
    Double salary;
    Double se;

    Employee(String fn , String ln , Double sal){
        this.first_name=fn;
        this.last_name=ln;
        this.salary=sal;
    }

    void set_first_name(String f){
        first_name=f;
    }

    void set_last_name(String l){
        last_name=l;
    }

    void set_salary(Double s){
        salary=s;
    }


    void get_first_name(){
        System.out.println("Employee first name = "+first_name);
    }

    void get_last_name(){
        System.out.println("Employee last name = "+last_name);
    }

    void get_salary(){
         se = salary*12;
         System.out.println("Yearly salary of "+first_name+" "+last_name+" = "+se);
    }

    void rise(){
        Double y_salary = (se + (se*0.1));
        System.out.println("Yearly salary of "+first_name+" "+last_name+" after rise of 10% = "+ y_salary);
    }

}



class EmployeeTest{
   public static void main(String[] args) {

    String n1,nm1;
    String n2,nm2;
    Double s1,sm1;

   

    Employee e1 = new Employee("Pranav","Rangate",1000.0);

// printing e1 objets data:


//    e1.get_first_name();
//    e1.get_last_name();
//    e1.get_salary();
//    e1.rise();
//    System.out.println("\n\n");



// If want to change the data of any object perform below code

//    Scanner sc1 = new Scanner(System.in);

//    System.out.println("Enter first name = ");
//    n1 = sc1.next();
//    System.out.println("Enter last name = ");
//    n2 = sc1.next();
//    System.out.println("Enter salary = ");
//    s1 = sc1.nextDouble();

//    e1.set_first_name(n1);
//    e1.set_last_name(n2);
//    e1.set_salary(s1);


// printing data of e1 object after changing data

//    e1.get_first_name();
//    e1.get_last_name();
//    e1.get_salary();
//    e1.rise();
//    System.out.println("\n\n");


    Employee e2 = new Employee("Saptarshi","Pujari",5000.0);


// printing e2 object data    

//    e2.get_first_name();
//    e2.get_last_name();
//    e2.get_salary();
//    e2.rise();
//    System.out.println("\n\n");


// If want to change the data of any object perform below code

//    Scanner sc2 = new Scanner(System.in);

//    System.out.println("Enter first name = ");
//    nm1 = sc2.next();
//    System.out.println("Enter last name = ");
//    nm2 = sc2.next();
//    System.out.println("Enter salary = ");
//    sm1 = sc2.nextDouble();

//    e2.set_first_name(nm1);
//    e2.set_last_name(nm2);
//    e2.set_salary(sm1);


// printing data of e2 object after changing data

//    e2.get_first_name();
//    e2.get_last_name();
//    e2.get_salary();
//    e2.rise();
//    System.out.println("\n\n");


     e1.get_salary();
     e1.rise();
     System.out.println("\n");
     e2.get_salary();
     e2.rise();


   }
    

}
