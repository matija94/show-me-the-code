package arraysAndStrings;

import java.util.ArrayList;

public class ZeroMatrix {

	
	public static void zeroMatMemory(int mat[][]) {
		ArrayList<Integer> zeroRows = new ArrayList<>();
		ArrayList<Integer> zeroCols = new ArrayList<>();
		
		for (int i=0; i<mat.length; i++) {
			for(int j=0;j<mat[i].length;j++) {
				if (mat[i][j] == 0) {
					zeroRows.add(i);
					zeroCols.add(j);
					break;
				}
			}
		}
		
		zeroRows.forEach(row -> {
			for(int i=0; i<mat[0].length; i++) {
				mat[row][i] = 0;
			}
		});
		zeroCols.forEach(col-> {
			for(int i=0; i<mat.length; i++) {
				mat[i][col] = 0;
			}
		});
	}

	public static void main(String[] args) {
		int mat[][] = {
				{1,2,3},
				{4,5,0},
				{0,8,9}
				
		};
		zeroMatMemory(mat);
		
		System.out.println("Done");
	}
}
