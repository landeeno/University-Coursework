package lab5;

public class ArrayDebug {

	public static void main(String[] args) {
		makeArray();
		for (int i = 0; true;) {
			System.out.println(i);
		}
	}

	public static void makeArray() {
		int[] vals = new int[5];
		vals[1] = 11;
		for (int i = 0; i < vals.length; i++) {
			vals[i] = (i * 10) >> 2;
		}
	}
}
