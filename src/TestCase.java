import java.util.ArrayList;
import java.util.List;

import map.Coordinate;
import map.PathMap;
import pathFinder.DijkstraPathFinder;

public class TestCase {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		test1();
		test2();
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
    	DijkstraPathFinder d=new DijkstraPathFinder(map);
    	d.findPath();
	}
	
	public static void test2() {
		PathMap map=new PathMap();
		Coordinate cells[][] = new Coordinate[10][10];
    	for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Coordinate coord = new Coordinate(i, j);
                cells[i][j] = coord;
            }
    	}
    	map.cells=cells;
    	map.waypointCells=new ArrayList<Coordinate>();
    	map.originCells=new ArrayList<Coordinate>();
    	map.destCells=new ArrayList<Coordinate>();
    	map.originCells.add(new Coordinate(1,1));
    	map.originCells.add(new Coordinate(2,4));
    	map.originCells.add(new Coordinate(9,9));
    	map.originCells.add(new Coordinate(5,2));
    	map.destCells.add(new Coordinate(5,8));
    	map.destCells.add(new Coordinate(0,6));
    	map.destCells.add(new Coordinate(7,5));
    	map.cells[5][5].setImpassable(true);
    	map.cells[5][4].setImpassable(true);
    	map.cells[4][4].setImpassable(true);
    	map.cells[4][3].setImpassable(true);
    	map.cells[3][5].setImpassable(true);
    	map.cells[3][6].setImpassable(true);
    	map.cells[3][7].setImpassable(true);
    	map.cells[3][9].setImpassable(true);
    	map.cells[8][0].setImpassable(true);
    	map.cells[7][0].setImpassable(true);
    	map.cells[8][1].setImpassable(true);
    	map.cells[7][1].setImpassable(true);
    	map.cells[3][8].setTerrainCost(18);
    	map.cells[4][9].setTerrainCost(2);
    	map.cells[4][8].setTerrainCost(2);
    	map.cells[4][7].setTerrainCost(2);
    	map.cells[5][7].setTerrainCost(2);
    	map.cells[5][9].setTerrainCost(2);
    	map.cells[6][7].setTerrainCost(2);
    	map.cells[6][8].setTerrainCost(2);
    	map.cells[6][9].setTerrainCost(2);
    	map.cells[1][8].setTerrainCost(3);
    	map.cells[1][7].setTerrainCost(3);
    	map.waypointCells.add(map.cells[5][0]);
    	map.waypointCells.add(map.cells[3][3]);
    	map.sizeC=10;
    	map.sizeR=10;
    	DijkstraPathFinder d=new DijkstraPathFinder(map);
    	System.out.println(d.findPath());
	}

}
