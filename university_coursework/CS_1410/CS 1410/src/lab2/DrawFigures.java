package lab2;

public class DrawFigures {
	/**
     * Prints out a diamond, an X, and a rocket.
     */
	public static void upperCarrot() {
		System.out.println("   /\\");
		System.out.println("  /  \\");
		System.out.println(" /    \\");
	}
	
	public static void lowerCarrot() {
		System.out.println(" \\    /");
		System.out.println("  \\  /");
		System.out.println("   \\/");	
	}
	public static void space() {
		System.out.println();
	}
	
	public static void drawBoxLabel(String label) {
		System.out.println("|" + label + "|");
		System.out.println("|      |");
	}
	
	public static void drawRocket(String label) {
		upperCarrot();
		drawBox();
		drawBoxLabel(label);
		drawBox();
		upperCarrot();
	}
	
	public static void drawBox() {
		System.out.println("+------+");
		System.out.println("|      |");
		System.out.println("|      |");
		System.out.println("+------+");
	}
	public static void main (String[] args)	{
		
		upperCarrot();
		lowerCarrot();
		space();
		lowerCarrot();
		upperCarrot();
		space();
		drawRocket("CS1400");
		
//		System.out.println("   /\\");
//		System.out.println("  /  \\");
//		System.out.println(" /    \\");
//		System.out.println(" \\    /");
//		System.out.println("  \\  /");
//		System.out.println("   \\/");
//		System.out.println();
//		System.out.println(" \\    /");
//		System.out.println("  \\  /");
//		System.out.println("   \\/");
//		System.out.println("   /\\");
//		System.out.println("  /  \\");
//		System.out.println(" /    \\");
//		System.out.println();
//		System.out.println("   /\\");
//		System.out.println("  /  \\");
//		System.out.println(" /    \\");
//		System.out.println("+------+");
//		System.out.println("|      |");
//		System.out.println("|      |");
//		System.out.println("+------+");
//		System.out.println("|CS1410|");
//		System.out.println("|      |");
//		System.out.println("+------+");
//		System.out.println("|      |");
//		System.out.println("|      |");
//		System.out.println("+------+");
//		System.out.println("   /\\");
//		System.out.println("  /  \\");
//		System.out.println(" /    \\");
	}
}
