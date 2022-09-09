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
/**
 * Controller that handles the Statistics view
 */
public class StatisticsController {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	/**
	 * The logged player
	 */
	private Player p = LoginState.getLoggedPlayer();
	/**
	 * Statistics of the logged player
	 */
	@FXML
	private Text wins, losses, gamesPlayed, expGained, currentLevel;
	
	/**
	 * Updates the view whit the statistics of the logged player
	 */
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
