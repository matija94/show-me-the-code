package easy;

public class Hex {

	/*
	 * Draws a picture of a 4 x 4 Hex game in progress. The board is a 4 x 4
	 * collection of hexagons packed together, with 4 hexagons in each vertical
	 * column, and 4 hexagons in each diagonal from upper left to lower right. Two
	 * players play against each other. One of the players ('h') tries to make a
	 * horizontal chain of adjacent hexagons stretching between the left and right
	 * of the board. The other player ('v') tries to make a vertical chain of
	 * adjacent hexagons stretching from the bottom to the top of the board.
	 * 
	 * We can refer to any position on the board by a pair of coordinates giving the
	 * diagonal distance and vertical distance from the upper left hexagon. Using
	 * these coordinates, the two hexagons marked 'h' are located at (0,0) and at
	 * (2,1).
	 * 
	 * Given the size of the board and a list of all the marked hexagons, we want
	 * software that can draw the board using characters as shown above. Create a
	 * class Hex that contains a method picture that is given n (the vertical and
	 * diagonal size of the board) and marks (a list of all the marked hexagons) and
	 * that returns a picture of the board in the format shown above. The return
	 * will be a that if printed one per line in order would produce the picture.
	 * Each element of the return should have no trailing spaces, and at least one
	 * of the elements should have no leading spaces.
	 * 
	 * marks will be a in which each element will consist of exactly 3 characters:
	 * two digits giving the diagonal and then the vertical coordinate of a hexagon,
	 * followed by either 'v' or 'h', the marking of that hexagon.
	 */
	public String[] picture(int n, String[] marks) {
		int height = 3 * n;
		int width = 2 * n + 1;
		char[][] mat = new char[height][width];
		char oddRowEvenCh = '/', oddRowOddCh = ' ', evenRowOddCh = '_', evenRowEvenCh = '\\';

		int columnLimit = 1;
		int blankSpacesScale = 1;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width && j <= columnLimit; j++) {
				if (i > 0 && i < height - (n - 1)) { // starts without blank
					if (i % 2 == 0) { // starts with '\'
						if (j % 2 == 0) {
							mat[i][j] = evenRowEvenCh;
							evenRowEvenCh = evenRowEvenCh == '\\' ? '/' : '\\';
						} else {
							mat[i][j] = evenRowOddCh;
							evenRowOddCh = evenRowOddCh == '_' ? ' ' : '_';
						}
					} else { // starts with '/'
						if (j % 2 == 0) {
							mat[i][j] = oddRowEvenCh;
							oddRowEvenCh = oddRowEvenCh == '/' ? '\\' : '/';
						} else {
							mat[i][j] = oddRowOddCh;
							oddRowOddCh = oddRowOddCh == ' ' ? '_' : ' ';
						}
					}
				} else if (i > 0) { // starts with blank
					if (j < 2 * blankSpacesScale) {
						mat[i][j] = ' ';
					} else if (j % 2 == 0) {
						mat[i][j] = evenRowEvenCh;
						evenRowEvenCh = evenRowEvenCh == '\\' ? '/' : '\\';
					} else {
						mat[i][j] = evenRowOddCh;
						evenRowOddCh = evenRowOddCh == '_' ? ' ' : '_';
					}
				} else {
					mat[i][j] = j % 2 == 0 ? ' ' : '_';
				}
			}
			oddRowEvenCh = '/';
			oddRowOddCh = ' ';
			evenRowOddCh = '_';
			evenRowEvenCh = '\\';
			columnLimit += 2;
			if (i >= (height - (n - 1))) {
				blankSpacesScale++;
			}
		}

		markHex(mat, marks);
		String pic[] = new String[height];
		for (int i = 0; i < height; i++) {
			StringBuilder sb = new StringBuilder();
			for (int j = 0; j < mat[i].length; j++) {
				if (((int) mat[i][j]) > 0) {
					sb.append(mat[i][j]);
				}
			}
			pic[i] = sb.toString();
		}
		return pic;
	}

	private void markHex(char mat[][], String marks[]) {
		for (String mark : marks) {
			int col = 2 * (mark.charAt(0) - '0') + 1;
			int row = 2 * (mark.charAt(1) - '0') + 1;
			char marking = mark.charAt(2);
			row = row + (mark.charAt(0) - '0');
			mat[row][col] = marking;
		}
	}

	public static void main(String[] args) {
		Hex h = new Hex();
		String pic[] = h.picture(10, new String[] { "12h", "02v", "10h", "11h", "20v", "44v", "98h" });
		for (String p : pic) {
			System.out.println(p);
		}
	}
}
