import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static final int port = 9393;

    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(port);
            System.out.println("Server running...");
            while(true) {
                Socket socket = ss.accept();
                System.out.println("Client accepted");
                ServerThread serverThread = new ServerThread(socket);
                new Thread(serverThread).start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
