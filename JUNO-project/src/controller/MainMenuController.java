package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.LoginState;
import model.Player;

public class MainMenuController {

	@FXML
	private Text nickname;
	@FXML
	private Text livello, livelloSuccessivo, expMinima, expMassima;
	@FXML
	private ImageView profilePic;
	@FXML
	private Pane profileCard;
	@FXML
	private ProgressBar barraProgressi;

	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	public void initialize() throws FileNotFoundException {
		Player p = LoginState.getLoggedPlayer();
		System.out.println(p);
		if (!(p == null)) {
			int playerEXP = p.getExp();
			int playerLevel = p.getLevel();
			System.out.println("EXP: "+playerEXP);
			System.out.println("LIV: "+playerLevel);
			
			int minEXP = (int) Math.pow(2, playerLevel+1)*5 - 20;
			int maxEXP = (int) Math.pow(2, playerLevel+2)*5 - 20;
			
					
			nickname.setText(p.getNickname());
			livello.setText(String.valueOf(p.getLevel()));
			livelloSuccessivo.setText("LIV."+(playerLevel+1));
			if (p.getLevel() != 1 ) expMinima.setText(""+minEXP+"XP");
			else expMinima.setText("0XP");
			expMassima.setText(""+maxEXP+"XP");
			profilePic.setImage(new Image(new FileInputStream(p.getAvatar())));
			// p.getExp - expMinimo / (expMassimo - ExpMinimi)
			barraProgressi.setProgress((playerEXP-minEXP) / (double) (maxEXP - minEXP));
			profileCard.setOpacity(1);
		} else {
			profileCard.setOpacity(0);
		}
		
	}

	public void switchToStartGame(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/views/PreGameSettings.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	public void switchToLogin(ActionEvent event) throws IOException {

		root = FXMLLoader.load(getClass().getResource("/views/LoginOrRegister.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void switchToUserSettings(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/views/UserSettings.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	private Button logoutButton;
	@FXML
	private AnchorPane scenePane;

	public void exit() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText("sei sicuro di uscire dall'applicazione?");

		if (alert.showAndWait().get() == ButtonType.OK) {
			stage = (Stage) scenePane.getScene().getWindow();
			stage.close();
		}
	}

}