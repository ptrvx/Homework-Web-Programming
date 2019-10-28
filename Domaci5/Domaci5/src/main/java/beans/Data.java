package beans;

import java.io.Serializable;
import java.util.HashMap;

public class Data implements Serializable {

	private static final long serialVersionUID = -3704064216984180685L;
	
	private HashMap<String, String> data;
	
	public void setter(String key, String value) {
		data.put(key, value);
	}
	
	public HashMap<String, String> getter() {
		return data;
	}

	public Data() {
		data = new HashMap<String, String>();
	}

}

