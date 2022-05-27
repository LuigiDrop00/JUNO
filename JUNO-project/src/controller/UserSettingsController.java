package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class UserSettingsController {

	private Stage stage;
	private Scene scene;
	private Parent root;
	
	public void backToMainMenu(ActionEvent event) throws IOException {
		
		//prompts the user on whether saving or not the data and then changes view
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText("stai per tornare al menu' principale!");
		alert.setContentText("vuoi salvare gli eventuali cambiamenti?");
		if(alert.showAndWait().get() == ButtonType.OK) {
			System.out.println("salvando le impostazioni!");
		}
		
		
		root = FXMLLoader.load(getClass().getResource("/views/MainMenu.fxml"));
		stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	
}
