package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginOrRegisterController {

	private Stage stage;
	private Scene scene;
	private Parent root;
	
	public void backToMainMenu(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/views/MainMenu.fxml"));
		stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	public void switchToRegistrazione(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/views/Registrazione.fxml"));
		stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}
