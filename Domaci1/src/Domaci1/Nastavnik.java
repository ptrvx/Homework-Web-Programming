package Domaci1;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

public abstract class Nastavnik implements Runnable{

    protected final Object lock = new Object();
    protected ArrayList<StudentThread> studenti;
    protected Random rand = new Random();
    protected Semaphore semaphore;
    protected String threadName;
    protected long start = -1;
    protected long vreme = 0;

    Nastavnik(ArrayList<StudentThread> studenti) {
        this.studenti = studenti;
        this.semaphore = new Semaphore(0);
    }

    abstract void odbrana(int index, long vreme);

    Semaphore getSem() {
        return semaphore;
    }

    @Override
    public String toString() {
        return threadName;
    }

}
