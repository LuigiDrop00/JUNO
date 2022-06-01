package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Player;

public class MainMenuController {	
	private Stage stage;
	private Scene scene;
	private Parent root;
	public void switchToStartGame(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/views/PreGameSettings.fxml"));
		stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	public void switchToSettings(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/views/Settings.fxml"));
		stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	public void switchToLogin(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/views/LoginOrRegister.fxml"));
		stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	public void switchToUserSettings(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/views/UserSettings.fxml"));
		stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	private static Button logoutButton;
	@FXML
	private static AnchorPane scenePane;
	public void exit(){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText("sei sicuro di uscire dall'applicazione?");
		
		if(alert.showAndWait().get() == ButtonType.OK) {
			stage = (Stage) scenePane.getScene().getWindow();
			stage.close();
		}
	}
	
    @FXML
    private Text nickname;
    @FXML
    private Text livello;
    @FXML
    private ImageView profilePic;

    private static Player loggedPlayer;

    public void logAccount(Player player){

        loggedPlayer = player;
        nickname.setText(player.getNickname());
		livello.setText(String.valueOf(player.getLevel()));
		try {
        profilePic.setImage(new Image(new FileInputStream(player.getAvatar())));   
        } catch (FileNotFoundException e) {
		System.out.println("immagine profilo non trovata!");
        }
    }

    
}
