package hw1;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Thread thread1 = new Thread(new ThreeMethods()::first);
        Thread thread2 = new Thread(new ThreeMethods()::second);
        Thread thread3 = new Thread(new ThreeMethods()::third);

        thread3.start();
        thread3.join();

        thread2.start();
        thread2.join();

        thread1.start();
    }
}

class ThreeMethods {

    public void first() {
        System.out.println("first");
    }

    public void second() {
        System.out.println("second");
    }

    public void third() {
        System.out.println("third");
    }
}