package database;

import java.util.ArrayList;

public class AsistentData {

	private ArrayList<Asistent> asistenti = new ArrayList<Asistent>();
	
	public synchronized void oceni(String ime, double ocena) {
		Asistent found = null;
		for (Asistent a : asistenti) {
			if (a.getIme().equals(ime)) {
				found = a;
				break;
			}
		}
		
		if (found != null) {
			found.oceni(ocena);
		} else {
			asistenti.add(new Asistent(ime, ocena));
		}
	}
	
	public synchronized ArrayList<Asistent> getAsistenti() {
		return asistenti;
	}
	
}
