package controller;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.LoginState;
import model.Player;
/**
 * Controller that handles the Registrazione view
 */
public class RegistrazioneController {
	private Stage stage;
	private Scene scene;
	private Parent root;

	/**
	 * Text field where to put the nickname
	 */
	@FXML
	private TextField nickname;
	/**
	 * Text field where to put the password
	 */
	@FXML
	private PasswordField password;
	/**
	 * Text that pops up if there is an error
	 */
	@FXML
	private Text errore;
	/**
	 * Creates a new account with the inserted nickname and password
	 * @param event
	 * @throws IOException
	 */
	public void onSubmit(ActionEvent event) throws IOException {
		String nick = nickname.getText();
		String passwd = password.getText();
		if(nick.length() == 0 || passwd.length() == 0){
			//errore
			errore.setOpacity(1);
			//dopo 2 secondi il messaggio di errore viene rimosso
			Timer timer = new Timer();
			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					errore.setOpacity(0);
					timer.cancel();
				}
			};
			timer.schedule(task, 1700);
			//se c'e qualche errore non prosegue col metodo
			return;
		}

		Player player = new Player(nick, passwd).create();
		LoginState.setLoggedPlayer(player);
		backToMainMenu(event);
	}
	public void backToLogin(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/views/LoginOrRegister.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	public void backToMainMenu(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/views/MainMenu.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}