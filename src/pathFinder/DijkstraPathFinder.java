package pathFinder;

import map.Coordinate;
import map.PathMap;
import java.util.*;

/**
 * @author Haixiao Dai
 */

public class DijkstraPathFinder implements PathFinder
{
    // TODO: You might need to implement some attributes
	PathMap map;


    public DijkstraPathFinder(PathMap map) {
        // TODO :Implement
    	this.map=map;

    } // end of DijkstraPathFinder()
    
    @Override
    public List<Coordinate> findPath() {
    	List<Coordinate> path = new ArrayList<Coordinate>();
    	try {
    	// If there is only one origin cell and one destcell， go to method created for task A
    	if(map.originCells.size()==1&&map.destCells.size()==1) {
    		return findPathAB();
    	}
    	//If there are more than one origin cell or descell
    	else {
    	List<List<Coordinate>> pathlist = new ArrayList<List<Coordinate>>();
    	for(int i=0;i<map.originCells.size();i++) {
    		for(int j=0;j<map.destCells.size();j++) {
    			path=findPath(map.originCells.get(i),map.destCells.get(j));
    			pathlist.add(path);
    		}
    	}
    	for(int v=0;v<pathlist.size();v++) {
    		pathlist.get(v).get(pathlist.get(v).size()-1).setDistance(caculatedis(pathlist.get(v)));
    	}
    	Collections.sort(pathlist, new Comparator<List<Coordinate>>(){

			@Override
			public int compare(List<Coordinate> o1, List<Coordinate> o2) {
				// TODO Auto-generated method stub
				return o1.get(o1.size()-1).getDistance()-o2.get(o2.size()-1).getDistance();
			}
    		
    	});
    	return pathlist.get(0);
    	}
    	}
    	catch (Exception e) {
    		return path;
    	}
    }
    
    //This method is to support task A and B, when there is only one origin cell and one destcell
    public List<Coordinate> findPathAB() {
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
    			small=findsmallestpoint(map,map.cells[small.getRow()][small.getColumn()]);
    			path.addAll(small.getPa());
    			map.waypointCells.remove(small);
    		}
    		path.addAll(lastd(small,map.cells[map.destCells.get(0).getRow()][map.destCells.get(0).getColumn()]));
    		return path;
    	}
    }
    
    //This method is to support task C, when there are more than one origin cell or descell
    public List<Coordinate> findPath(Coordinate ori,Coordinate des) {
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
		newmap.waypointCells=new ArrayList<Coordinate>();
		for(int i=0;i<map.waypointCells.size();i++) {
			newmap.waypointCells.add(new Coordinate(map.waypointCells.get(i).getRow(),map.waypointCells.get(i).getColumn()));
		}
    	List<Coordinate> path = new ArrayList<Coordinate>();
    	List<Coordinate> unmarked = new ArrayList<Coordinate>();
    	if(newmap.waypointCells==null) {
    	Coordinate tem=null;
    	newmap.cells[ori.getRow()][ori.getColumn()].setDistance(0);
    	for(int i=0;i<newmap.sizeR;i++) {
    		for(int k=0;k<newmap.sizeC;k++) {
    			if(!newmap.cells[i][k].getImpassable()) {
    			unmarked.add(newmap.cells[i][k]);
    			}
    		}
    	}
    	while(!unmarked.isEmpty()) {
    		tem=findsmallest(unmarked);
    		unmarked.remove(tem);
    		if(tem.equals(newmap.cells[des.getRow()][des.getColumn()])) {
    			break;
    		}
    		else {
    			for(Coordinate c2:findneighbours(tem,newmap)) {
    				if(unmarked.contains(c2)) {
    					int newdis=tem.getDistance()+c2.getTerrainCost();
    					if(newdis<c2.getDistance()) {
    						c2.setDistance(newdis);
    						newmap.cells[c2.getRow()][c2.getColumn()].setDistance(newdis);
    						c2.setParent(tem);
    						newmap.cells[c2.getRow()][c2.getColumn()].setParent(tem);
    						newmap.cells[c2.getRow()][c2.getColumn()]=c2;
    					}
    				}
    			}
    		}
    	}
    	Coordinate tem2=newmap.cells[des.getRow()][des.getColumn()];
    	while(tem2!=null&&tem2!=newmap.cells[ori.getRow()][ori.getColumn()]) {
    		path.add(tem2);
    		tem2=newmap.cells[tem2.getParent().getRow()][tem2.getParent().getColumn()];
    	}
    	path.add(newmap.cells[ori.getRow()][ori.getColumn()]);
    	Collections.reverse(path);
    	return path;
    	}

    	else {
    		Coordinate small=newmap.cells[ori.getRow()][ori.getColumn()];
    		while(newmap.waypointCells.size()>0) {
    			small=findsmallestpoint(newmap,newmap.cells[small.getRow()][small.getColumn()]);
    			path.addAll(small.getPa());
    			newmap.waypointCells.remove(small);
    		}
    		path.addAll(lastd(small,newmap.cells[des.getRow()][des.getColumn()]));
    		return path;
    	}
    }
    

    
    //This method is to support Task C and D, when the shortest path includes backtrack, update distance of path after backtrack
    public int caculatedis(List<Coordinate> f) {
    	int dis=0;
    	for(int i=0;i<f.size();i++) {
    		dis=dis+f.get(i).getDistance();
    	}
    	return dis;
    }
    
    //This method is to support all tasks, find a unmarked coordinate that have the smallest distance. 
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
    
    //This method is to support task D, given an map and origin cell, find the nearest waypoint cell of the origin cell
    public Coordinate findsmallestpoint(PathMap map,Coordinate ori) {
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
    		newmap.cells[ori.getRow()][ori.getColumn()].setDistance(0);
    		newmap.destCells=new ArrayList<Coordinate>();
    		newmap.destCells.add(newmap.cells[map.waypointCells.get(a).getRow()][map.waypointCells.get(a).getColumn()]);
    		List<Coordinate> newpath = insideloop(newmap);
//        	newmap.cells[newmap.originCells.get(0).getRow()][newmap.originCells.get(0).getColumn()].setPa(newpath);
        	map.waypointCells.get(a).setPa(newpath);
    	}
    	Collections.sort(map.waypointCells, new Comparator<Coordinate>() {

			@Override
			public int compare(Coordinate o1, Coordinate o2) {
				// TODO Auto-generated method stub
				return o1.getPa().get(o1.getPa().size()-1).getDistance()-o2.getPa().get(o2.getPa().size()-1).getDistance();
			}
			
		});
    	for(int v=1;v<map.waypointCells.size();v++) {
    		map.waypointCells.get(v).setDistance(Integer.MAX_VALUE);
    	}
    	return map.waypointCells.get(0);
    }
    
    //This method is to support Task D, find a path from last waypoint cell to the dest cell
    public List<Coordinate> lastd(Coordinate ori,Coordinate des) {
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
    		newmap.destCells.add(newmap.cells[des.getRow()][des.getColumn()]);
    		List<Coordinate> newpath=insideloop(newmap);
    		return newpath;
    }
    
    //This method is to support task C and D, find the smallest path for a given map
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
    	path.add(pa.cells[pa.originCells.get(0).getRow()][pa.originCells.get(0).getColumn()]);
    	Collections.reverse(path);
    }

    	return path;
} 
}// end of class DijsktraPathFinder
