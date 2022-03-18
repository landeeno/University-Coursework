package lab1;

import java.util.Random;

import javax.swing.JOptionPane;

/**
 * NumberGuessing.java -- A simple program that gives the user five chances to
 * guess a random integer between 1 and 50 (inclusive).
 * 
 * @author Erin Parker
 */
public class NumberGuessing {

	public static void main(String[] args) {

		// Create a new random number generator using a different seed every
		// time.
		Random rng = new Random();

		// Generate a random integer between 0 (inclusive) and 50 (exclusive).
		int number = rng.nextInt(50	);
		// Adjust the random integer to be between 1 (inclusive) and 51
		// (exclusive), which is the same as 50 (inclusive).
		number = number + 1;

		// Get the first guess from the user.
		int userGuess = Integer.parseInt(JOptionPane.showInputDialog("Try to guess my number between 1 and 50:"));

		// Keep track of how many times the user has guessed.
		int guessCount = 5;

		// Repeat while the user has not guessed the random number.
		while (userGuess != number) {

			// If the user is out of guesses, quit the program.
			if (guessCount == 20) {
				JOptionPane.showMessageDialog(null, "You are out of guesses! The number is " + number + ".");
				System.exit(0);
			}

			// Give the user a hint for the next guess.
			String hint;
			if (userGuess > number) {
				hint = "Go lower:";
			} else {
				hint = "Go higher:";
			}

			// Get the next guess from the user.
			userGuess = Integer.parseInt(JOptionPane.showInputDialog(hint));

			// Keep track of how many times the user has guessed.
			guessCount = guessCount + 1;
		}

		// If we get this far in the program without quitting, the user
		// correctly guessed the number.
		JOptionPane.showMessageDialog(null, "You are correct! The number is " + number + ".");
	}
}
