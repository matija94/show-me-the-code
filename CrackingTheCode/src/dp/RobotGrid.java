package dp;

import java.awt.Point;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class RobotGrid {

	public static final int GO = 1;
	public static final int STOP = 0;
	
	public static LinkedList<Point> path(int[][] grid) {
		Set<Point> failed = new HashSet<>();
		LinkedList<Point> visited = new LinkedList<>();
		path(grid,visited,0,0,failed);
		return visited;
	}

	private static void path(int[][] grid, LinkedList<Point> path, int row, int col, Set<Point> failed) {
		if (row == grid.length || col == grid[row].length) {
			return; // out of bound
		}
		Point p = new Point(row, col);
		if(failed.contains(p)) {
			return;
		}
		path.add(p);
		if (grid[row][col] == STOP) {
			failed.add(path.removeLast());
			return;
		}
		path(grid,path,row,col+1,failed);
		path(grid,path,row+1,col,failed);
		if(row < grid.length-1 && grid[row+1][col] == STOP) {
			path.removeLast();
		}
	}
	
	public static void main(String[] args) {
		int r = 1000;
		int c = 1000;
		int grid[][] = new int[r][c];
		for(int i=0;i<r;i++){
			for(int j=0;j<c;j++){
				if (i % 2 == 1 && j>0) {
					grid[i][j]=STOP;
				}
				else {
					grid[i][j]=GO;
				}
			}
		}
		System.out.println(path(grid));
	}
}
