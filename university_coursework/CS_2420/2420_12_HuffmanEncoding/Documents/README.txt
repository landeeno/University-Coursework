README: 

---------------------------------------------------------------
AUTHOR INFORMATION
---------------------------------------------------------------

Date:				04/25/2017
Assignment:			Assignment 12 - Huffman Encoding
Partner 1 Name:		Brent Collins
Partner 1 UID: 		u0456586
Partner 2 Name: 	Landon Crowther
Partner 2 UID: 		u0926601
Class: 				CS 2420


---------------------------------------------------------------
Project Summary
---------------------------------------------------------------

	In this project we implemented Huffman coding. We encoded and 
	decoded text using Huffman trees to represent the data in a 
	compressed format. A Huffman tree is a frequency sorted tree based data 
	structure.
1) HuffmanTreeUsingWords
	HuffmanTreeUsingWords is a priority queue, specifically heap
	of Nodes, where the traversal of the tree will give the optimal
	prefix code for symbols in text


---------------------------------------------------------------
Late Work
---------------------------------------------------------------

n/a

---------------------------------------------------------------
Notes to TA
---------------------------------------------------------------
One partner, Landon, was working with a different partner, but
because of discrepancies in schedule and different levels of comfort
with the underlying material they both felt it was better to switch.


Both partners prefer camelCase styling over snake_case styling.
That being said, all of our code was written with camelCase, and
we left the original code as is. We also prefer the egyptian style
curly braces.



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
	-- Brent Collins
	-- Landon Crowther
	
---------------------------------------------------------------
Design Decisions
---------------------------------------------------------------

camelCase styling

Compute most common word implementation:

	Both partners felt a hash table was the best data structure
	to use for this method because of its speed. We then dumped
	the objects into an array for sorting, empirically this was
	the fastest way to implemnt that method.

---------------------------------------------------------------
Problems Encountered & Solutions
---------------------------------------------------------------

Reading back in the compressed data required some trial and error.
It seems as though the provided code expects the data header to be
in a certain format. Once we figured this out there was no problem,
but this caused a little confoundment.



