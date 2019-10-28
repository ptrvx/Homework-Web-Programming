package Domaci1;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class StudentThread implements Runnable {

    private String threadName;
    private long arrival;
    private long start;
    private long end;
    private int index;
    private int score = -1;
    private ArrayList<Nastavnik> nastavnici;
    private int theOne = -1;

    private final Object lock = new Object();
    private Random rand = new Random();

    StudentThread(int index, long arrival, ArrayList<Nastavnik> nastavnici) {
        this.arrival = arrival;
        this.nastavnici = nastavnici;
        this.index = index;
    }


    @Override
    public void run() {

        threadName = Thread.currentThread().toString();

        try {

            for (int i = 0; i < nastavnici.size(); i++) {
                if (nastavnici.get(i).getSem().tryAcquire() && Main.running) {
                    theOne = i;
                    break;
                }
            }

            while (theOne == -1) {
                for (int i = 0; i < nastavnici.size(); i++) {
                    if (nastavnici.get(i).getSem().tryAcquire(1, TimeUnit.SECONDS) && Main.running) {
                        theOne = i;
                        break;
                    }
                }
            }

            nastavnici.get(theOne).odbrana(index,rand.nextInt(500) + 500);
            synchronized (lock) {
                lock.wait();
            }

        } catch (InterruptedException e) {
        } finally {
            if (theOne != -1) {                                      // Zapoceta odbrana se zavrsava
                Main.score(score);
                System.out.println(this);
            }
        }
    }

    void setScore(int score, long start) {
        this.score = score;
        this.start = start;
        this.end = System.currentTimeMillis();
        synchronized (lock) {
            lock.notify();
        }
    }

    @Override
    public String toString() {
        return String.format("Thread: %s Arrival: %d Prof: %s TTC: %d:%d Score: %s", threadName, arrival, nastavnici.get(theOne), end - start, start - Main.time, score);
    }

    long getArrival() {
        return arrival;
    }

}
