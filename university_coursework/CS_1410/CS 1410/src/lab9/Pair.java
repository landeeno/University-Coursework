package lab9;

import java.util.*;

public class Pair <T, E> {

	private T left;
	private E right;

	public Pair (T left, E right) {
		this.left = left;
		this.right = right;

	}

	public String toString() {
		return "(" + left + ", " + right + ")";
	}

	@Override
	public boolean equals(Object other) {
		//checks to see of "other" is a Pair object
		if (other instanceof Pair) {
			Pair<T, E> newOther = (Pair) other;
			if (this.left.equals(newOther.left) && this.right.equals(newOther.right) )
				return true;
			return false;
		}
		return false;
	}


	public static void main(String[] args) {
		//		Pair<String, Integer> pair1 = new Pair<String, Integer>("Landon", 21);
		//		System.out.println(pair1.toString());
		//
		//		ArrayList<String> list1 = new ArrayList<String>();
		//		list1.add("Landon");
		//		list1.add("Crowther");
		////		list1.remove("Landon");
		////		System.out.println(list1);
		//		
		//		System.out.println(list1.indexOf("Crowther"));

		ArrayList<Pair<String, String>> list2 = new ArrayList<Pair<String, String>>();
		list2.add(new Pair<String,String>("Hello", "World"));
		list2.add(new Pair<String, String>("Hello", "again"));
		System.out.println(list2);
		
		list2.remove(new Pair<String, String>("Hello", "World"));

	}
}
