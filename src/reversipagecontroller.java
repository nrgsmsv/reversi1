

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class reversipagecontroller implements Initializable {
	@FXML
	private VBox root;
	@FXML
	private HBox son;
	@FXML
	private VBox page;
	@FXML
	private Label Player;
	@FXML
	private Button exitgame;

	@FXML
	private Label Fplayer = new Label("2");
	@FXML
	private Label Splayer = new Label("2");
	@FXML
	private Button newGame = new Button();
	@FXML
	private reversiboard reversiboard;
	@FXML
	private MenuBar bar = new MenuBar();

	@FXML
	private MenuItem exit = new MenuItem("Exit");
	@FXML
	private MenuItem settings = new MenuItem("Settings");


	//private mainpagecontroller m = new mainpagecontroller();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		reversiboard = new reversiboard(this);

		reversiboard.setPrefWidth(400);
		reversiboard.setPrefHeight(400);
		son.getChildren().add(0,reversiboard);

		root.widthProperty().addListener((observable, oldValue, newValue) -> {
			double boardNewWidth = newValue.doubleValue() - 200;
			reversiboard.setPrefWidth(boardNewWidth);
			reversiboard.draw();
		});

		root.heightProperty().addListener((observable, oldValue, newValue) -> {
			reversiboard.setPrefHeight(newValue.doubleValue());
			reversiboard.draw();
		});


	}

	public Label getxPoints() {
		return Fplayer;
	}

	public Label getoPoints() {
		return Splayer;
	}

	public Label getPlayer() {
		return Player;
	}

	public char getPlayerSymbol(){
		if(this.getPlayer().getText().equals("Firstplayer")){
			return 'F';
		}
		return 'S';
	}

	public void switchPlayer(){
		if(this.getPlayerSymbol() == 'F'){
			this.Player.setText("Secondplayer");
		} else {
			this.Player.setText("Firstplayer");
		}
	}

	@FXML
	void newGameClick(ActionEvent event) {
		try {
			VBox page = FXMLLoader.load(getClass().getResource("reversipage.fxml"));
			Scene scene = new Scene(page,800,600);
			Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
			window.setScene(scene);
			window.show();
		} catch (IOException ex) {
			Logger.getLogger(mainpagecontroller.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	@FXML
	void exitgameaction(ActionEvent event) {
		Stage stage = (Stage) exitgame.getScene().getWindow();
		stage.close();
	}

    @FXML
	void setaction(ActionEvent event) {
		Stage win = new Stage();
 		win.setMinWidth(250);
 		AnchorPane root6 = null;
		try {
			root6 = (AnchorPane)FXMLLoader.load(getClass().getResource("settingpage.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		Scene scene = new Scene(root6,800,600);
		win.setScene(scene);
		win.showAndWait();
	}
}
