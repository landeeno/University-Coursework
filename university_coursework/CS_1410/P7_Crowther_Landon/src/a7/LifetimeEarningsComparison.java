
package a7;
import java.text.DecimalFormat;


import javax.swing.JOptionPane;
/**
 * Landon Crowther
 * u0926601
 * HW7 - Lifetime Earnings Comparison
 * @author Landon Crowther
 *
 */
public class LifetimeEarningsComparison {

	public static void main(String[] args) {

		//JOptionPane to collect information for Job 1:
		String firstSal = JOptionPane.showInputDialog("Enter job 1 starting salary: ");
		double job1Sal = Double.parseDouble(firstSal);
		String firstRaise = JOptionPane.showInputDialog("Enter job 1 yearly raise: ");
		double job1Raise = Double.parseDouble(firstRaise);

		//JOptionPane to collect information for Job 2:
		String secondSal = JOptionPane.showInputDialog("Enter job 2 starting salary: ");
		double job2Sal = Double.parseDouble(secondSal);
		String secondRaise = JOptionPane.showInputDialog("Enter job 2 yearly raise: ");
		double job2Raise = Double.parseDouble(secondRaise);

		//JOptionPane to collect number of years:
		String numYears = JOptionPane.showInputDialog("Enter number of years to compute: ");
		int year = Integer.parseInt(numYears);

		//Defining Job classes: 
		LifetimeEarnings Job1 = new LifetimeEarnings(job1Sal, job1Raise);
		LifetimeEarnings Job2 = new LifetimeEarnings(job2Sal, job2Raise);

		//Print salaries for first job:
		System.out.println("Job 1: (" + job1Raise + "% yearly raise, $" + job1Sal + " starting sal.)");
		System.out.println(displayWages(Job1.computeLifetimeEarnings(year)));
		System.out.println(displayTotal(Job1, year));

		System.out.println();

		//Print salaries for second job: 
		System.out.println("Job 2: (" + job2Raise + "% yearly raise, $" + job2Sal + " starting sal.)");
		System.out.println(displayWages(Job2.computeLifetimeEarnings(year)));
		System.out.println(displayTotal(Job2, year));





	}

	/**
	 * This method computes the sum of an array of primitive double salaries
	 * @param wages		array of salaries
	 * @return			total value
	 *			For example:
	 *				sumWages( {1.00, 2.00, 3.00} ) would return:		
	 *				6.00
	 */
	public static double sumWages(double[] wages) {



		//initializing total year's earnings as 0:
		double sumWages = 0.0;

		//check to make sure length of wages[] array isn't 0
		if (wages.length == 0)
			return sumWages;

		//accumulation loop that adds each element of the input array to the current total
		for (int i = 0; i < wages.length; i++) {
			sumWages = sumWages + wages[i];
		}
		return sumWages;
	}

	/**
	 * This sub-method takes an array of salaries and converts them to a string 
	 * with a new line after each element in the input array except for the final element
	 * in the input array
	 * @param wages		input array of salaries
	 * @return			string of salaries
	 * 
	 * 		For example: displayWages( {12.34, 15.11, 18.32} ) would return: 
	 * 			12.34
	 * 			15.11
	 * 			18.32
	 */
	public static String displayWages(double[] wages) {
		//formatter to round to two decimal places: 
		DecimalFormat formatter = new DecimalFormat("#0.00");

		//initialize with empty string:
		String result = "";		

		//loop that cycles through each year's salary:
		for (int i = 0; i < wages.length; i++) {			

			//filling string with the salary for that year:
			result = result + formatter.format(wages[i]);

			//if statement that adds a new line for each element 
			//in the array except the last (display purposes only)
			if (i != wages.length - 1) {
				result = result + "\n";
			}
		}
		return result;
	}

	/**
	 * This sub-method produces the text explaining the return value of the sumWages method
	 * @param job		LifetimeEarnings object with pre-declared starting salary and raise %
	 * @param years		number of years to compute
	 * @return			String that adds description to the sumWages return value
	 */
	public static String displayTotal(LifetimeEarnings job, int years) {
		DecimalFormat formatter = new DecimalFormat("#0.00");
		return "Total wages over " + years + " years: $" + formatter.format(sumWages(job.computeLifetimeEarnings(years)));
	}

}
