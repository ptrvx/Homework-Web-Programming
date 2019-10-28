package server;

import java.util.concurrent.CyclicBarrier;

public class Resources {

    private User winner = null;
    private int tableSeats = 6;
    private User[] users;

    private CyclicBarrier cyclicBarrier = new CyclicBarrier(6);
    private int player = 0;
    private int m;

    private boolean stillGoing = true;

    public Resources() {
        this.users = new User[this.tableSeats];
        this.m = 20;    // insert M value
    }

    public synchronized Boolean giveSeat(User user) {
        for (int i = 0; i < tableSeats; i++) {
            if (users[i] == null) {
                users[i] = user;
                return true;
            }
        }
        return false;
    }

    public synchronized void add(String id) {
        for (int i = 0; i < tableSeats; i++) {
            if (users[i].getId().equals(id)) {
                int p = users[i].add();
                if (winner == null || p > winner.getPoints())
                    winner = users[i];
                break;
            }
        }
    }

    public synchronized void reset(String id) {
        for (int i = 0; i < tableSeats; i++) {
            if (users[i].getId().equals(id)) {
                users[i] = null;
                break;
            }
        }
        player = 0;
        stillGoing = false;
        roundOver();
    }

    public void advance() {
        player++;
        stillGoing = true;
        roundOver();
    }

    public String currentPlayer() {
        return users[player].getId();
    }

    public int bulletsLeft() {
        return tableSeats - player;
    }

    public CyclicBarrier getCyclicBarrier() {
        return cyclicBarrier;
    }

    public int getPlayer() {
        return player;
    }

    public boolean isStillGoing() {
        return stillGoing;
    }

    public void setStillGoing(boolean stillGoing) {
        this.stillGoing = stillGoing;
    }

    public int getM() {
        return m;
    }

    private void roundOver() {
        m--;

        System.out.println("Rundi do kraja: " + m);

        if (m == 0) {
            System.out.println("Pobednik je " + winner + " sa " + winner.getPoints() + " poena.");
        }
    }
}
