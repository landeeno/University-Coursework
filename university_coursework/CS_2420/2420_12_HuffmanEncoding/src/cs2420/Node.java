package cs2420;

import static cs2420.Utility.printable_symbol;

/**
 * Represents a single node of a Huffman Tree. These nodes are
 * used during the Huffman Tree compression/decompression. 
 * 
 * @author Brent Collins and Landon Crowther
 *  */
class Node implements Comparable<Node> {

	
	private String symbol;
	private Node left;
	private Node right;
	private int frequency;
	private Node parent;



	/**
	 * Constructs a leaf node.
	 * 
	 * @param sym
	 *            - the symbol
	 * @param frequency
	 *            - how often the symbol occurs
	 */
	public Node(String sym, int frequency) {
		this.symbol = sym;
		this.frequency = frequency;
		
	}

	/**
	 * Constructs an internal node. Note that a non-leaf/internal node has a
	 * weight (sum of the weights of its children) but no character.
	 * 
	 * @param left
	 *            - left child of the new node
	 * @param right
	 *            - right child of the new node
	 */
	public Node(String sym, Node left, Node right) {
		this.symbol = sym;
		this.left = left;
		this.right = right;
		this.frequency = left.get_frequency() + right.get_frequency();
		left.parent = this;
		right.parent = this;
		
	}

	/**
	 * @return the symbol associated with the node
	 */
	public String get_symbol() {
		return this.symbol;
	
	}

	/**
	 * concisely print the Node
	 */
	public String toString() {
		String toString = "";
		toString += this.symbol;
		toString += ": " + this.frequency;
		return toString;
		
	}

	/**
	 * @return true if a leaf (valid symbol) node
	 */
	boolean leaf() {
		if (this.left == null && this.right == null) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Setter for parent node
	 * 
	 * @param parent
	 */
	public void set_parent(Node parent) {
		this.parent = parent;
		
	}

	/**
	 * @return the parent of this node
	 */
	public Node get_parent() {
		return this.parent;

	}

	/**
	 * @return the left child of the parent of this node
	 */
	public Node parents_left() {
		return this.parent.left;

	}

	/**
	 * @return the weight (frequency of appearance) associated with this node
	 */
	public int get_frequency() {
		return this.frequency;

	}

	/**
	 * add one to the frequency field
	 */
	public void increment_frequency() {
		this.frequency += 1;

	}

	/**
	 * WARNING: only call this method on the "root" of the tree
	 * 
	 * Returns the symbol encoded by a bit string, by traversing the path from
	 * the root of the tree to the leaf node containing the character. A '0' in
	 * the bit string causes the path to follow a left child, and a '1' in the
	 * bit string causes the path to follow a right child.
	 * 
	 * @param code
	 *            - bit string to be decoded, such as "01010001"
	 * @return return null if the bit string does not lead to a symbol,
	 *         otherwise return the symbol string
	 */
	String get_symbol(String code) {
		if (this.get_parent() != null) {
			throw new RuntimeException("can only be called on root node");
		}

		Node current = this;
		for (int i = 0; i < code.length(); i++) {

			if (code.charAt(i) == '0') {
				if (current.left != null) {
					current = current.left;
				} else {
					return null;
				}

			} else {
				if (current.right != null) {
					current = current.right;
				} else {
					return null;
				}

			}
		}

		if (!current.leaf()) {
			return null;
		}
		return current.symbol;

	}

	/**
	 * @return the left most child of this node
	 */
	private Node left_most_child() {
		

		if (this.leaf()) {
			return this;
		}

		return this.left.left_most_child();
	
	}

	/**
	 * Compare to Huffman nodes, using frequencies.
	 * 
	 * @param rhs
	 *            - right-hand side node
	 * @return a value > 0 if this node is larger than rhs, a value < 0 if this
	 *         node is smaller than rhs, 0 if the nodes are equal
	 * 
	 *         larger means node occurs more often (has a higher frequency).
	 *         when tied, compare the symbol of this node's left most child vs
	 *         the symbol of rhs.left_most_child
	 */
	public int compareTo(Node rhs) {
		int diff = this.frequency - rhs.frequency;
		if (diff != 0) {
			return diff;
		} else {
			return this.left_most_child().get_symbol().compareTo(rhs.left_most_child().get_symbol());
		}

	}

	// ------------------ DOT description methods and data
	// -------------------------------

	static int null_count = 0;

	/**
	 * write the edges in the graph in the form used by the DOT language
	 * 
	 * @param print_line
	 *            - the file to print to
	 * @param null_count
	 *            - to keep track of nice "null" edges, we need this as each one
	 *            has to have a new name
	 */
	public String createDot() {
		String result = "";

		null_count = 0;

		result += "digraph huffman_tree{\n";

		result += "\thuffman_root -> " + this.symbol + ";\n";
		result += "\thuffman_root [shape=\"none\"];\n";

		// recursively build the dot file
		result += write_dot_helper();

		// create all the null values so they look goodn
		for (int i = 0; i < null_count; i++) {
			result += "null" + i + " [shape=point];\n";
		}

		result += "}";

		return result;
	}

	/**
	 * create the DOT syntax for a graph
	 * 
	 * @param print_line
	 */
	public String write_dot_helper() {
		String result = "";

		String label = printable_symbol(this.symbol);

		if (leaf()) {
			result += "\t" + label + " [label=\"" + label + "\\n" + this.frequency + "\"]\n";

			return result;
		}

		result += "\t" + label + " [label=\"" + label + "\\n" + this.frequency + "\"]\n";

		if (this.left == null || this.right == null) {
			System.out.println("ERROR");
			throw new RuntimeException(" nodes must eith have 0 or 2 children");
		}

		String left_label = printable_symbol(left.symbol);
		String right_label = printable_symbol(right.symbol);

		result += "\t" + label + "-> " + left_label + "[ label=0 ]\n";
		result += "\t" + label + "-> " + right_label + "[ label=1 ]\n";
		result += this.left.write_dot_helper();
		result += this.right.write_dot_helper();

		return result;
	}
	
	/**
	 * getter for the left node
	 * @return the left node from the current node
	 */
	public Node getLeft() {
		return this.left;
	}
	
	/**
	 * getter for the right node
	 * @return the left node from the current node
	 */
	public Node getRight() {
		return this.right;
	}

}
