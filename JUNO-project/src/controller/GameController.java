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
import model.Entity;
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
	
	Game game;
	Entity[] players;
	@FXML
	public void initialize() throws FileNotFoundException {
		game=new Game();
		players=game.players;
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
		String action= (String) arg;
		Entity p= players[game.getTurn()];
		switch (action) {
		case "Draw": p.HAND.get(p.HAND.size()-1); break; //TODO play animazione e suono di pesca; e aggiorna le carte in mano
		case "Play": Game.pile.get(Game.pile.size()-1); //TODO play animazione e suono di carta giocata; e aggiorna le carte in mano e la pila
		case "Pass": break; //TODO aggiorna il colore
		case "Uno": break; //TODO play suono uno
		case "NoUno": break; 
		case "IncorrectPlay": break;
		case "Victory": break; //TODO play musica vittoriosa? e mostrare schermata risultati?
		case "Loss": break; //TODO play musica triste? e mostrare schermata risultati?
		}
		
		System.out.println(arg);
		
	}
}
