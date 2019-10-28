package database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ServerThread implements Runnable {

	static AsistentData data = new AsistentData();
	
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	
	public ServerThread(Socket socket) {
		this.socket = socket;
		try {
			this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.out = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void run() {
		try {
		String request = in.readLine();
		JsonObject requestJson = new JsonParser().parse(request).getAsJsonObject();
		JsonObject response = new JsonObject();
		
		if (requestJson.get("method").getAsString().equals("get")) {
			System.out.println("Get request.");
			JsonArray jsonArray = new JsonArray();
			ArrayList<Asistent> asistenti = data.getAsistenti();
			for (Asistent a : asistenti) {
				jsonArray.add(a.toJson());
			}
			response.add("asistenti", jsonArray);
			response.addProperty("status", 200);
		} else if (requestJson.get("method").getAsString().equals("post")) {
			data.oceni(requestJson.get("ime").getAsString().toUpperCase(), requestJson.get("ocena").getAsDouble());
			System.out.println("Post request.");
			response.addProperty("status", 200);
		} else {
			System.out.println("Bad request.");
			response.addProperty("status", 400);	
		}
		
		out.println(response);
		
		in.close();
		out.close();
		socket.close();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
