
import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class reversiboard extends GridPane {
	private Main m = new Main();
	private logic logic;
	private static final char Firstplayer = 'F';
	private static final char Secondplayer = 'S';
	private reversipagecontroller rpc;

	public reversiboard(reversipagecontroller rgc) {
		this.logic = new logic();
		this.rpc = rgc;
		rgc.getPlayer().setText(logic.FirstPlayer);
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("reversiboard.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	static void Display(String title, String message){

		Stage win = new Stage();
		win.setTitle(title);

		win.initModality(Modality.APPLICATION_MODAL);
		win.setMinWidth(20);

		Label l = new Label();
		l.setText(message);
		l.setFont(new Font(20.0));
		Button exit = new Button("Exit");
		win.setOnCloseRequest(e ->{
			win.close();
		});
		exit.setOnAction(e -> {
			win.close();

		});

		VBox lay = new VBox(10);
		lay.getChildren().addAll(l,exit);
		lay.setAlignment(Pos.CENTER);

		Scene scene = new Scene(lay,350,150);
		win.setScene(scene);

		win.showAndWait();

	}

	public void draw() {

		boolean nomove = false;
		boolean endgame= false;
		ArrayList<Point> startpoints = new ArrayList<Point>();
		ArrayList<Point> endpoints = new ArrayList<Point>();

		logic.find_options(rpc.getPlayerSymbol(),startpoints,endpoints);

		if(startpoints.isEmpty()){
			rpc.switchPlayer();
			startpoints.clear();
			endpoints.clear();
			nomove =true;
			logic.find_options(rpc.getPlayerSymbol(),startpoints,endpoints);
			if(startpoints.isEmpty()){
				endgame = true;
			}
		} else { 
 			this.getChildren().clear();
		}
 		this.rpc.getxPoints().setText(String.valueOf(logic.getBoard().player_points('F')));
		this.rpc.getoPoints().setText(String.valueOf(logic.getBoard().player_points('S')));

		double height = (double)this.getPrefHeight() - 75;
		double width = (double)this.getPrefWidth() - 75;
		double size = logic.getBoard().getSize();
		double cellHeight = (double)height /(double) size;
		double cellWidth = (double)width / (double) size;
 		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {

				Rectangle rect = new Rectangle(cellWidth, cellHeight, Color.web("#3f51b5"));
				rect.setStroke(Color.BLACK.darker().darker());
				if (logic.getBoard().get_cell(i, j) == Firstplayer) {
					this.add(rect, j, i);
					this.addDisk(j , i , cellHeight, startpoints, endpoints, logic.get_player(1).getColor());
				} else if(logic.getBoard().get_cell(i, j) == Secondplayer) {
					this.add(rect, j, i);
					this.addDisk(j , i , cellHeight, startpoints, endpoints, logic.get_player(2).getColor());
				} else  {
					this.add(rect, j, i);
				}
			}
		}
 		for (Point point : startpoints) {
			this.addDisk(point.getY(), point.getX(), cellHeight, startpoints, endpoints, Color.TRANSPARENT);
		}
 		if(logic.board_full() || endgame){
			endOfGame();
		} else if(nomove==true){
			Display("Reversi", "No available move!");
		}
	}

	private void endOfGame(){
		int player1Point = Integer.parseInt(this.rpc.getxPoints().getText());
		int player2Point = Integer.parseInt(this.rpc.getoPoints().getText());
		String message = ""; 
		
		if(!logic.board_full()) {
			message = "No Available moves for both players\n";

		}
	
		if(player2Point > player1Point) {
			message = message + "THE WINNER IS PLAYER2";
		} else if ( player1Point == player2Point) {
			message = message + "TIKO TIKO SHIVAION";
		} else {
			message = message + "THE WINNER IS PLAYER1";
		}
		Display("End of Game", message);
		Stage window =(Stage) this.rpc.getPlayer().getScene().getWindow();
		this.m.start(new Stage());
		window.close();

	}

	private void addDisk(int j, int i, double cellHeight ,ArrayList<Point> startpoints,ArrayList<Point> endpoints,Color playerColor){
		Circle downcircle;
		if(playerColor.toString().equals("0x000000ff")){
			downcircle = new Circle(cellHeight/2,Color.WHITE);
		} else if(playerColor.toString().equals("0xffffffff")) {
			downcircle = new Circle(cellHeight/2,Color.BLACK);
		} else {
			downcircle = new Circle(cellHeight/2,Color.TRANSPARENT);
		}
		downcircle.setStroke(Color.BLACK);
		this.add(downcircle, j, i);

		Circle upcircle = new Circle(cellHeight/2 - 1,playerColor);
		upcircle.setStroke(Color.BLACK);
		GridPane.setHalignment(upcircle, HPos.RIGHT);
		GridPane.setValignment(upcircle, VPos.TOP);

		this.add(upcircle, j, i);

		if(playerColor.toString().equals("0x00000000")){
			upcircle.setOnMouseEntered(e-> {
				char symbol = rpc.getPlayerSymbol();
				if(symbol == 'F'){
					upcircle.setFill(this.logic.get_player(1).getColor());
				} else {
					upcircle.setFill(this.logic.get_player(2).getColor());
				}
			});
			upcircle.setOnMouseExited(e-> {
				upcircle.setFill(Color.TRANSPARENT);
			});
		}

		upcircle.setOnMouseClicked(event ->{
			int row = GridPane.getRowIndex(upcircle);
			int col = GridPane.getColumnIndex(upcircle);

			String s = this.rpc.getPlayer().getText();
			if(s.equals(logic.get_player(1).getName())){
				if(logic.playone(logic.get_player(1).getSymbol(),startpoints,endpoints, row, col) != -1) {
					rpc.getPlayer().setText(logic.get_player(2).getName());
					this.draw();
				}
			} else {
				if(logic.playone(logic.get_player(2).getSymbol(),startpoints,endpoints, row, col) != -1) {
					rpc.getPlayer().setText(logic.get_player(1).getName());
					this.draw();
				}
			}
		});
	}

}

