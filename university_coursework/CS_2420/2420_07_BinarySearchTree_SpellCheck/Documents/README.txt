---------------------------------------------------------------
AUTHOR INFORMATION
---------------------------------------------------------------

Date:				03/02/2017
Assignment:			Assignment 07 - Binary Search Tree
Partner 1 Name:		Landon Crowther
Partner 1 UID: 		u0926601
Partner 2 Name: 	Dezeray Kowalski
Partner 2 UID: 		u1013930
Class: 				CS 2420


---------------------------------------------------------------
Project Summary
---------------------------------------------------------------

The purpose of this assignment was to create our own version of
Java's treeSet, along with all of the main method calls that
treeSet has. 

A Binary Search Tree is a data structure where an object is 
placed in the "root" node. Any additional objects are placed
on their comparison to the "root". Any object that is "less than"
the root goes to the left, and anything "greater than" goes to the 
right. 

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
	-- Dezeray Kowalski
	
---------------------------------------------------------------
Design Decisions
---------------------------------------------------------------

camelCase styling

Special Helper Methods:

	We made a few special helper methods that we seemed to 
	be quite useful:
	
	1) childAnalysis
	
		this method is called on a node. It determines how many
		has. For one child, the method returns either -1 (child on left)
		or 1 (child on right). For two children, the method returns 2,
		and for 0 children the method returns 0.
		
		These numbers are then used in switch statements in the BST
		remove method. We felt that this was a cleaner and mroe simple
		approach compared to many nested if statements.
		
	2) findParent
		
		This method takes a Type item parameter. When called on a node,
		it returns the PARENT of the sub-node whos data is "item". The
		item parameter is guaranteed to be contained in the tree at this
		point. 
		
Removing when there are two nodes beneath the successor node:

	We made the to always call this.left.getRightmostNode() for 
	removing when the option was present. This was just a design
	decision. 
	

---------------------------------------------------------------
Problems Encountered & Solutions
---------------------------------------------------------------

REMOVE WAS THE MOST DIFFICULT METHOD BY FAR WITHOUT ANY DOUBT.

We kept over complicating things (like a lot). After talking
with multiple TAs, we finally were able to write out psuedocode
that worked for our implementation. Because we used special
helper methods & switch statements (mentioned above), the 
logic for remove was slightly different than the logic we discussed
in class. 

The most frustrating part of this assignment was the fact that we would
"fix" the remove() method, run the tests, and sadly find out that
we created even more problems. This happened way too many times to count.
Each time, however, we used the debugger to find where the problem was,
and was able to work through it for a successful implementation. 

