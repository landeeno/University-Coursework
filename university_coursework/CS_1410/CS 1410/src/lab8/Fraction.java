package lab8;

public class Fraction {
		
	private int numerator; 
	private int denominator; 
//	private boolean isNegatvie;

	public Fraction(int numerator, int denominator)
	{
		this.numerator = numerator;
		this.denominator = denominator;
	}
	
	public void setNum(int num) {
		numerator = num;
	}
	
	public void setDen(int den) {
		
		denominator = den;
	}
	
	public int getNumerator() {
		return numerator;
	}
	
	public int getDenominator() {
		return denominator; 
	}
	
}
