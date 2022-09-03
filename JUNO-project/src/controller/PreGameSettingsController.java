package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Game;

public class PreGameSettingsController {

	private Stage stage;
	private Scene scene;
	private Parent root;
	
	public void clickClassic() {
		Game.setGameMode("classic");
	}
	
	public void clickTeams() {
		Game.setGameMode("teams");
	}
	
	public void clickChaos() {
		Game.setGameMode("chaos");
		System.out.println("CHAOOOOOOOOOOOOOOOOOS");
	}
	
	public void startGame(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/views/GameView.fxml"));
		stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void backToMainMenu(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/views/MainMenu.fxml"));
		stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}
