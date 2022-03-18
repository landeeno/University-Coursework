

package inClassPractice;

public class Van extends Car {
	
	public Van(String color) {
		super(color, false);
	}
	
	public int getCapacity() {
		return 8;
	}
	
	public void setColor(int color) {
		switch(color) {
		case 0:
			setColor("merlot");
			break;
		case 1:
			setColor("black");
			break;
		case 2:
			setColor("white");
		}
	}
	
	public String openDoor() {
		return "Yeek!";
	}
}  // end Van

