package hw2;

import java.util.concurrent.Semaphore;

public class Main{
    public static void main(String[] args) {
        Semaphore sem =new Semaphore(5,true);
new Truck(sem,"Truck_1");
        new Truck(sem, "Truck_2");
        new Truck(sem, "Truck_3");
        new Truck(sem, "Truck_4");
        new Truck(sem, "Truck_5");
        new Truck(sem, "Truck_6");
        new Truck(sem, "Truck_7");
        new Truck(sem, "Truck_8");
        new Truck(sem, "Truck_9");
        new Truck(sem, "Truck_10");
        new Truck(sem, "Truck_11");
        new Truck(sem, "Truck_12");

    }
}


 class Truck extends Thread {
    private Semaphore sem;
    private String nameTruck;
    //private boolean pass = false;

    public Truck(Semaphore sem, String nameTruck) {
        this.nameTruck = nameTruck;
        this.sem = sem;
        this.start();
    }

    @Override
    public void run() {
//if(!pass){
    try {
        sem.acquire();
        System.out.println("\t"+nameTruck+" Заезжает на взвешивание");
        sleep(1000);
        //pass=true;
        System.out.println("\t\t\t"+nameTruck+" Взвесился и выехал с весов");
        sem.release();
        sleep(1000);

    } catch (InterruptedException e) {
        throw new RuntimeException(e);
    }

    }
}
