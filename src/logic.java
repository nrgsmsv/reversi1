
import java.io.*;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import javafx.scene.paint.Color;


public class logic {
	private Player[] players = new Player[2];
	public String FirstPlayer;
	private Board board;

	public logic() {

		this.players[0] = new Player("Firstplayer", 'F');
		this.players[1] = new Player("Secondplayer", 'S');

		readFromFile();
		this.init_start_board();
	}
	private void readFromFile(){
		File f = new File("settings.txt");

		if(f.exists()) {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(f.getAbsolutePath()));

				String line = reader.readLine();
				while (line != null) {
					StringBuilder contentBuffer = new StringBuilder();
					contentBuffer.append(line.trim());
					String[] parts = contentBuffer.toString().split(";");
					String s1 = parts[0];
					String s2 = parts[1];

					if(s1.equals("start_player")) {
						FirstPlayer = s2;
					} else if(s1.equals("color_player1")) {
						players[0].setColor(Color.web(s2));
					} else if(s1.equals("color_player2")) {
						players[1].setColor(Color.web(s2));
					} else if(s1.equals("board_size")) {
						this.board = new Board(Integer.parseInt(s2));
					}

					line = reader.readLine();
				}
				reader.close();
			} catch (Exception e) {
				System.out.println("not found");
			}

		} else { 

			players[0].setColor(Color.BLACK);
			players[1].setColor(Color.WHITE);
			this.board =new Board(8);
			this.FirstPlayer = "Firstplayer";
		}
	}

	private void init_start_board() {
		this.board.set_matrix(this.board.getSize()/2 - 1,this.board.getSize()/2 - 1,'S');
		this.board.set_matrix(this.board.getSize()/2,this.board.getSize()/2,'S');
		this.board.set_matrix(this.board.getSize()/2,this.board.getSize()/2 - 1,'F');
		this.board.set_matrix(this.board.getSize()/2 - 1,this.board.getSize()/2,'F');
	}

	public void print_board(){
		this.board.print_matrix();
	}

	public Player get_player(int i) {
		return players[i-1];
	}

	public Board getBoard(){
		return this.board;
	}

	public int playone(char symbol,ArrayList<Point> start_points,ArrayList<Point> end_points, int row,int col) {
 		Set <Point> s = new TreeSet<Point>();
		for (Point point : start_points) {
			s.add(point);
		}
		//if point in set change board
		if(is_point_in_set(new Point(row, col), s) == true) {
			change_all_points(symbol,new Point(row,col),start_points,end_points);
			return 0;
		} //else dont change board
		return -1;
	}

	public void find_options(char symbol, ArrayList<Point> start, ArrayList<Point> end) {
		int size = this.board.getSize();
		
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++ ) {
				Point p = new Point(-1,-1);
				if (this.board.get_cell(row,col) == ' ') {
 					p = this.check_right(row,col,symbol);
					if (p.valid_point() == true) {
						start.add(new Point(row,col));
						end.add(p);
					}
					p = this.check_left(row,col,symbol);
					if (p.valid_point() == true) {
						start.add(new Point(row,col));
						end.add(p);
					}
					p = this.check_up(row,col,symbol);
					if (p.valid_point() == true) {
						start.add(new Point(row,col));
						end.add(p);
					}
					p = this.check_down(row,col,symbol);
					if (p.valid_point() == true) {
						start.add(new Point(row,col));
						end.add(p);
					}
					p = this.check_up_right(row,col,symbol);
					if (p.valid_point() == true) {
						start.add(new Point(row,col));
						end.add(p);
					}
					p = this.check_down_right(row,col,symbol);
					if (p.valid_point() == true){
						start.add(new Point(row,col));
						end.add(p);
					}
					p = this.check_up_left(row,col,symbol);
					if (p.valid_point() == true) {
						start.add(new Point(row,col));
						end.add(p);
					}
					p = this.check_down_left(row,col,symbol);
					if (p.valid_point() == true) {
						start.add(new Point(row,col));
						end.add(p);
					}
				}

			}
		}
	}

	private Point check_right(int row, int col, char symbol) {
		int i = row;
		int j = col + 1;
		char flip_symbol;
		int flip_ctr = 0;

		if (symbol == 'F') {
			flip_symbol = 'S';
		} else {
			flip_symbol = 'F';
		}
		for(; j < this.board.getSize() - 1; j++) {
			if (this.board.get_cell(i,j) == flip_symbol) {
				flip_ctr++;
			} else {
				break;
			}
		}
		if (flip_ctr > 0 && this.board.get_cell(i,j) == symbol) {
			return new Point(i,j);
		}
		return new Point(-1,-1);
	}

	private Point check_left(int row, int col, char symbol){
		int i = row;
		int j = col - 1;
		int flip_ctr = 0;
		char flip_symbol;

		if (symbol == 'F') {
			flip_symbol = 'S';
		} else {
			flip_symbol = 'F';
		}
		for(; j > 0; j--) {
			if (this.board.get_cell(i,j) == flip_symbol) {
				flip_ctr++;
			} else {
				break;
			}
		}
		if (flip_ctr > 0 && this.board.get_cell(i,j) == symbol) {
			return new Point(i,j);
		}
		return new Point(-1,-1);
	}

	private Point check_up(int row, int col, char symbol) {
		int i = row - 1;
		int j = col;
		int flip_ctr = 0;
		char flip_symbol;

		if (symbol == 'F') {
			flip_symbol = 'S';
		} else {
			flip_symbol = 'F';
		}
		for(; i > 0; i--) {
			if (this.board.get_cell(i,j) == flip_symbol) {
				flip_ctr++;
			} else {
				break;
			}
		}
		if (flip_ctr > 0 && this.board.get_cell(i,j) == symbol) {
			return new Point(i,j);
		}
		return new Point(-1,-1);
	}

	private Point check_down(int row, int col, char symbol){
		int i = row + 1;
		int j = col;
		int flip_ctr = 0;
		char flip_symbol;

		if (symbol == 'F') {
			flip_symbol = 'S';
		} else {
			flip_symbol = 'F';
		}
		for(; i < this.board.getSize() - 1; i++) {
			if (this.board.get_cell(i,j) == flip_symbol) {
				flip_ctr++;
			} else {
				break;
			}
		}
		if (flip_ctr > 0 && this.board.get_cell(i,j) == symbol) {
			return new Point(i,j);
		}
		return new Point(-1,-1);
	}

	private Point check_up_right(int row, int col,char symbol){
		int i = row - 1;
		int j = col + 1;
		int flip_ctr = 0;
		char flip_symbol;

		if (symbol == 'F') {
			flip_symbol = 'S';
		} else {
			flip_symbol = 'F';
		}
		int size = this.board.getSize();
		for(; j < size - 1 && i > 0; j++, i--) {
			if (this.board.get_cell(i,j) == flip_symbol) {
				flip_ctr++;
			} else {
				break;
			}
		}
		if (flip_ctr > 0 && this.board.get_cell(i,j) == symbol) {
			return new Point(i,j);
		}
		return new Point(-1,-1);
	}

	private Point check_down_right(int row, int col, char symbol) {
		int i = row + 1;
		int j = col + 1;
		int flip_ctr = 0;
		char flip_symbol;
		if (symbol == 'F') {
			flip_symbol = 'S';
		} else {
			flip_symbol = 'F';
		}
		int size =this.board.getSize();
		for(; j < size - 1 && i < size -1; j++, i++) {
			if (this.board.get_cell(i,j) == flip_symbol) {
				flip_ctr++;
			} else {
				break;
			}
		}
		if (flip_ctr > 0 && this.board.get_cell(i,j) == symbol) {
			return new Point(i,j);
		}
		return new Point(-1,-1);
	}

	private Point check_up_left(int row, int col, char symbol) {
		int i = row - 1;
		int j = col - 1;
		int flip_ctr = 0;
		char flip_symbol;
		if (symbol == 'F') {
			flip_symbol = 'S';
		} else {
			flip_symbol = 'F';
		}

		for(; j > 0 && i > 0; j--,i--) {
			if (this.board.get_cell(i,j) == flip_symbol) {
				flip_ctr++;
			} else {
				break;
			}
		}
		if (flip_ctr > 0 && this.board.get_cell(i,j) == symbol) {
			return new Point(i,j);
		}
		return new Point(-1,-1);
	}

	private Point check_down_left(int row, int col, char symbol) {
		int i = row + 1;
		int j = col - 1;
		int flip_ctr = 0;
		char flip_symbol;
		if (symbol == 'F') {
			flip_symbol = 'S';
		} else {
			flip_symbol = 'F';
		}
		int size = this.board.getSize();
		for(; j > 0 && i < size - 1; j--,i++) {
			if (this.board.get_cell(i,j) == flip_symbol) {
				flip_ctr++;
			} else {
				break;
			}
		}
		if (flip_ctr > 0 && this.board.get_cell(i,j) == symbol) {
			return new Point(i,j);
		}
		return new Point(-1,-1);
	}

	private void change_all_points(char symbol, Point point, ArrayList<Point> start_points, ArrayList<Point> end_points) {
		for (int i = 0; i < start_points.size(); i++) {
			if(start_points.get(i).isEqual(point)) {
				change_board_point_to_point(start_points.get(i),end_points.get(i),symbol);
			}
		}
	}

	private void change_board_point_to_point(Point p1, Point p2,char symbol) {
		this.board.set_matrix(p1.getX(),p1.getY(),symbol);
		int x_s = p1.getX();
		int y_s = p1.getY();
		int x_e = p2.getX();
		int y_e = p2.getY();

		if(x_s == x_e) {
			if(y_s < y_e){
				for(int i = y_s + 1; i < y_e; i++){
					this.board.set_matrix(x_s,i,symbol);
				}
			} else {
				for(int i = y_s -1; i > y_e; i--){
					this.board.set_matrix(x_s,i,symbol);
				}
			}
		} else if(y_s == y_e) {
			if(x_s < x_e){
				for(int i = x_s + 1; i < x_e; i++){
					this.board.set_matrix(i,y_s,symbol);
				}
			} else {
				for(int i = x_s -1; i > x_e; i--){
					this.board.set_matrix(i,y_s,symbol);
				}
			}
		} else if(x_s > x_e) {
			if(y_s < y_e){
				for(int i = x_s - 1, j = y_s + 1 ; i > x_e; i--,j++){
					this.board.set_matrix(i,j,symbol);
				}
			} else {
				for(int i = x_s - 1, j = y_s - 1 ; i > x_e; i--,j--){
					this.board.set_matrix(i,j,symbol);
				}
			}
		} else {
			if(y_s < y_e){
				for(int i = x_s + 1, j = y_s +1 ; i < x_e; i++,j++){
					this.board.set_matrix(i,j,symbol);
				}
			} else {
				for(int i = x_s + 1, j = y_s - 1 ; i < x_e; i++,j--){
					this.board.set_matrix(i,j,symbol);
				}
			}
		}
	}

	public boolean is_point_in_set(Point p, Set<Point> s) {
		for (Point point : s) {
			if(point.isEqual(p)) {
				return true;
			}
		}
		return false;
	}

	public boolean board_full() {
		int size = this.board.getSize();
		if(this.board.player_points('F') + this.board.player_points('S') == size*size) {
			return true;
		}
		return false;
	}
}
