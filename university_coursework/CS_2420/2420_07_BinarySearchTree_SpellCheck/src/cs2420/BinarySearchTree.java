package cs2420;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * This class is similar to Java's treeSet, but called a BinarySearchTree
 * instead
 * 
 * A Binary Search Tree is a data structure where an object is placed in the
 * "root" node. Any additional objects are placed on their comparison to the
 * "root". Any object that is "less than" the root goes to the left, and
 * anything "greater than" goes to the right.
 * 
 * @author Dezeray Kowalski & Landon Crowther
 *
 * @param <Type>
 */
public class BinarySearchTree<Type extends Comparable<? super Type>> implements SortedSet<Type> {

	// top node in the BST
	Node root = new Node(null);
	// keep track of number of elements in BST
	int counter;

	/**
	 * Constructor -
	 */
	public BinarySearchTree() {
		counter = 0;
	}

	/**
	 * 
	 * Node is the structure contianing the actual data for the BST. In each
	 * node, there are 3 components: the data, a reference to the left node and
	 * a reference to the right node.
	 * 
	 * Pictorially, a node is:
	 *
	 * left data right --------------- <--+ | 5 | +---> ---------------
	 * 
	 * Note, while a 5 is used above any "Type" could be contained in the node
	 */
	private static class Node<Type extends Comparable<? super Type>> {

		protected Type data;
		protected Node<Type> left, right;

		Node(Type the_data) {
			this.data = the_data;
			this.left = this.right = null;
		}

		/**
		 * Helper method that determines how many children a parent node has,
		 * and if there is only 1 child, determines if it is on left or right
		 * 
		 * @return - 0 if there are no children, 2 if there are 2 children, -1
		 *         if only child is on left, 1 if only child is on right
		 */
		private int childrenAnalysis() {
			if (this.left == null && this.right == null) {
				return 0;
			}

			else if (this.left != null && this.right != null) {
				return 2;
			}

			else if (this.left != null) {
				return -1;
			}

			else {
				return 1;
			}
		}

		/**
		 * 
		 * This function must be written recursively.
		 *
		 * Height is defined as the 1 plus the maximum height of the left vs
		 * right sub tree
		 * 
		 * @return the height from this node to its leaves
		 * 
		 * 
		 */
		protected int height() {
			if (this.left == null && this.right == null) {
				return 1;
			}

			int leftHeight, rightHeight;
			leftHeight = rightHeight = 0;

			if (left != null) {
				leftHeight = left.height();
			}

			if (right != null) {
				rightHeight = right.height();
			}

			return Math.max(leftHeight, rightHeight) + 1;
		}

		/**
		 * recursively determine if the item is in this node or the nodes after
		 * 
		 * @param item
		 *            - needle
		 * @return true if item in tree
		 */
		protected boolean contains(Type item) {

			// base case
			int comparison = item.compareTo(this.data);

			if (comparison == 0) {
				return true;
			}

			// check to see if left and right are null, add recursive call
			if (comparison < 0 && this.left != null) {
				return left.contains(item);
			}

			if (comparison > 0 && this.right != null) {
				return right.contains(item);
			}

			// item not found, return false;
			return false;

		}

		/**
		 * recursively traverse the BST. Note, this helper method takes a
		 * ArrayList as a paramter, and puts all the elements in the BST into
		 * the AL in sorted order
		 * 
		 * @param items
		 *            - ArrayList (empty)
		 */
		protected void traverseInOrder(ArrayList<Type> items) {
			if (this.left != null) {
				this.left.traverseInOrder(items);
			}

			items.add((Type) this.data);

			if (this.right != null) {
				this.right.traverseInOrder(items);
			}

		}

		/**
		 * this method returns a string (used for the DOT implementation) of the
		 * BST in the current order it is in
		 * 
		 * @return - String in DOT format
		 */
		protected String traverseOriginalOrder() {

			String result = "";

			if (this.left != null) {

				result += this.data + " -> " + this.left.data + "; \n";

				result += this.left.traverseOriginalOrder();

			}

			if (this.left == null) {

				result += this.data + " -> null" + this.data + "; \n";
				result += "null" + this.data + " [shape=point];\n";

			}

			if (this.right != null) {

				result += this.data + " -> " + this.right.data + "; \n";

				result += this.right.traverseOriginalOrder();
			}

			if (this.right == null) {

				result += this.data + " -> null" + this.data + "; \n";
				result += "null" + this.data + "[shape=point];\n";

			}

			return result;
		}

