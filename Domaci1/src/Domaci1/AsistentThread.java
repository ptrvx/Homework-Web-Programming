package Domaci1;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class AsistentThread extends Nastavnik implements Runnable {

    private int index = -1;

    AsistentThread(ArrayList<StudentThread> studenti) {
        super(studenti);
    }

    void odbrana(int index, long vreme) {
        this.index = index;
        this.vreme = vreme;
        synchronized (lock) {
            lock.notify();
        }

    }

    @Override
    public void run() {
        this.threadName = Thread.currentThread().toString();

        while (Main.running) {

            try {
                synchronized (lock) {
                    index = -1;
                    semaphore.release();
                    lock.wait();
                }

                start = System.currentTimeMillis();
                Thread.sleep(vreme);

            } catch (InterruptedException e) {
            } finally {
                if (index != -1) {
                    studenti.get(index).setScore(rand.nextInt(11), start);
                }
            }
        }
    }

}
