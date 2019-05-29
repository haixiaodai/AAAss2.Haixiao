package pathFinder;

import map.Coordinate;
import map.PathMap;
import java.util.*;



public class DijkstraPathFinder implements PathFinder
{
    // TODO: You might need to implement some attributes
	PathMap map;
	List<Coordinate> path = new ArrayList<Coordinate>();
	List<Coordinate> unmarked = new ArrayList<Coordinate>();

    public DijkstraPathFinder(PathMap map) {
        // TODO :Implement
    	this.map=map;

    } // end of DijkstraPathFinder()
    
    //This method is to support task A and B
    @Override
    public List<Coordinate> findPath() {
    	Coordinate tem=null;
    	map.cells[map.originCells.get(0).getRow()][map.originCells.get(0).getColumn()].setDistance(0);
    	for(int i=0;i<map.sizeR;i++) {
    		for(int k=0;k<map.sizeC;k++) {
    			if(!map.cells[i][k].getImpassable()) {
    			unmarked.add(map.cells[i][k]);
    			}
    		}
    	}
    	while(!unmarked.isEmpty()) {
    		tem=findsmallest();
    		unmarked.remove(tem);
    		if(tem.equals(map.cells[map.destCells.get(0).getRow()][map.destCells.get(0).getColumn()])) {
    			break;
    		}
    		else {
    			for(Coordinate c2:findneighbours(tem)) {
    				if(unmarked.contains(c2)) {
    					int newdis=tem.getDistance()+c2.getTerrainCost();
    					if(newdis<c2.getDistance()) {
    						c2.setDistance(newdis);
    						map.cells[c2.getRow()][c2.getColumn()].setDistance(newdis);
    						c2.setParent(tem);
    						map.cells[c2.getRow()][c2.getColumn()].setParent(tem);
    						map.cells[c2.getRow()][c2.getColumn()]=c2;
    					}
    				}
    			}
    		}
    	}
    	Coordinate tem2=map.cells[map.destCells.get(0).getRow()][map.destCells.get(0).getColumn()];
    	System.out.println(tem2.getParent());
    	while(tem2!=null&&tem2!=map.cells[map.originCells.get(0).getRow()][map.originCells.get(0).getColumn()]) {
    		path.add(tem2);
    		tem2=map.cells[tem2.getParent().getRow()][tem2.getParent().getColumn()];
    	}
    	path.add(map.originCells.get(0));
    	Collections.reverse(path);
    	return path;
    }
    
    //This method is to support task A and B, find a unmarked coordinate that have the smallest distance. 
    //If there a more than one coordinates that have the same distance, return the first one in the original arraylist.
    public Coordinate findsmallest() {
    	Coordinate smallest=unmarked.get(0);
    	for(int i=0;i<unmarked.size();i++) {
    		if(unmarked.get(i).getDistance()<smallest.getDistance()) {
    			smallest=unmarked.get(i);
    		}
    	}
    	return smallest;
    }


    @Override
    public int coordinatesExplored() {
        // TODO: Implement (optional)

        // placeholder
        return 0;
    } // end of cellsExplored()


    
    //This method is to support Task B, given a coordinate, sorting it's neighbors by their terraincost, and return as a sorted ArrayList
    public List<Coordinate> findneighbours(Coordinate c){
    	List<Coordinate> neighbours = new ArrayList<Coordinate>();
    	try {
    	neighbours.add(map.cells[c.getRow()][c.getColumn()+1]);
    	}
    	catch(ArrayIndexOutOfBoundsException | NullPointerException e) {
    		
    	}
    	try {
    	neighbours.add(map.cells[c.getRow()][c.getColumn()-1]);
    	}
    	catch(ArrayIndexOutOfBoundsException | NullPointerException e) {
    		
    	}
    	try {
    	neighbours.add(map.cells[c.getRow()+1][c.getColumn()]);
    	}
    	catch(ArrayIndexOutOfBoundsException | NullPointerException e) {
    		
    	}
    	try {
    	neighbours.add(map.cells[c.getRow()-1][c.getColumn()]);
    	}
    	catch(ArrayIndexOutOfBoundsException | NullPointerException e) {
    		
    	}
    	neighbours.sort(new Comparator<Coordinate>() {
    		@Override
			public int compare(Coordinate o1, Coordinate o2) {
    			return o1.getTerrainCost()-o2.getTerrainCost();
    		}
    	});
    	return neighbours;
    }


} // end of class DijsktraPathFinder
