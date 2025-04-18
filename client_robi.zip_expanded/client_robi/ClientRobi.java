package client_robi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ClientRobi extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("modele.fxml"));
        Parent root = loader.load();
        //primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("img/EdenCodingIcon.png")));
        primaryStage.setTitle("Projet ROBI L3");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        
    }
    
	public static void main(String[] args) {
		launch(args);
		
	}


}
