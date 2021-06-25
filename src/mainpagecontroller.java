import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class mainpagecontroller {

    @FXML
    private Button playgame;

    @FXML
    private Button settings;

    @FXML
    private Button exit;

    @FXML
    void exitaction(ActionEvent event) {
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }

    @FXML
    void playgameaction(ActionEvent event) {
        try {
            VBox root = FXMLLoader.load(getClass().getResource("reversipage.fxml"));
            Scene scene = new Scene(root,800,600);
            Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(mainpagecontroller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void settingsaction(ActionEvent event) {
try {
            AnchorPane root3 = FXMLLoader.load(getClass().getResource("settingpage.fxml"));
            Scene scene = new Scene(root3,800,600);
            Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(mainpagecontroller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

