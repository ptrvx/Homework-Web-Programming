package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {

    private ServerSocket serverSocket;
    private Resources resources;

    public Server() throws IOException {
        this.serverSocket = new ServerSocket(9393);
        resources = new Resources();
    }

    public void run() {
        while(resources.getM() > 0) {
            try {
                Socket socket = serverSocket.accept();
                ServerThread serverThread = new ServerThread(socket, resources);
                new Thread(serverThread).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
