import java.util.Scanner;

class Classroom_data{
    String class_name;
    String students[];

    Classroom_data( String cn , String stu[]){
        this.class_name=cn;
        this.students = stu;
    }

    void print(){
        System.out.println("Class Name : " + class_name);
        System.out.print("Students : ");
        int i;
        for(i=0;i<students.length;i++){
            System.out.print(students[i]+" ");
        }

    }
}

class Classroom{
    public static void main(String aergs[]){

        
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter no of students in classroom : ");
        int n = sc.nextInt();

        String stud[]= new String[n];
        
        for(int j=0;j<n;j++){
            System.out.println("Enter the names of students");
            stud[j]=sc.next();
        }

        Classroom_data c1 = new Classroom_data("TY CSE(AI)", stud );
        c1.print();
    }
}
