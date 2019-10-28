package Domaci1;

import java.util.ArrayList;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class ProfesorThread extends Nastavnik implements Runnable {

    private ArrayList<Integer> index = new ArrayList<>();
    private CyclicBarrier barrier = new CyclicBarrier(2);

    ProfesorThread(ArrayList<StudentThread> studenti) {
        super(studenti);
    }

    void odbrana(int index, long vreme) {
        this.index.add(index);
        this.vreme = Math.max(vreme, this.vreme);
        try {
            barrier.await();

        } catch(Exception e) {
        }
        synchronized (lock) {
            lock.notify();      // ovo ce se pozvati vise puta ali mislim da to ne moze da pravi problem
        }

    }

    @Override
    public void run() {
        this.threadName = Thread.currentThread().toString();

        while(Main.running) {
            try {
                synchronized (lock) {
                    barrier.reset();
                    index.clear();
                    vreme = -1;
                    semaphore.release(2);
                    lock.wait();
                }

                start = System.currentTimeMillis();
                Thread.sleep(vreme);

            } catch (InterruptedException e) {
            } finally {
                for(int i : index) {
                    studenti.get(i).setScore(rand.nextInt(11), start);
                }
            }


        }

    }
}
