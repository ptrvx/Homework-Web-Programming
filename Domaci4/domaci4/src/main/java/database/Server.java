package database;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	public static void main(String[] args) {
		
		try {
			ServerSocket ss = new ServerSocket(9090);
			System.out.println("Database server running. . .");
			while(true) {
				Socket socket = ss.accept();
				
				Thread thread = new Thread(new ServerThread(socket));
				thread.start();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
