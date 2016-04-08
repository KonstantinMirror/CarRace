package epamlab;

public class Main {
	
	public static void main(String[] args) {
		Car[] cars = new Car[3];
		cars[0] = new Car("Piter", 10);
		cars[1] = new Car("Alex", 10);
		cars[2] = new Car("Mikel", 10);
		StartManager.startRase(cars);
		System.out.println(StartManager.getWinner());
	}

}
