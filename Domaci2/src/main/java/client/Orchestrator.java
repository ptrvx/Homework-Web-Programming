package client;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Orchestrator implements Runnable {

    private int n;
    private ExecutorService executorService;

    public Orchestrator(int n) {
        this.executorService = Executors.newCachedThreadPool();
        this.n = n;
    }

    public void run() {
        try {
            for (int i = 0; i < n; i++) {
                Thread.sleep(1000);
                this.executorService.submit(new Client());

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.executorService.shutdown();

    }
}
