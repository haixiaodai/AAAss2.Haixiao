import java.util.ArrayList;
import java.util.List;

import map.Coordinate;
import map.PathMap;
import pathFinder.DijkstraPathFinder;

public class TestCase {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		test1();
	}
	
	public static void test1() {
		PathMap map=new PathMap();
		Coordinate cells[][] = new Coordinate[7][7];
    	for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                Coordinate coord = new Coordinate(i, j);
                cells[i][j] = coord;
            }
    	}
    	map.cells=cells;
    	List<Coordinate> originCells=new ArrayList<Coordinate>();
    	List<Coordinate> desCells=new ArrayList<Coordinate>();
    	List<Coordinate> waypointCells=new ArrayList<Coordinate>();
    	originCells.add(new Coordinate(2,0));
    	desCells.add(new Coordinate(0,3));
    	map.originCells=originCells;
    	map.destCells=desCells;
    	map.cells[2][2].setImpassable(true);
    	map.cells[3][2].setImpassable(true);
    	map.cells[4][2].setImpassable(true);
    	map.cells[1][2].setImpassable(true);
    	map.cells[1][3].setImpassable(true);
    	map.cells[2][3].setImpassable(true);
    	map.cells[3][3].setImpassable(true);
    	map.cells[2][4].setImpassable(true);
    	map.cells[1][1].setTerrainCost(12);
    	map.cells[1][2].setTerrainCost(10);
    	map.cells[1][1].setTerrainCost(9);
    	map.cells[0][0].setTerrainCost(6);
    	map.cells[0][2].setTerrainCost(8);
    	map.sizeC=7;
    	map.sizeR=7;
    	waypointCells.add(new Coordinate(6,1));
    	waypointCells.add(new Coordinate(4,6));
    	map.waypointCells=waypointCells;
    	DijkstraPathFinder d=new DijkstraPathFinder(map);
    	d.findPath();
	}

}
