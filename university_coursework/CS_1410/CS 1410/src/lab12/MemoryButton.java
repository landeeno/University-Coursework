package lab12;

import java.awt.Font;

import javax.swing.JButton;
import java.awt.Color;
public class MemoryButton extends JButton {

	private int secretValue;   // value under the ?
	
	/** 
	 * Create a button for the Memory Game.
	 * 
	 * @param _secretValue -- the value to hide under a ?
	 */
	public MemoryButton(int _secretValue) {
		super("?");
		secretValue = _secretValue;
		setFont(new Font("Dialog", Font.PLAIN, 24));
	}

	/**
	 * Make the secret value visible to the player.
	 */
	public void showValue() {
		setBackground(Color.yellow);
//		setOpaque(true);
//		setBorderPainted(false);
		setText("" + secretValue);
	}
	
	/**
	 * Hide the secret value from the player.
	 */
	public void hideValue() {
		setBackground(null);
		setText("?");
	}
	
	/**
	 * Getter for the secret value.
	 * 
	 * @return the secret value
	 */
	public int getValue() {
		return secretValue;
	}
	
	// Required by a serializable class (ignore for now)
	private static final long serialVersionUID = 1L;
}