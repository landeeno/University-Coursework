// Landon Crowther
// u0926601
// Assignment 1 - Driving Cost Calculator
// CS 1410 - 001

package a1;
import javax.swing.JOptionPane; // The 'import' alerts the compiler to use additional features

public class DrivingCostCalculator {
	
	public static void displayBanner() {
		// method for creating the banner
		
		System.out.println(" ");
		System.out.println("******************  :)  ***********************");
		System.out.println("*           Driving Cost Calculator           *");
		System.out.println("******************* :)  ***********************");
		System.out.println(" ");
		 	
	}
	
	public static void main(String[] args) {
		
		String drivingDistance = JOptionPane.showInputDialog("Enter the driving distance (miles):");
		double mileage = Double.parseDouble(drivingDistance);
		// Collecting input for the distance of the trip
		
		String carEfficiency = JOptionPane.showInputDialog("Enter your vehicle's fuel efficiency (miles/gallon):");
		double mpg = Double.parseDouble(carEfficiency);
		// collecting input for the car's fuel efficiency
		
		String gasPrice = JOptionPane.showInputDialog("Enter the fuel cost ($/gal):");
		double gasCost = Double.parseDouble(gasPrice);	
		// collecting input for the cost of gas
		
		double gallonsUsed = mileage / mpg;
		// calculating the amount of gallons required for the trip
		
		double finalCost = gallonsUsed * gasCost;
		// calculating the final cost
		
		displayBanner();
		// referencing the method that creates the banner
		
		System.out.println("The cost of the trip is $" + finalCost);
		// displaying the fuel cost beneath the banner
		
	}
}

