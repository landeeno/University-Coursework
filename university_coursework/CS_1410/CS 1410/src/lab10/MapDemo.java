package lab10;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.TreeMap;
//import java.util.TreeMap;

public class MapDemo {

	public static void main(String[] args) {
		ArrayList<String> arr = new ArrayList<String>();
		
		arr.add("cat");
		arr.add("ant");
		arr.add("dog");
		
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("c", "That-one-motha-fucka-Jesus");
		map.put("b", "Landon");
		map.put("a", "Kent");
		
		
		//System.out.println(map.values() + "\n");
		
		for (String s : map.keySet() ) {
			System.out.println( s );
			}
		
		System.out.println(map.remove("a "));
		
		for (String s : map.keySet() ) {
			System.out.println( s );
			}
		
		Object s = new TreeMap();
	}
	
}
