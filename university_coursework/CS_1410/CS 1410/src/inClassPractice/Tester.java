package inClassPractice;

public class Tester {
	public static void main(String[] args) {
		Van v = new Van("blue");
		//System.out.println(v.getCapacity());
		v.setColor(1);
		//System.out.println(v.getColor());
		
		Car c = new Van("red");
		int x = c.getCapacity();
		System.out.println(x);
		
		
		
	}
} // end Tester