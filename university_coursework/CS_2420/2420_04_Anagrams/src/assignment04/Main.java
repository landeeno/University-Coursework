package assignment04;

import java.util.Arrays;


public class Main 
{
	
	public static void main(String[] args)
	{
		String[] testArray = AnagramUtil.getLargestAnagramGroup("Resources/words");
		System.out.println(Arrays.toString(testArray));
		
	}

}
