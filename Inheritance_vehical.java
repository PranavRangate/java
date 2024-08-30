import java.util.Scanner;

 class Vehical{

    String model;
    int year;
    String fual_type;

}

class Truck extends Vehical{

    float T_avg = 7;
    float dist;

    Truck(String model,int year, String fual_type){
        this.model=model;
        this.year=year;
        this.fual_type=fual_type;
    }

    public void t_get(){
        System.out.println("Truck model = "+model);
        System.out.println("Truck year = "+year);
        System.out.println("Truck fual_type = "+fual_type);
    }

    public void dis_tra_Truck(float dist){
        this.dist=dist;
        System.out.println("Distance travelled by Truck = "+dist);
    }



}

class car extends Vehical{

    float C_avg = 20;
    float dist;

    car(String model,int year, String fual_type){
        this.model=model;
        this.year=year;
        this.fual_type=fual_type;
    }
    public void c_get(){
        System.out.println("car model = "+model);
        System.out.println("car year = "+year);
        System.out.println("car fual_type = "+fual_type);
    }
    
    public void dis_tra_car(float dist){
        this.dist=dist;
        System.out.println("Distance travelled by car = "+dist);
    }


}

class motercycle extends Vehical{

    float m_avg = 50;
    float dist;

    motercycle(String model,int year, String fual_type){
        this.model=model;
        this.year=year;
        this.fual_type=fual_type;
    }

    public void m_get(){
        System.out.println("motercycle model = "+model);
        System.out.println("motercycle year = "+year);
        System.out.println("motercycle fual_type = "+fual_type);
    }
    
    public void dis_tra_mot(float dist){
        this.dist=dist;
        System.out.println("Distance travelled by motercycle = "+dist);
    }

    public void speed(){

    }


}

public class Inheritance_vehical{
    public static void main(String[] args){
      

    }
}
