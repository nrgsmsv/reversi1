import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class settingpagecontroller implements Initializable {

    @FXML
    private ComboBox<String> sizebox;

    @FXML
    private ColorPicker colorf;

    @FXML
    private ColorPicker colors;

    @FXML
    private ChoiceBox<String> starterbox;

    @FXML
    private Button applyb;

    @FXML
    private Button backb;

    private final int minBoardSize = 4;
    private final int maxBoardSize = 20;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        starterbox.getItems().addAll("player1","player2");
        starterbox.setValue("player1");

        for(int i = minBoardSize ; i <= maxBoardSize ; i+=2) {
            sizebox.getItems().add(String.valueOf(i));
        }
        sizebox.setVisibleRowCount(5);
        readFromFile();
    }
    @FXML
    void applybaction(ActionEvent event) {
        WriteConfigToFile();
        try {
            AnchorPane root4 = FXMLLoader.load(getClass().getResource("mainpage.fxml"));
            Scene scene = new Scene(root4,800,600);
            Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(mainpagecontroller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void backbaction(ActionEvent event) {
        try {
            AnchorPane root5 = FXMLLoader.load(getClass().getResource("mainpage.fxml"));
            Scene scene = new Scene(root5,800,600);
            Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(mainpagecontroller.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                        starterbox.setValue(s2);
                    } else if(s1.equals("color_player1")) {
                        colorf.setValue(Color.web(s2));
                    } else if(s1.equals("color_player2")) {
                        colors.setValue(Color.web(s2));
                    } else if(s1.equals("board_size")) {
                        sizebox.setValue(s2);
                    }

                    line = reader.readLine();
                }
                reader.close();
            } catch (Exception e) {
                System.out.println("not found");
            }

        } else {
              starterbox.setValue("player1");
             colorf.setValue(Color.BLACK);
             sizebox.setValue("8");
        }
    }
    private void WriteConfigToFile() {

        PrintWriter writer = null;

        try{
            writer = new PrintWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(new File("settings.txt"))));

        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            writer.println("start_player;" + starterbox.getValue());
            writer.println("color_player1;" + colorf.getValue().toString());
            writer.println("color_player2;" + colors.getValue().toString());
            writer.println("board_size;" + sizebox.getValue());
            writer.close();
        }

    }
    @FXML
    void colorfaction(ActionEvent event) {

    }

    @FXML
    void colorsaction(ActionEvent event) {

    }

    @FXML
    void sizeboxaction(ActionEvent event) {

    }
}
