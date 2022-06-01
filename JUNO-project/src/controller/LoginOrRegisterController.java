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
import model.IncorrectPasswordException;
import model.Player;
import model.SaveNotFoundException;

public class LoginOrRegisterController {
	private Stage stage;
	private Scene scene;
	private Parent root;

	public void backToMainMenu(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/views/MainMenu.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void switchToRegistrazione(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/views/Registrazione.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	private TextField nickname;
	@FXML
	private PasswordField password;
	@FXML
	private Text errore;

	public void onSubmit(ActionEvent event) throws SaveNotFoundException {
		// INSERTED CREDENTIALS
		String nick = nickname.getText();
		String passwd = password.getText();
		try {
			if(nick.length() == 0) throw new IncorrectPasswordException();
			//cerca il giocatore
			Player player = Player.load(nick, passwd);
			//carica il menu principale
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MainMenu.fxml"));
			root = loader.load();
			MainMenuController controller = loader.getController();
			//inserisce il giocatore nel menu
			controller.logAccount(player);
			//salta alla scena del menu
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			System.out.println("something went wrong while retrieving file");
		} catch (IncorrectPasswordException e) {
			errore.setText("errore nell'inserimento dati, riprova!");
		} catch(SaveNotFoundException e) {
			errore.setText("Account non trovato, riprova!");
		} finally {
			errore.setOpacity(1);
			Timer timer = new Timer();
			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					errore.setOpacity(0);
					timer.cancel();
				}
			};
			timer.schedule(task, 1700);
		}
	}
}
