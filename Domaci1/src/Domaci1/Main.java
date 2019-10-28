package Domaci1;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.*;

public class Main {


    static boolean running = true;
    private static int a = 0;
    private static int sum = 0;
    static long time;

    static synchronized void score(int x) {
        a++;
        sum += x;
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Unesite broj studenata: ");
        int n = sc.nextInt();
//        int n = 100;
        long vremeIspita = 5000;
        Random rand = new Random();


        ArrayList<StudentThread> studenti = new ArrayList<>();
        ArrayList<Nastavnik> nastavnici = new ArrayList<>();
        nastavnici.add(new AsistentThread(studenti));
        nastavnici.add(new ProfesorThread(studenti));

        double[] ds = rand.doubles(n).map((x) -> (x * 1000)).toArray();
        for (int i = 0; i < n; i++) {
            studenti.add(new StudentThread(i, (long)ds[i], nastavnici));
        }

        ExecutorService niti = Executors.newFixedThreadPool(2);
        niti.submit(nastavnici.get(0));
        niti.submit(nastavnici.get(1));
        ScheduledExecutorService schThreadPool = Executors.newScheduledThreadPool(n);

        time = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            schThreadPool.schedule(studenti.get(i), studenti.get(i).getArrival(), TimeUnit.MILLISECONDS);
        }

        try {
            Thread.sleep(vremeIspita + time - System.currentTimeMillis()); // Mozda je dovoljno precizno da se ostavi sleep(5000)
        } catch (Exception e) {
            e.printStackTrace();
        }
        running = false;   // Sprecava vise studenata da zapocne odbranu kada krene shutdown
        niti.shutdownNow();
        try {
            niti.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println("Niti pool took too long to shutdown.");
        }
        schThreadPool.shutdownNow();
        try {
            schThreadPool.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println("Students pool took too long to shutdown.");
        }

        System.out.println("Prosecna ocena: " + (double)sum/a + " Broj odbrana: " + a);

    }
}
