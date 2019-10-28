package server;

public class User {
    private String id;
    private int points = 0;

    public User(String id) {
        this.id = id;
        this.points = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int add() {
        return ++points;
    }

    public int getPoints() {
        return points;
    }

    @Override
    public String toString() {
        return id;
    }
}