		/**
		 * recursive - add a node
		 * 
		 * @param item
		 *            - data to add
		 * @return
		 */
		protected void insert(Type item) {

			// if data = null:
			if (this.data == null) {
				this.data = item;
				return;
			}

			// this < item (add to the right)
			if (this.data.compareTo(item) < 0) {
				// base case
				if (this.right == null) {
					this.right = new Node(item);
					return;
				} else {
					this.right.insert(item);
				}
			}

			// this < item (add left)
			else if (this.data.compareTo(item) > 0) {
				// base case
				if (this.left == null) {
					this.left = new Node(item);
					return;
				} else {
					this.left.insert(item);
				}
			}

			// this == item (one last check for duplicate
			else {
				return;
			}

		}

		/**
		 * This helper method "searches" the tree for the node associated with a
		 * given item. It works by using compareTo from the root node, and uses
		 * recursion to search downward until the Node containing "item" is
		 * found.
		 * 
		 * Note, the "item" is GUARANTEED to be in the tree, because the
		 * contains() method was previously verified before this method was ever
		 * called.
		 * 
		 * @param item
		 *            - data that the node we are looking for contains
		 * @return - the node containing the item
		 */
		private Node<Type> findParent(Type item) {

			int childAnalysis = this.childrenAnalysis();

			if (Math.abs(childAnalysis) == 1) { // one child
				if (childAnalysis == 1) {
					if (this.right.data.compareTo(item) == 0) {
						return this;
					} else {
						return this.right.findParent(item);
					}
				} else {
					if (this.left.data.compareTo(item) == 0) {
						return this;
					} else {
						return this.left.findParent(item);
					}
				}
			}

			// base case (node with children containing item)
			if (this.left.data.compareTo(item) == 0 || this.right.data.compareTo(item) == 0) {
				return this;
			}

			// see if "item" is to the left:
			if (item.compareTo(this.data) < 0) {
				return this.left.findParent(item);
			}

			// item is to the right:
			else {
				return this.right.findParent(item);
			}

		}

		/**
		 * @return The leftmost node in the binary tree rooted at this node.
		 */
		public Node<Type> getLeftmostNode() {
			if (left == null)
				return this;

			return left.getLeftmostNode();
		}

		/**
		 * @return The leftmost node in the binary tree rooted at this node.
		 */
		public Node<Type> getRightmostNode() {

			if (right == null)
				return this;

			return right.getRightmostNode();
		}

	}

	/**
	 * Adds an item into the BST in the correct location
	 * 
	 * true if item is added, false if item already exists
	 */
	@Override
	public boolean add(Type item) {
		// see if tree is empty
		if (root.data == null) {
			// root.data = item;
			// root.left = root.right = null;
			root.insert(item);
			counter++;
			return true;
		}

		// tree is not empty, check for duplicates
		if (this.contains(item) == true) {
			return false;
		} else
		// no duplicates, insert item
		{
			root.insert(item);
			counter++;
			return true;
		}
	}

	/**
	 * Adds a collection of elements to a BST
	 * 
	 * returns true if everything was added successfully, and false otherwise
	 */
	@Override
	public boolean addAll(Collection<? extends Type> items) {
		boolean keepGoing = true;

		for (Type t : items) {
			keepGoing = this.add(t);
			if (keepGoing == false) {
				break;
			}
		}

		return keepGoing;
	}

	/**
	 * resets a BST
	 */
	@Override
	public void clear() {
		root.left = root.right = null;
		this.root.data = null;
		counter = 0;
	}

	/**
	 * determin if "item" parameter is in BST
	 */
	@Override
	public boolean contains(Type item) {
		// check for empty:
		if (counter == 0) {
			return false;
		}

		return this.root.contains(item);
	}

	/**
	 * determine if all items in Collection are in BST
	 */
	@Override
	public boolean containsAll(Collection<? extends Type> items) {
		boolean keepGoing = true;

		for (Type t : items) {
			keepGoing = this.contains(t);
			if (keepGoing == false) {
				break;
			}
		}

		return keepGoing;
	}

