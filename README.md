To compile:
javac -cp .:jopt-simple-5.0.2.jar PathFinderTester.java map/*.java pathFinder/*.java

To run on server, using example1 and all the optional files, apart from output file, specified:
java -cp .:jopt-simple-5.0.2.jar PathFinderTester -v -t terrain1.para -w waypoints1.para example1.para 

Task A and Task B:
	We choose grid to represent the graph. Every cell of the grid is filled by one coordinate. First, add an integer attribute to coordinate called distance and set it to MAX_VALUE and set the origin coordinate’s distance to 0. Then add a Coordinate type attribute called “parent” to coordinate and set default value to null. Then add all coordinates in the grid which are not impassable to a new arraylist called “unmarked”. While the unmarked array is not empty, repeating:
1.	Find a coordinate (tem) with smallest distance in the unmarked arraylist, then 
2.	Remove this coordinate (tem) from the unmarked arraylist. 
3.	Find neighbours of this coordinate (tem) and sort them by their terrain cost as an arraylist.
4.	For every neighbour of “tem” that exists in the unmarked array list, create “newdis” integer variable which value is the origin coordinate’s (tem) distance plus terrain cost.
5.	Compare newdis with the original distance of the current neighbour coordinate. If newdis smaller than original distance of the current neighbour:
(1)	Set coordinate’s distance to newdis.
(2)	Set this current coordinate’s parent to original coordinate (tem).
6.	Repeat above operations until the smallest coordinate is the detonation coordinate (path found) or the unmarked arraylist become empty (no path found).
Then Get the parent coordinate of destination coordinate, if parent coordinate is not the origin coordinate, add parent coordinate to the “path” arraylist and find parent coordinate of parent coordinate (back trace). Repeating these steps until the parent coordinate is the origin coordinate. Then Reverse arraylist “path”. Finally Return “path” as the shortest path found.

Task C:
Using methods create for Task A and Task B to find shortest paths from every origin cells to every destcells and create an arraylist to store these paths (the cost of every path will also be recorded). After all origin cells and destcells are scanned, sort this arraylist by the cost of the paths. Then return the first path in this arraylist.


Task D: Using methods create for Task A and Task B, from origin cell, scan all waypoint cells to find the nearest waypoint cell, the path (as “pa” in the coordinate) and distancewill be stored after find the cell. Then set this waypoint cell as start point to find the nearest waypoint cell of this waypoint cell and store the path and distance. Repeat until all waypoint cells are scanned. Then start from last waypoint cell to find a shortest path to the destcell. Finally, start from origin cell, connect all waypoint cells by the order they have been found and then coonect the last waypoint cell to the destcell, now the shortest path has been found.
