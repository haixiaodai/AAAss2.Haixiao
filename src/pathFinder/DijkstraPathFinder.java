package pathFinder;

import map.Coordinate;
import map.PathMap;
import java.util.*;


public class DijkstraPathFinder implements PathFinder
{
    // TODO: You might need to implement some attributes
	PathMap map;


    public DijkstraPathFinder(PathMap map) {
        // TODO :Implement
    	this.map=map;

    } // end of DijkstraPathFinder()
    
    //This method is to support task A and B
    @Override
    public List<Coordinate> findPath() {
    	List<Coordinate> path = new ArrayList<Coordinate>();
    	List<Coordinate> unmarked = new ArrayList<Coordinate>();
    	if(map.waypointCells==null) {
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
    		tem=findsmallest(unmarked);
    		unmarked.remove(tem);
    		if(tem.equals(map.cells[map.destCells.get(0).getRow()][map.destCells.get(0).getColumn()])) {
    			break;
    		}
    		else {
    			for(Coordinate c2:findneighbours(tem,map)) {
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

    	else {
    		Coordinate small=map.cells[map.originCells.get(0).getRow()][map.originCells.get(0).getColumn()];
    		while(map.waypointCells.size()>0) {
    			small=findsmallestpoint(map.cells[small.getRow()][small.getColumn()]);
    			path.addAll(small.getPa());
    			map.waypointCells.remove(small);
    		}
    		path.addAll(lastd(small));
    		return path;
    	}
    }
    
    //This method is to support task A and B, find a unmarked coordinate that have the smallest distance. 
    //If there a more than one coordinates that have the same distance, return the first one in the original arraylist.
    public Coordinate findsmallest(List<Coordinate> unmarked) {
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
    public List<Coordinate> findneighbours(Coordinate c, PathMap map){
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

    public Coordinate findsmallestpoint(Coordinate ori) {
    	for(int a=0;a<map.waypointCells.size();a++) {
    		PathMap newmap=new PathMap();
    		newmap.cells=new Coordinate[map.sizeR][map.sizeC];
    		newmap.sizeR=map.sizeR;
    		newmap.sizeC=map.sizeC;
    		for(int q=0;q<map.sizeR;q++) {
    			for(int w=0;w<map.sizeC;w++) {
    				Coordinate co=new Coordinate(q,w);
    				co.setImpassable(map.cells[q][w].getImpassable());
    				co.setTerrainCost(map.cells[q][w].getTerrainCost());
    				newmap.cells[q][w]=co;
    			}
    		}
    		newmap.originCells=new ArrayList<Coordinate>();
    		newmap.originCells.add(new Coordinate(ori.getRow(),ori.getColumn()));
    		newmap.destCells=new ArrayList<Coordinate>();
    		newmap.destCells.add(newmap.cells[map.waypointCells.get(a).getRow()][map.waypointCells.get(a).getColumn()]);
    		List<Coordinate> newpath = insideloop(newmap);
        	newmap.cells[newmap.originCells.get(0).getRow()][newmap.originCells.get(0).getColumn()].setPa(newpath);
        	map.waypointCells.get(a).setPa(newpath);
    	}
    	Collections.sort(map.waypointCells, new Comparator<Coordinate>() {

			@Override
			public int compare(Coordinate o1, Coordinate o2) {
				// TODO Auto-generated method stub
				return o1.getDistance()-o2.getDistance();
			}
			
		});
    	for(int v=1;v<map.waypointCells.size();v++) {
    		map.waypointCells.get(v).setDistance(Integer.MAX_VALUE);
    	}
    	return map.waypointCells.get(0);
    }
    
    public List<Coordinate> lastd(Coordinate ori) {
    		PathMap newmap=new PathMap();
    		newmap.cells=new Coordinate[map.sizeR][map.sizeC];
    		newmap.sizeR=map.sizeR;
    		newmap.sizeC=map.sizeC;
    		for(int q=0;q<map.sizeR;q++) {
    			for(int w=0;w<map.sizeC;w++) {
    				Coordinate co=new Coordinate(q,w);
    				co.setImpassable(map.cells[q][w].getImpassable());
    				co.setTerrainCost(map.cells[q][w].getTerrainCost());
    				newmap.cells[q][w]=co;
    			}
    		}
    		newmap.originCells=new ArrayList<Coordinate>();
    		newmap.originCells.add(new Coordinate(ori.getRow(),ori.getColumn()));
    		newmap.destCells=new ArrayList<Coordinate>();
    		newmap.destCells.add(newmap.cells[map.destCells.get(0).getRow()][map.destCells.get(0).getColumn()]);
    		List<Coordinate> newpath=insideloop(newmap);
    		return newpath;
    }
    
    public List<Coordinate> insideloop(PathMap pa) {
    	List<Coordinate> path = new ArrayList<Coordinate>();
    	List<Coordinate> unmarked = new ArrayList<Coordinate>();
    	if(pa.waypointCells==null) {
    	Coordinate tem=null;
    	pa.cells[pa.originCells.get(0).getRow()][pa.originCells.get(0).getColumn()].setDistance(0);
    	for(int i=0;i<pa.sizeR;i++) {
    		for(int k=0;k<pa.sizeC;k++) {
    			if(!pa.cells[i][k].getImpassable()) {
    			unmarked.add(pa.cells[i][k]);
    			}
    		}
    	}
    	while(!unmarked.isEmpty()) {
    		tem=findsmallest(unmarked);
    		unmarked.remove(tem);
    		if(tem.equals(pa.cells[pa.destCells.get(0).getRow()][pa.destCells.get(0).getColumn()])) {
    			break;
    		}
    		else {
    			for(Coordinate c2:findneighbours(tem,pa)) {
    				if(unmarked.contains(c2)) {
    					int newdis=tem.getDistance()+c2.getTerrainCost();
    					if(newdis<c2.getDistance()) {
    						c2.setDistance(newdis);
    						pa.cells[c2.getRow()][c2.getColumn()].setDistance(newdis);
    						c2.setParent(tem);
    						pa.cells[c2.getRow()][c2.getColumn()].setParent(tem);
    						pa.cells[c2.getRow()][c2.getColumn()]=c2;
    					}
    				}
    			}
    		}
    	}
    	Coordinate tem2=pa.cells[pa.destCells.get(0).getRow()][pa.destCells.get(0).getColumn()];
    	while(tem2!=null&&tem2!=pa.cells[pa.originCells.get(0).getRow()][pa.originCells.get(0).getColumn()]) {
    		path.add(tem2);
    		tem2=pa.cells[tem2.getParent().getRow()][tem2.getParent().getColumn()];
    	}
    	path.add(pa.originCells.get(0));
    	Collections.reverse(path);
    }

    	return path;
} 
}// end of class DijsktraPathFinder
