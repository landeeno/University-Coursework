package a7;

/**
 * Landon Crowther
 * u0926601
 * HW7 - Lifetime Earnings Comparison
 * @author Landon Crowther
 *
 */
public class LifetimeEarnings {

	private double startingSalary;
	private double raisePercentage;
	private int currentYear;

	/**
	 * constructor
	 * @param _startingSalary	starting salary
	 * @param _raisePercentage	fixed yearly raise percentage
	 */
	public LifetimeEarnings(double _startingSalary, double _raisePercentage) {

		//ensure that the starting salary is greater than 0
		if (_startingSalary >= 0) {
			startingSalary = _startingSalary; 
		}

		raisePercentage = _raisePercentage;
		currentYear = 0;
	}

	/**
	 * This method computes the starting salary based on the instance variables
	 * @return salary computation
	 */
	public double currentSalary() {
		return startingSalary * Math.pow(1.0 + raisePercentage/100.0, currentYear);		
	}

	/**
	 * This public method sets the current year for a LifetimeEarnings object
	 * @param _currentYear - value for the year input
	 */
	public void setYear(int _currentYear) {
		if (_currentYear >= 0) {
			currentYear = _currentYear;
		}
	}

	/**
	 * This method adds 1 to the current year
	 */
	public void incrementYear() {
		currentYear++;
	}

	/**
	 * This method computes the salary of a LifetimeEarnings object for numYears
	 * @param numYears - number of years to compute
	 * @return - double array of salaries for numYears years
	 */
	public double[] computeLifetimeEarnings(int numYears) {
		
		if (numYears < 0)
			return null;		
		
		double[] salary = new double[numYears];
		for (int i = 0; i < salary.length; i++) {
			currentYear = i;
			salary[i] = currentSalary();
		}
		return salary;

	}

	/**
	 * This method returns a string stating the current instance 
	 * variables startingSalary, raisePercentage, and currentYear
	 */
	public String toString() {
		return "Current year: " + currentYear + "\nRaise percentage: " + raisePercentage + "\nStarting salary: " + startingSalary + "\n";
	}


	/**
	 * Created this sub-method to compare each of the instance variables of two LifetimeEarnings variables
	 * @param job	LifetimeEarnings object
	 * @return		true if ALL of the object variables are equivalent, false otherwise
	 * 
	 * note -- not required, just created for testing purposes.
	 */
	public boolean equals(LifetimeEarnings job) {
		if (this.startingSalary == job.startingSalary && this.raisePercentage == job.raisePercentage && this.currentYear == job.currentYear) 
			return true;
		else 
			return false;
	}
}
