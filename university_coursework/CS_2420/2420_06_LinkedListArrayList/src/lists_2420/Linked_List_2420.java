package lists_2420;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Linked list, single direction, implementation using nodes.
 * 
 * @author Brent Collins Landon Crowther
 *
 */

public class Linked_List_2420 implements List_2420
{
	private int counter;
	private Node first;

	/**
	 * static class to create nodes
	 * 
	 * @author Brent Collins, Landon Crowther
	 *
	 * @param <Type>
	 *            generic type
	 */
	static class Node<Type>
	{
		Type data;
		Node next;

		/**
		 * constructor
		 * 
		 * @param the_data
		 *            - the data to be added to the node
		 * @param after_me
		 *            - the next node to point to
		 */
		public Node(Type the_data, Node<Type> after_me)
		{
			this.data = the_data;
			this.next = after_me;
		}

		/**
		 * helper method to recursively compute the length of the length of the
		 * linked list
		 * 
		 * @return the number of elements in the linked list
		 */
		public int length()
		{
			if (this.next == null)
			{
				return 1;
			} else
			{
				return 1 + this.next.length();
			}

		}

		/**
		 * recursively iterate through the list to check if a value is contained
		 * in the list.
		 * 
		 * @param -
		 *            item to check if it is contained within the linked list
		 * @return true if it is in the list, false otherwise
		 */
		public boolean contains_recursive(Object item)
		{

			if (this.data.equals(item))
			{
				return true;
			} else if (this.next.equals(null))
			{
				return false;
			}
			return this.next.contains_recursive(item);

		}

		
	}

	/**
	 * add an item to the beginning of the list
	 * 
	 * @param -
	 *            the data to be added to the beginning
	 */
	public void add_first(Object data)
	{
		

		// Initialize Node only incase of 1st element
		if (first == null)
		{
			first = new Node(data, first);
		} else
		{
			Node temp = new Node(data, first);
			
			temp.next = first;
			first = temp;
		}

		counter++;

	}

	/**
	 * add to the end of the list
	 * 
	 * @param the
	 *            data to be added to the end of the list
	 */
	@Override
	public void add_last(Object data)
	{
		// Initialize Node only incase of 1st element
		if (first == null)
		{
			first = new Node(data, first);
			counter++;
			return;
		}

		Node current = first;

		if (current != null) {

		while (current.next != null)
		{
			current = current.next;
		}

		}

		current.next = new Node(data, null);

		counter++;

	}

	/**
	 * add to the middle
	 * 
	 * @param after
	 *            - the number of elements that will come before the data
	 * @param data
	 *            - the data to be added after the index.
	 */

	@Override
	public void add_middle(int after, Object data)
	{
		if (counter == 0)
		{
			add_first(data);
			return;
		}

		int index = after;
		Node temp = new Node(data, null);
		Node current = first;

		if (current != null)
		{
			for (int i = 0; i < index && current.next != null; i++)
			{
				current = current.next;
			}
		}

		temp.next = current.next;

		current.next = temp;

		counter++;

	}

	/**
	 * clear all the values from the data
	 */
	@Override
	public void clear()
	{
		// if the list is empty, do nothing
		if (counter == 0)
		{
			return;
		}
		Node current = first;
		Node temp = current;
		
		while (current != null)
		{	
			
			temp = current;
			current.next = null;
			current = temp.next;
		}
		first.data = null;
		this.counter = 0;

	}

	/**
	 * does the list contain iterative
	 * @param - the item to check whether or not it is contained
	 * @return true if the item is contained, false otherwise
	 */
	@Override
	public boolean contains(Object item)
	{
		Node current = first;
		if (counter == 1)
		{
			return first.data.equals(item);
		}
		
		if (current != null)
		{

			while (current != null)
			{
				if (current.data.equals(item))
				{
					return true;
				}
				current = current.next;
			}

		}
		return false;
	}

	/**
	 * get the first node
	 * @return the value of the first node
	 */
	@Override
	public Object get_first() throws NoSuchElementException
	{
		// get the first node
		Node current = first;
		if (current == null)
		{
			throw new NoSuchElementException();
		}
		return current.data;

	}

	/**
	 * get the last node
	 * @param the value of the last node
	 */
	@Override
	public Object get_last() throws NoSuchElementException
	{

		Node<Object> current = first;
		if (current == null)
		{
			throw new NoSuchElementException();
		}
		while (current.next != null)
		{
			current = current.next;

		}
		return current.data;

	}

	/**
	 * remove the first node
	 */
	@Override
	public Object remove_first() throws NoSuchElementException
	{

		Node current = first;
		Node temp = first;
		if (current == null)
		{
			throw new NoSuchElementException();
		}

		first = current.next;
		counter--;
		return temp.data;

	}

	/**
	 * remove the last node
	 */
	@Override
	public Object remove_last() throws NoSuchElementException
	{
		// TODO Auto-generated method stub

		Node current = first;
		Node prev = current;
		Node last = prev;
		if (current == null)
		{
			throw new NoSuchElementException();
		}
		while (current.next != null)
		{
			prev = current;
			current = current.next;
			last = prev;

		}

		prev.next = null;
		counter--;
		return current.data;

	}

	/**
	 * return the size
	 */
	@Override
	public int size()
	{
		// TODO Auto-generated method stub
		return counter;
	}

	/**
	 * reverse the order of the linked list
	 */
	@Override
	public void reverse()
	{

		// initialize a value to hold the previous node
		Node prev = null;
		Node temp = first;
		while (temp != null)
		{
			Node next = temp.next;
			temp.next = prev;
			prev = temp;
			temp = next;
		}
		first = prev;

	}

	/**
	 * compute the size of the linked list (number of nodes), recursively
	 */
	@Override
	public int compute_size_recursive()
	{

		if (counter == 0)
		{
			return 0;
		}

		if (this.first.next == null)
		{
			return 1;
		}

		return this.first.length();
	}

	/**
	 * convert to an array list
	 */
	@Override
	public ArrayList to_ArrayList_post_recurse()
	{
		this.reverse();
		ArrayList<Object> linkedArray = new ArrayList<>();
		Node current = first;
		while (current != null)
		{
			linkedArray.add(current.data);
			current = current.next;
		}
		return linkedArray;

		
	}

	/**
	 * convert to an array list
	 * 
	 */
	@Override
	public ArrayList<Object> to_ArrayList()
	{
		ArrayList<Object> linkedArray = new ArrayList<>();
		Node current = first;
		while (current != null)
		{
			linkedArray.add(current.data);
			current = current.next;
		}
		return linkedArray;
	}

	/**
	 * does the linked list contain some object
	 */
	@Override
	public boolean contains_recursive(Object item)
	{
		return this.first.contains_recursive(item);

	}

	/**
	 * helper method
	 * 
	 * @return
	 */
	public static List_2420<Integer> new_list()
	{
		return new Linked_List_2420();
	}

	/**
	 *
	 * 
	 *
	 * Creates a string that describes the contents of the list, starting with
	 * the size in parentheses for example, a list of the numbers 0, 1, 2, 3
	 * would print as:
	 * 
	 * "(4) [0]--> [1]--> [2]--> [3]--> null"
	 *
	 * an empty list should simply return the string "empty"
	 *
	 * 
	 * @return a string representation of this chain of nodes
	 */
	public String toString()
	{
		String returnString = "(" + counter + ") ";
		Node current = first;
		while (current.next != null)
		{
			returnString += "[" + current.data + "]--> ";
			current = current.next;
		}
		returnString += "[" + current.data + "]--> ";
		returnString += "null";
		return returnString;
	}

}
