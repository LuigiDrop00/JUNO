package application;
	
import java.io.IOException;

import controller.UserSettingsController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import model.Mazzo;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;


public class JUNO extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			Parent root = FXMLLoader.load(getClass().getResource("/views/MainMenu.fxml"));
			Scene scene = new Scene(root,Color.AQUAMARINE);
			
			primaryStage.setScene(scene);
		    primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException {
		//TODO creaMazzo
		new UserSettingsController();
		Mazzo.getInstance();
		
		launch(args);
	}
}
