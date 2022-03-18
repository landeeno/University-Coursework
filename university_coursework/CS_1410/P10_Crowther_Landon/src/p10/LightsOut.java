package p10;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class LightsOut extends JFrame implements ActionListener {

	//array of LightsOutButtons
	private LightsOutButton[][] buttons;
	//restart button
	private JButton restartButton;
	//solve button
	private JButton solveButton;
	//move count
	private int count;
	//label for count
	private JLabel countLabel;

	/**
	 * constructor for LightsOut game. Constructor sets up the grid with all of the
	 * LightsOutButtons, as well as the panels necessary for Count, Solve, and Restart
	 * button. 
	 */
	public LightsOut() {

		// Exit on close
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		//magic code we're supposed to add.
		try {
			UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
		} catch (Exception e) {
			e.printStackTrace();
		}



		//main panel that will hold everything
		JPanel main = new JPanel();
		main.setLayout(new BorderLayout());

		//game panel 
		JPanel game = new JPanel();
		game.setLayout(new GridLayout(5,5));
		//adding game panel to main
		main.add(game, BorderLayout.CENTER);

		//setting up the buttons
		buttons = new LightsOutButton[5][5];
		for (int row = 0; row < 5; row++) {			
			for (int col = 0; col < 5; col ++) {
				game.add(buttons[row][col] = new LightsOutButton(row, col));
				buttons[row][col].addActionListener(this);
			}
		}

		//randomize the board
		randomize();

		//making lower panel that will contain contain both count and randomize button
		JPanel lowerPanel = new JPanel();
		lowerPanel.setLayout(new GridLayout(1,3));
		lowerPanel.setPreferredSize(new Dimension(1000, 100));

		//adding count panel
		countLabel = new JLabel("Count: " + count);	
		lowerPanel.add(countLabel);

		//making restart button
		restartButton = new JButton("Restart");
		restartButton.addActionListener(this);

		//adding restart button
		lowerPanel.add(restartButton);

		//make solve button
		solveButton = new JButton("Solve");
		solveButton.addActionListener(this);

		//add to lower panel
		lowerPanel.add(solveButton);

		//adding lowerPanel to main JPanel
		main.add(lowerPanel, BorderLayout.SOUTH);

		//setting up JFRame 
		setTitle("Landon Crowther - Lights Out");
		setContentPane(main);
		setPreferredSize(new Dimension(1000, 1000));
		pack();

	}

	/**
	 * method that changes the status of the button clicked and 
	 * all the neighbors
	 * @param x - x coordinate (starting at top left corner) of button
	 * @param y - y coordinate (starting at top left corner and moving down)
	 */
	private void buttonClick(int x, int y) {	

		//change status of clicked button
		buttons[x][y].changeStatus();

		/*
		 * change status of all the neighbors.
		 * each neighbor has an relative if statement
		 * to account for boundary conditions.
		 */

		//north neighbor
		if (y - 1 > -1){
			buttons[x][y-1].changeStatus();
		}

		//east neighbor
		if (x + 1 < 5) {
			buttons[x+1][y].changeStatus();
		}

		//south neighbor
		if (y + 1 < 5) {
			buttons[x][y+1].changeStatus();
		}

		//west neighbor
		if (x - 1 > -1) {
			buttons[x-1][y].changeStatus();
		}


		//update count 
		count++;


		/*
		 * Circumstance for the user winning
		 */
		//checks to see if all tiles black
		if (checkForWin() == true) {

			//update label if user wins
			countLabel.setText("Count: " + count);
			//change color
			makeAllBlue();
			//make pop up message
			JOptionPane.showMessageDialog(null, "You Win!");

		}

	}

	/**
	 * method that changes all buttons to blue for the user winning
	 * Just uses a for loop
	 */
	public void makeAllBlue() {

		for(int x = 0; x < 5; x++){
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j ++) {
					buttons[i][j].blue();
				}
			}	
		}
	}


	/**
	 * helper method that sets up a random solve-able grid
	 */
	private void randomize() {
		//simulates 25 random button clicks
		if (checkForWin() == true)
			//reset colors so that if the board was blue, it no longer is
			resetColors();
		//random button clicks
		for (int i = 0; i < 25; i++){
			buttonClick((int) (Math.random() * 4), (int) (Math.random() * 4));
		}
		//resetting the count
		count = 0;


	}
	/**
	 * This method checks for the value (on or off) of every element 
	 * in the game
	 * @return true if the user wins, false otherwise
	 */
	public boolean checkForWin() {

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j ++) {
				if (buttons[i][j].getStatus() == false)
					return false;
			}
		}
		//true - all buttons are black
		return true; 
	}

	/**
	 * method that utilizes a method in LightsOutButton class.
	 * Basically just gets rid of the blue from the changeColor() method
	 */
	public void resetColors() {
		for (int i = 0; i < 5; i ++) {
			for (int j = 0; j < 5; j++ ) {
				buttons[i][j].resetColor();
			}
		}
	}

	/**
	 * actionPerformed method for class
	 */
	public void actionPerformed(ActionEvent e) {


		// case for the restart Button
		if ( e.getSource() == restartButton) {
			//only applicable if the user has previously won the game
			//basically ensures that there is no blue spots left on board
			if (checkForWin() == true) {
				resetColors();
			}
			//re-randomize the board
			randomize();
			//update the count
			countLabel.setText("Count: " + count);
		}
		//case for solveButton
		else if	(e.getSource() == solveButton) {
			solve();

		}
		
		else {
			//ensures the user can't click buttons after winning the game until new game is started
			if (checkForWin() == true) {
				JOptionPane.showMessageDialog(null, "You must first start a new game");
			}
			//case for buttons being pressed
			else {

				LightsOutButton gridButton = (LightsOutButton)e.getSource();
				//simulate the buttonClick method for whichever button the user clicks
				buttonClick(gridButton.getRow(), gridButton.getCol());
				countLabel.setText("Count: " + count);
			}

		}

	}

	/**
	 * solve method
	 */
	private void solve() {
		//prevents user from solving a pre-solved game
		if (checkForWin() == true) {
			JOptionPane.showMessageDialog(null, "You must first start a new game");
		}
		//solve method
		else {

			//chasing method
			for (int yval = 1; yval < 5; yval ++) {
				for (int xval = 0; xval < 5; xval++) {
					if (buttons[yval-1][xval].getStatus() == false) {
						buttonClick(buttons[xval][yval].getCol(), buttons[xval][yval].getRow());
					}


				}
			}

			//checking for bottom-row conditions and clicking appropriate buttons in top row
			if (buttons[4][0].getStatus() == false && buttons[4][1].getStatus() == true 
					&& buttons[4][2].getStatus() == true) {
				buttonClick(0, 0);
				buttonClick(0, 1); }

			else if (buttons[4][0].getStatus() == true && buttons[4][1].getStatus() == false 
					&& buttons[4][2].getStatus() == true) {
				buttonClick(0, 0);
				buttonClick(0, 3); }

			else if (buttons[4][0].getStatus() == false && buttons[4][1].getStatus() == false 
					&& buttons[4][2].getStatus() == false)
				buttonClick(0, 1);

			else if (buttons[4][0].getStatus() == true && buttons[4][1].getStatus() == true 
					&& buttons[4][2].getStatus() == false)
				buttonClick(0, 3);

			else if (buttons[4][0].getStatus() == false && buttons[4][1].getStatus() == true 
					&& buttons[4][2].getStatus() == false)
				buttonClick(0, 4);

			else if (buttons[4][0].getStatus() == true && buttons[4][1].getStatus() == false 
					&& buttons[4][2].getStatus() == false)
				buttonClick(0, 0);

			else 
				buttonClick(0, 2);

			//chase again
			for (int yval = 1; yval < 5; yval ++) {
				for (int xval = 0; xval < 5; xval++) {
					if (buttons[yval-1][xval].getStatus() == false) {
						buttonClick(buttons[xval][yval].getCol(), buttons[xval][yval].getRow());
					}


				}
			}



		}

	}

	// Required by a serializeable class (ignore for now)
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		LightsOut l = new LightsOut();
		l.setVisible(true);

	}

}
