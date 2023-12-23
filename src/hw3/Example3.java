package hw3;
//Задача с очередью пациентов: реализовать чтение из файла, сбор объекта и передачу в список объектов класса Patient
//(использование Callable и Future)

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.*;

public class Example3 {
    static String file = "D:\\Андрей\\Java\\AlexeiTop\\ThreadHomework\\src\\hw3\\dump.txt";
    static DequeWrapper newPatientStrings = new DequeWrapper();
    static List<Patient> patients = new ArrayList<>();
//    static Object object = new Object();

    public static void main(String[] args) throws ExecutionException, InterruptedException {


        //получаем List<Patient> из текстового dump.txt
//        List<Patient> patients = Dump.getDump();
//        java.util.List<Patient> patients = Dump.getDump();
//        System.out.println(patients.size());      //для проверки корректности считывания базы выводим длину массива пациентов
//        patients.forEach(System.out::println);    //для проверки выводим содержимое базы

//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//        PatientString newPatientString = new PatientString();
//
//        Future<String> future = executorService.submit(newPatientString);
//        try {
//            String s = future.get();
//            newPatientStrings.add(s);
//        } finally {
//            executorService.shutdown();
//        }

//        MyRunnable runnable = new PatientString();
        Thread thread1 = new Thread(new PatientString());
        Thread thread2 = new Thread(new Patients());

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join(5000);

        patients.forEach(System.out::println);

//        System.out.println(newPatientStrings.size());
//        System.out.println(newPatientStrings);


    }


    static class PatientString implements Runnable {

//    String newPatientString;

        @Override
        public void run() {
            try (Scanner scanner = new Scanner(new FileReader(file))) {
                String newPatientString;
                while (scanner.hasNext()) {
                    newPatientString = scanner.nextLine();
                    newPatientStrings.add(newPatientString);
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static class Patients implements Runnable {
        @Override
        public void run() {
            while (true) {
                String temp;
                try {
                    temp = newPatientStrings.pop().replaceAll(",", "")
                            .replaceAll("'", "")
                            .replace("(", "")
                            .replace(")", "");
                    Patient newPatient = new Patient(temp);
                    patients.add(newPatient);
                } catch (InterruptedException e) {
                    System.out.println("Поток чтения из очереди прерван");
                }
            }
        }
    }
}

class DequeWrapper {
    final Deque<String> newPatientStrings;

    public DequeWrapper() {
        this.newPatientStrings = new ArrayDeque<>();
    }

    public synchronized void add (String st) {
        newPatientStrings.add(st);
        notify();
    }

    public synchronized String pop() throws InterruptedException {
        while (newPatientStrings.isEmpty()) {
            wait();
        }
        return newPatientStrings.pop();
    }

    public synchronized boolean isEmpty() {
        return newPatientStrings.isEmpty();
    }

}





