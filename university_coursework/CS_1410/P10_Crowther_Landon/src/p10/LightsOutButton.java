package p10;

import java.awt.Color;

import javax.swing.*;

public class LightsOutButton extends JButton {

	//row number index
	private int rowNum;
	//column number index
	private int colNum;
	//boolean for status of light (true = light is off, false = light is on)
	private boolean status;
	
	


	/**
	 * constructor method - essentially gives each button a coordinate
	 * @param rowNum
	 * @param colNum
	 */
	public LightsOutButton(int rowNum, int colNum) {
		this.rowNum = rowNum;
		this.colNum = colNum;
		//setting light to "off"
		status = true;
		setBackground(Color.black);
	}
	
	/**
	 * getter method for row number
	 * @return
	 */
	public int getRow() {
		return rowNum;
	}
	
	/**
	 * getter method for column number
	 * @return
	 */
	public int getCol() {
		return colNum;
	}

	/**
	 * helper method that sets changes the background to blue (winning scenario)
	 */
	public void blue() {		
		setBackground(Color.blue);
	}
	
	/**
	 * helper method that switches the value (boolean) of a button
	 * when it is clicked. It also accounts for color changes
	 */
	public void changeStatus() {
		//change status for clicked button 
		status = !status;	
		//make sure that the tiles are the right color 
		//black = off, white = on
		resetColor();
	}

	/**
	 * additional helper method that accounts for the color changes
	 * based on a button's boolean value. This method is called by the LightsOut
	 * class when the user wins the game and wants to continue playing. 
	 */
	public void resetColor() {
		if (status == true)
			setBackground(Color.black);
		else
			setBackground(Color.white);
	}
	
	/**
	 * getter method for the status of button
	 * @return
	 */
	public boolean getStatus() {
		return status;
	}

	//magic code that I don't know the purpose of
	private static final long serialVersionUID = 1L;
//end of class
}
