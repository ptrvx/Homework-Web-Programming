package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class AsistentData {

	public ArrayList<Asistent> getAsistenti() throws IOException {
		ArrayList<Asistent> asistenti = new ArrayList<Asistent>();
		
		Socket socket = new Socket("localhost", 9090);
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		
		JsonObject request = new JsonObject();
		request.addProperty("method", "get");
		out.println(request.toString());
		String response = in.readLine();
		in.close();
		out.close();
		socket.close();
		
		JsonObject asistentiObject = new JsonParser().parse(response).getAsJsonObject();
		JsonArray asistentiArray = asistentiObject.getAsJsonArray("asistenti");
		
		for (JsonElement a : asistentiArray) {
			JsonObject j = (JsonObject) a;
			asistenti.add(new Asistent(j.get("ime").getAsString(), j.get("prosek").getAsDouble()));
		}		
		
		return asistenti;
	}
	
	public void oceni(String ime, double ocena) throws IOException {
		Socket socket = new Socket("localhost", 9090);
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		
		JsonObject request = new JsonObject();
		request.addProperty("method", "post");
		request.addProperty("ime", ime);
		request.addProperty("ocena", ocena);
		out.println(request.toString());
		String response = in.readLine();
		in.close();
		out.close();
		socket.close();
		
	}
	
	
}
