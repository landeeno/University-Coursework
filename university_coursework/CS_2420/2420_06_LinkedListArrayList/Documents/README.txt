README: 

---------------------------------------------------------------
AUTHOR INFORMATION
---------------------------------------------------------------

Date:				02/23/2017
Assignment:			Assignment 06 - Linked List
Partner 1 Name:		Landon Crowther
Partner 1 UID: 		u0926601
Partner 2 Name: 	Brent Collins
Partner 2 UID: 		u0456586
Class: 				CS 2420


---------------------------------------------------------------
Project Summary
---------------------------------------------------------------

	The purpose of this project was to create our own version
of Linked Lists, using a List interface. We created two versions
of this implementation:

1) Linked Lists
	Linked Lists are a "chain" of Nodes, where each Node contains
	two pieces of information: the data it contains, and a reference
	to the next link (Node) in the chain
	
2) Array List
	Similar to Java's ArrayList, we made a list implementation
	of data, but this data was stored as an array. In an effort
	to minimize complexity and runtime, we combatted java's 
	indexing by making this array (which we call backingArray) 
	a continuous loop with a start and end index, rather than
	alwasy starting at 0.

---------------------------------------------------------------
Late Work
---------------------------------------------------------------

n/a

---------------------------------------------------------------
Notes to TA
---------------------------------------------------------------

Both partners prefer camelCase styling over snake_case styling.
That being said, all of our code was written with camelCase, and
we left the original code as is.  



---------------------------------------------------------------
Pledge
---------------------------------------------------------------

I pledge that the work done here was my own and that I have 
learned how to write this program (such that I could throw 
it out and restart and finish it in a timely manner).  
I am not turning in any work that I cannot understand, 
describe, or recreate.  Any sources (e.g., web sites) other
than the lecture that I used to help write the code are cited 
in my work.  When working with a partner, I have contributed
an equal share and understand all the submitted work.  
Further, I have helped write all the code assigned as 
pair-programming and reviewed all code that was written separately.
	-- Landon Crowther
	-- Brent Collins
	
---------------------------------------------------------------
Design Decisions
---------------------------------------------------------------

camelCase styling

ArrayList implementation:

	for the post_recursive method, we made a copy of the 
	original Array_List_2420, called the reverse method 
	on the copy, and then called the toArrayList() method
	on this.
	

---------------------------------------------------------------
Problems Encountered & Solutions
---------------------------------------------------------------

Array List gave us a lot of tricky boundary issues. It would have
been effective to find a way to bound things rather than having
to play logic games. 

Add Middle was very vague. We thought it needed to be implemented one way,
and it turned out that the "after" parameter didn't quite make sense.
after 2 seemed like it should be after 2 elements (spot 3) , but
actually at spot 4 (after 3 elements).

The only reason we came to this conclusion was because the Javadoc
on the List_2420 class, where it said "after 0" would result in
placing it after first element. 

