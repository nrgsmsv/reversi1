
import java.util.Arrays;

public class Board {
	private int size;
	private char[][] matrix;

	public Board(int size){
		this.size = size;
		this.matrix = new char[size][size];
		this.init_matrix();
	}

	public void init_matrix(){
		for (int i = 0; i < size; i++) {
			for(int j = 0; j< size; j++)
				this.matrix[i][j] = ' ';
		}
	}

	public void set_matrix(int row, int col, char symbol) {
		if (symbol != 'F' && symbol != 'S') {
			System.out.println("Unrecognized symbol.");
			return;
		} else {
			this.matrix[row][col] = symbol;
		}
	}

	public void print_matrix() {
		//print first row
		System.out.print(" |");
		for (int i = 0; i < size; i++) {
			System.out.print(" " + (i + 1) + " |");
		}
		System.out.println();
		char[] c1 = new char[4 * size + 2];
		Arrays.fill(c1, '-');
		System.out.println(new String(c1));
		for ( int i = 0; i < size; i++) {
			System.out.print( (i+1)+ "|");
			// run all columns in the row
			for ( int j = 0; j < size; j++) {
				System.out.print(" "+ this.matrix[i][j] + " |");
			}
			//print an empty row
			System.out.println();
			char[] c2 = new char[4 * size + 2];
			Arrays.fill(c2, '-');
			System.out.println(new String(c2));		
		}
	}

	public char get_cell(int row,int col){
		return this.matrix[row][col];
	}

	public int getSize(){
		return this.size;
	}

	public int player_points(char symbol) {
		int points = 0;
		for(int i = 0; i< size; i++){
			for(int j=0; j<size; j++) {
				if(matrix[i][j] == symbol){
					points++;
				}
			}
		}
		return points;
	}
}
