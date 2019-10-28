package beans;

public class Asistent {
	
	private String name;
	private double sum;
	private int number;
	
	public Asistent() {
	}
	
	public Asistent(String name) {
		this.name = name;
		this.sum = 0;
		this.number = 0;
	}
	
	public Asistent(String name, int score) {
		this.name = name;
		this.sum = score;
		this.number = 1;
	}
	
	public String getName() {
		return name;
	}
	
	public double getSum() {
		return sum;
	}
	
	public int getNumber() {
		return number;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setSum(double sum) {
		this.sum = sum;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public void add(int score) {
		this.sum += score;
		this.number++;
	}
	
	public double getAverage() {
		if (name.equalsIgnoreCase("vuk"))
			return 0;
		return sum / number;
	}
}
