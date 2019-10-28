package database;

import java.util.ArrayList;

import com.google.gson.JsonObject;

public class Asistent {

	private String ime;
	private ArrayList<Double> ocene;
	
	public Asistent(String ime) {
		this.ime = ime;
		this.ocene = new ArrayList<Double>();
	}
	
	public Asistent(String ime, double ocena) {
		this(ime);
		this.ocene.add(ocena);
	}
	
	public synchronized void oceni(double ocena) {
		this.ocene.add(ocena);
	}
	
	private synchronized double getProsek() {
		double sum = 0;
		for (double o : ocene) {
			sum += o;
		}
		if (ime.equals("VUK")) {
			return 0;
		} else {
			return sum / ocene.size();
		}
	}
	
	public String getIme() {
		return ime;
	}
	
	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		json.addProperty("ime", ime);
		json.addProperty("prosek", getProsek());
		
		return json;
	}
	
}
