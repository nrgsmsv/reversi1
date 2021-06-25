
import javafx.application.Application;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;
 
public class Main extends Application {
	private Stage root1 = null;
	
	public void start(Stage primaryStage) {
		
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("mainpage.fxml"));
			Scene scene = new Scene(root,800,600);
			root1 = primaryStage;
			primaryStage.setTitle("Reversi Game");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) {
		launch(args);
	}
	
	public Stage getStage(){
		return this.root1;
	}

}