	/**
	 * return the "lowest" element in BST
	 */
	@Override
	public Type first() throws NoSuchElementException {
		if (root.data == null) {
			throw new NoSuchElementException();
		}

		Node current = root;
		while (current.left != null) {
			current = current.left;
		}
		return (Type) current.data;

	}

	/**
	 * determine if BST is empty (aka no elements)
	 */
	@Override
	public boolean isEmpty() {
		if (size() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * returns the "highest" element in the BST
	 */
	@Override
	public Type last() throws NoSuchElementException {
		if (root.data == null) {
			throw new NoSuchElementException();
		}

		Node current = root;
		while (current.right != null) {
			current = current.right;
		}
		return (Type) current.data;
	}

	/**
	 * remove the item from the bst
	 * 
	 * @return true if actually removed, false if "item" not in BST
	 */
	@Override
	public boolean remove(Type item) {

		// item is not in BST, return false;
		if (contains(item) == false) {
			return false;
		}

		// Dummy node for root's parent
		Node<Type> dummy = new Node<>(null);
		dummy.left = root;
		// item is GUARANTEED to be in BST:
		Node<Type> parent;

		// check to see if root is the item we are removing:
		if (root.data.compareTo(item) == 0) {
			parent = dummy;
		} else {
			parent = root.findParent(item);
		}

		// check if child is on left or right:
		Node<Type> child;

		// determine if Node containing item is on the left or right of parent
		boolean nodeContainingItemOnLeftOfParent;

		int parentAnalysis = parent.childrenAnalysis();

		switch (parentAnalysis) {

		case -1:
			// parent has ONLY left children:
			child = parent.left;
			nodeContainingItemOnLeftOfParent = true;
			break;
		case 1:
			// parent has ONLY right child
			child = parent.right;
			nodeContainingItemOnLeftOfParent = false;
			break;
		default:
			// parent has TWO children, determine which side item is on
			if (parent.left.data.compareTo(item) == 0) {
				child = parent.left;
				nodeContainingItemOnLeftOfParent = true;
			} else {
				child = parent.right;
				nodeContainingItemOnLeftOfParent = false;
			}

		}

		int childAnalysis = child.childrenAnalysis();
		switch (childAnalysis) {

		case 0:
			// child has no children

			if (nodeContainingItemOnLeftOfParent == true) {
				parent.left = null;
			} else {
				parent.right = null;
			}
			break;
		case 1:
			// child has one child (on right)

			if (nodeContainingItemOnLeftOfParent == true) {
				parent.left = child.right;
			} else {
				parent.right = child.right;
			}
			break;
		case -1:
			// child has one child (on left)

			if (nodeContainingItemOnLeftOfParent == true) {
				parent.left = child.left;
			} else {
				parent.right = child.left;
			}
			break;
		case 2:
			// child has two children
			Node<Type> successor = child.left.getRightmostNode();
			Node<Type> successorParent = root.findParent(successor.data);

			child.data = successor.data;

			int successorAnalysis = successor.childrenAnalysis();
			switch (successorAnalysis) {

			case 0:
				if (successorParent.left != null) {
					successorParent.left = null;
				} else {
					successorParent.right = null;
				}
				break;
			case -1:
				if (successorParent.left != null) {
					successorParent.left = successor.left;
				} else {
					successorParent.right = successor.left;
				}
				break;
			}
			break;
		}

		counter--;
		return true;

	}

	/**
	 * remove all elements in collection
	 */
	@Override
	public boolean removeAll(Collection<? extends Type> items) {
		boolean keepGoing = true;

		for (Type t : items) {
			keepGoing = this.remove(t);
			if (keepGoing == false) {
				break;
			}
		}

		return keepGoing;
	}

	@Override
	public int size() {
		return counter;
	}

	@Override
	public ArrayList toArrayList() {
		ArrayList result = new ArrayList<>();
		root.traverseInOrder(result);
		return result;

	}

	/**
	 * Creates a .dot file that can be used in the graphviz software to produce
	 * a visual representation of the BST
	 * 
	 * @param filename
	 *            - example.dot
	 */
	void writeDot(String filename) {
		String result = "";
		result = root.traverseOriginalOrder();
		try {
			PrintWriter writer = new PrintWriter(filename);
			writer.print(result);

			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
