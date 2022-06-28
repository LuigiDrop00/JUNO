package controller;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.LoginState;

public class UserSettingsController {

	public Image[] profileImages;
	private int profileImagesIndex = -1;
	private File[] listOfImages = new File("src/profilePic").listFiles();
	
	@FXML
	private TextField nickField;
	
	@FXML
	public void initialize() {
		nickField.setText(LoginState.getLoggedPlayer().getNickname());
	}
	
	public UserSettingsController() throws IOException {
		
		profileImages = new Image[listOfImages.length];
		
		for (int i = 0; i < listOfImages.length; i++) {
			File rawImage = listOfImages[i];
			System.out.println(rawImage.getPath());
			Image image = SwingFXUtils.toFXImage(ImageIO.read(rawImage), null);
			
			profileImages[i] = image;
		}
	}

	private Stage stage;
	private Scene scene;
	private Parent root;

	@FXML
	private ImageView profilePic;

	public void backToMainMenu(ActionEvent event) throws IOException {
		// prompts the user on whether saving or not the data and then changes view
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText("stai per tornare al menu' principale!");
		alert.setContentText("vuoi salvare gli eventuali cambiamenti?");
		if (alert.showAndWait().get() == ButtonType.OK) {
			//imposta l'immagine scelta con quella del profilo
			if(profileImagesIndex == -1) profileImagesIndex = 0;
			LoginState.getLoggedPlayer().setAvatar(listOfImages[profileImagesIndex].getPath());
			LoginState.getLoggedPlayer().setNickname(nickField.getText());
		}

		root = FXMLLoader.load(getClass().getResource("/views/MainMenu.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void immagineSuccessiva() {
		if(profileImagesIndex == profileImages.length-1) profileImagesIndex = -1;
		profilePic.setImage(profileImages[++profileImagesIndex]);
	}

	public void immaginePrecedente() {
		if(profileImagesIndex == 0 || profileImagesIndex == -1) profileImagesIndex = profileImages.length;
		profilePic.setImage(profileImages[--profileImagesIndex]);
	}

}
