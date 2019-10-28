package client;

public class ClientMain {

    public static void main(String[] args) {
        Orchestrator orchestrator = new Orchestrator(200);  // insert N value
        Thread thread = new Thread(orchestrator);
        thread.start();
    }
}
