package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.LoginState;
import model.Player;

public class StatisticsController {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	private Player p = LoginState.getLoggedPlayer();
	@FXML
	private Text wins, losses, gamesPlayed, expGained, currentLevel;
	
	@FXML
	public void initialize() {
		
		wins.setText(String.valueOf(p.getVictories()));
		losses.setText(String.valueOf(p.getLosses()));
		gamesPlayed.setText(String.valueOf(p.getGames()));
		expGained.setText(String.valueOf(p.getExp()));
		currentLevel.setText(String.valueOf(p.getLevel()));
	}
	
	public void backToMainMenu(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/views/MainMenu.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}
