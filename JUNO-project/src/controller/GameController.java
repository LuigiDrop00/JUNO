package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Observable;
import java.util.Observer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Game;
import model.LoginState;
import model.Player;

@SuppressWarnings("deprecation")
public class GameController implements Observer{
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	private Button exitButton;
	@FXML
	private AnchorPane scenePane;
	
	Game game= new Game();
	@FXML
	public void initialize() throws FileNotFoundException {
		game.deleteObservers();
		game.addObserver(this);
		game.aiTurn();
		
	}
	
	public void exit(ActionEvent event) throws IOException{
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setContentText("sei sicuro di uscire dalla Partita?");
		
		if(alert.showAndWait().get() == ButtonType.OK) {		
			root = FXMLLoader.load(getClass().getResource("/views/MainMenu.fxml"));
			stage = (Stage)((Node) event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println(arg+game.toString());
		
	}
}
