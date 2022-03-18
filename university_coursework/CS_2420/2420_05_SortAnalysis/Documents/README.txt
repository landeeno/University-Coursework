README: 

---------------------------------------------------------------
AUTHOR INFORMATION
---------------------------------------------------------------

Date:				02/16/2017
Assignment:			Assignment 05 - Sorting Analysis
Partner 1 Name:		Landon Crowther
Partner 1 UID: 		u0926601
Partner 2 Name: 	Parker Stewart
Partner 2 UID: 		u1001540
Class: 				CS 2420


---------------------------------------------------------------
Project Summary
---------------------------------------------------------------

	The purpose of this project was to write and analyze the
effectiveness of different sorting algorithms. These sorts include
shell sort, quick sort, insertion sort, merge sort, and Java's builtin
sort. 

	Our task was to write the merge, quick, and insertion sorts using 
generics. Guidelines were in place as for starting points, 
but we had to fine-tune the method. After all of the methods
were working properly, we had to run a timing analysis on each of the 
methods to see how they perforemd. There were many factors involved
in the timing analysis. 

	Some of the factors included variety in pivot selection for quick sort
and varying the insertion cutoff for merge and quick sorts. This cutoff is 
where the method should stop recursively sorting and switch to insertion sort.

---------------------------------------------------------------
Late Work
---------------------------------------------------------------

n/a

---------------------------------------------------------------
Notes to TA
---------------------------------------------------------------

One partner likes to use camelCase style, and the other likes to use
underscore. We did our best to ensure that the final code was in all
underscore typset, but there may be a few camelCase names that we 
happened to miss when merging code. 

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
	-- Parker Stewart
	
---------------------------------------------------------------
Design Decisions
---------------------------------------------------------------

	For the most part, we followed the template structure in the queue.
The only time we made an explicit design decision was in the 
Quick_Sort_Inplace_M3 class. The choose_pivot method for this 
class asked to place the lowest Type at index [start], highest type
at index [end], and the median at index [end-1]. In all other quick sort 
methods, the pivot (median in this case), was at the end of the array.

To adjust to this, we simply overrided the sort method in the M3 method. 
The only difference in the overriden method is that right before we
started the actual sort, we simply called the swap method on 
indices [end] and [end - 1]. This would ensure that the pivot was at the
end of the array.

---------------------------------------------------------------
Problems Encountered & Solutions
---------------------------------------------------------------

Merge Sort: 
	The biggest problem encountered with merge sort was getting an
off by one error in the combine method. It took a lot of debugging
to finally ensure that the bounds for the separate arrays weren't 
overlapping. In combine method, when merging the two virtual arrays,
we had to ensure that all elements were being accounted for, and only once.

Insertion Sort: 
	We originally just reused the psuedocode from the last assignment, however,
we ran into a major error that took quite a while to fix. In while
loop in the insertion sort, there was a hardcoded ( != 0 ) to make sure that the
loop stopped at the beginnning of the array. To fix this, we had to modifyt
the code so that instead of being not equal to 0, it was != to start. 



