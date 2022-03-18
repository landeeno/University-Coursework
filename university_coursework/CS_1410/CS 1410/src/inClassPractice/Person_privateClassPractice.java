package inClassPractice;

public class Person_privateClassPractice {
	
	//define data
	private String name;
	private String city;
	private String state;
	private int age;
	
	//Constructor
	public Person(String _name, String _city, String _state, int _age) {
		name = _name;
		city = _city;
		state = _state;
		age = _age;
	}
	
	public Person() {
		name = "Joe Blow";
		city = "NY";
		state = "NY";
		age = 0;
	}
	
	public boolean livesInUtah() {
		return state.equals("UT");
	}