---------------------------------------------------------------
AUTHOR INFORMATION
---------------------------------------------------------------

Date:				03/02/2017
Assignment:			Assignment 09 - PacMan
Partner 1 Name:		Landon Crowther
Partner 1 UID: 		u0926601
Partner 2 Name: 	Nathan Robbins
Partner 2 UID: 		u0945015
Class: 				CS 2420


---------------------------------------------------------------
Project Summary
---------------------------------------------------------------

The purpose of this assignment was to explore the algorithm
for breadth first search, using a pacman like maze structure.

Breadth first search uses an adjaceny list, where each "node"
or object or verticy has a data structure that keeps track of
all the other "nodes" that are connected. In the search itself,
starting at node a, the algorithm looks at all the nodes that
a is connected to, and marks them as "seen". Then, the algorithm
checks the next layer of "nodes" (checks all the nodes connected
to the nodes that node a had a reference to). 

The cycle continues until the desired output or destination is
reached. The unique fact about BFS is that the resulting path
is the shortest path, which in most cases, is most efficient. 

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
	
---------------------------------------------------------------
Design Decisions
---------------------------------------------------------------

When reading a .txt file, we had each character represent a certain
values. Using these values, we made a matrix if integers that represented
walls and empty spaces. For each wall, the number -1 was used. 
For each non-wall, a node was created that had a unique ID, starting at 0.
If the character was 'S' or 'G', a special class variable was assigned 
to that node's ID. 

By doing this, when we were able to reference nodes by their ID, rather
than their location. 

---------------------------------------------------------------
Problems Encountered & Solutions
---------------------------------------------------------------

For the most part, there were little to no problems during this
assignment. We just took each problem one step at a time, and by
having the end goal in mind, were able to make smart design decisions
that enabled us to solve the problems in this assignment.

