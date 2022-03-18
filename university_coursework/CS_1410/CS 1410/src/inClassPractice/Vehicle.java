package inClassPractice;

import java.util.ArrayList;

public class Vehicle {
	
	private String make;
	private String model;
	private int door;
	private double cost;

	public Vehicle(String make, String model, int door, double cost) {
		this.make = make;
		this.model = model;
		this.door = door;
		this.cost = cost;		
	}
	
	public String getMake() {
		return make;
	}

	public String getModel() {
		return model;
	}

	public int getDoor() {
		return door;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double _cost) {
		cost = _cost;
	}

	public String toString() {
		return getMake() + " " + getModel() + " with " + getDoor() + " door(s) costs $" + getCost();
	}

	public static boolean array220(int[] nums, int index) {
		  
		  if (index == nums.length-1)
		    return false;
		    
		  if( nums[index+1] == index*10)
		    return true;
		  else
		    return array220(nums, index+1);
		  
		  
		}
	
	public static void main(String[] args) {
		int[] arr = {1, 2, 20};
		System.out.println(array220(arr, 0));
	}
	
}
