package controller;

import java.io.File;
import javafx.scene.media.*;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.LoginState;
import model.Player;

public class MainMenuController {

	@FXML
	private Text nickname;
	@FXML
	private Text livello;
	@FXML
	private ImageView profilePic;
	@FXML
	private Pane profileCard;

	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	public void initialize() throws FileNotFoundException {
		Player p = LoginState.getLoggedPlayer();
		System.out.println(p);
		if (!(p == null)) {
			nickname.setText(p.getNickname());
			livello.setText(String.valueOf(p.getLevel()));
			profilePic.setImage(new Image(new FileInputStream(p.getAvatar())));
			profileCard.setOpacity(1);
		} else {
			profileCard.setOpacity(0);
		}
		
	}

	public void switchToStartGame(ActionEvent event) throws IOException {
		MediaPlayer m= new MediaPlayer(new Media(new File("src\\audioFiles\\uno.wav").toURI().toString()));
		m.autoPlayProperty();
		root = FXMLLoader.load(getClass().getResource("/views/PreGameSettings.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void switchToSettings(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/views/Settings.fxml"));
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