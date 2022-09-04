package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Card;
import model.Color;
import model.Deck;
import model.Entity;
import model.Game;

@SuppressWarnings("deprecation")
public class GameController implements Observer {

	private Stage stage;
	private Scene scene;
	private Parent root;

	@FXML
	private Button exitButton;
	@FXML
	private AnchorPane scenePane;
	@FXML
	private ImageView playerAvatar;
	@FXML
	private Text playerName;
	@FXML
	private TilePane mazzoCarte;
	@FXML
	private Text currentColor;
	@FXML
	private TilePane mano0;
	@FXML
	private TilePane mano1;
	@FXML
	private TilePane mano2;
	@FXML
	private TilePane mano3;
	@FXML
	private ImageView scarti;
	@FXML
	private ImageView mazzo;
	@FXML
	private ImageView lastPlayedCard;
	@FXML
	private AnchorPane pannelloScegli;

	@FXML
	private void clickGreen() {
		onColorChoosen(Color.GREEN);
	}

	@FXML
	private void clickYellow() {
		onColorChoosen(Color.YELLOW);
	}

	@FXML
	private void clickRed() {
		onColorChoosen(Color.RED);
	}

	@FXML
	private void clickBlue() {
		onColorChoosen(Color.BLUE);
	}

	private void openChooseColor() {
		pannelloScegli.toFront();
	}

	private void onColorChoosen(Color choosenColor) {
		Game.pile.getFirst().setColor(choosenColor);
		pannelloScegli.toBack();
	}

	public void animateDiscard(ImageView img, int giocatore) throws FileNotFoundException {
		// Point2D xyCarta = img.localToScene(img.getLayoutBounds().getMinX(),
		// img.getLayoutBounds().getMinY());
		Point2D xyScarti = scarti.localToScene(scarti.getLayoutBounds().getMinX(), scarti.getLayoutBounds().getMinY());
		double coordXFinali = 0;
		double coordYFinali = 0;

		ImageView nuovaImg = new ImageView();
		nuovaImg.setFitWidth(60);
		nuovaImg.setFitHeight(80);
		nuovaImg.setImage(img.getImage());
		switch (giocatore) {
		case 0:
			nuovaImg.setLayoutX(420);
			nuovaImg.setLayoutY(610);
			nuovaImg.setImage(img.getImage());
			scenePane.getChildren().add(nuovaImg);
			coordXFinali = xyScarti.getX() - 420 + 60 / 2;
			coordYFinali = xyScarti.getY() - 610 + 80 / 2;
			System.out.println(lastPlayedCard.toString());
			mano0.getChildren().remove(lastPlayedCard);
			break;
		case 1:
			nuovaImg.setLayoutX(-54);
			nuovaImg.setLayoutY(242);
			scenePane.getChildren().add(nuovaImg);
			coordXFinali = xyScarti.getX() + 54 + 60 / 2;
			coordYFinali = xyScarti.getY() - 242 + 80 / 2;
			mano1.getChildren().remove(0);
			break;
		case 2:
			nuovaImg.setLayoutX(442);
			nuovaImg.setLayoutY(-80);
			scenePane.getChildren().add(nuovaImg);
			coordXFinali = xyScarti.getX() - 442 + 60 / 2;
			coordYFinali = xyScarti.getY() + 80 + 60 / 2;
			mano2.getChildren().remove(0);
			break;
		case 3:
			nuovaImg.setLayoutX(900);
			nuovaImg.setLayoutY(242);
			scenePane.getChildren().add(nuovaImg);
			coordXFinali = xyScarti.getX() - nuovaImg.getLayoutX() + 60 / 2;
			coordYFinali = xyScarti.getY() - nuovaImg.getLayoutY() + 60 / 2;
			mano3.getChildren().remove(0);
			break;
		default:
			break;
		}
		nuovaImg.toFront();
		Path path = new Path();
		path.getElements().add(new MoveTo(0, 0));
		path.getElements().add(new LineTo(coordXFinali, coordYFinali));
		PathTransition transition = new PathTransition(Duration.millis(1000), path, nuovaImg);
		transition.play();
	}

	public void animateDraw(int giocatore) throws FileNotFoundException {
		ImageView nuovaImg = new ImageView();
		nuovaImg.setFitWidth(60);
		nuovaImg.setFitHeight(80);

		Point2D xyPilaMazzo = mazzo.localToScene(mazzo.getLayoutBounds().getMinX(), mazzo.getLayoutBounds().getMinY());
		nuovaImg.setLayoutX(xyPilaMazzo.getX());
		nuovaImg.setLayoutY(xyPilaMazzo.getY());
		// immagine sul mazzo

		double coordXFinali = 0;
		double coordYFinali = 0;
		String imgPath = "src/cardsImages/" + players[0].HAND.get(players[0].HAND.size() - 1);

		switch (giocatore) {
		case 0:
			coordXFinali = 80;
			coordYFinali = 500;
			// nuovaImg.setImage(new Image(new FileInputStream("src/cardsImages/UNO.jpg")));
			nuovaImg.setImage(new Image(new FileInputStream(imgPath)));
			break;
		case 1:
			coordXFinali = -385;
			coordYFinali = 5;
			nuovaImg.setImage(new Image(new FileInputStream("src/cardsImages/UNO.jpg")));
			break;
		case 2:
			coordXFinali = 45;
			coordYFinali = -280;
			nuovaImg.setImage(new Image(new FileInputStream("src/cardsImages/UNO.jpg")));
			break;
		case 3:
			coordXFinali = 600;
			coordYFinali = 5;
			nuovaImg.setImage(new Image(new FileInputStream("src/cardsImages/UNO.jpg")));
			break;
		default:
			break;
		}
		nuovaImg.toFront();
		// aggiunta carta nuova
		scenePane.getChildren().add(nuovaImg);

		Path path = new Path();
		path.getElements().add(new MoveTo(0f, 0f));
		LineTo line = new LineTo(coordXFinali, coordYFinali);
		path.getElements().add(line);

		PathTransition transition = new PathTransition(Duration.millis(1000), path, nuovaImg);
		transition.setOnFinished(event -> {
			ImageView img1 = new ImageView();
			img1.setFitWidth(60);
			img1.setFitHeight(80);
			img1.setImage(nuovaImg.getImage());
			img1.addEventFilter(MouseEvent.MOUSE_CLICKED, event1 -> {
				try {
					onDiscard(imgPath, event1);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			});
//			img1.setOnMouseClicked(mouseEvent -> {
//				try { onDiscard(mouseEvent); } catch (FileNotFoundException e) { e.printStackTrace();}
//			});
			switch (giocatore) {
			case 0:
				mano0.getChildren().addAll(img1);
				break;
			case 1:
				mano1.getChildren().addAll(img1);
				break;
			case 2:
				mano2.getChildren().addAll(img1);
				break;
			case 3:
				mano3.getChildren().addAll(img1);
				break;
			}
		});
		transition.play();
	}

	public void onDiscard(String path, MouseEvent event) throws FileNotFoundException {
		lastPlayedCard = (ImageView) event.getTarget();
		if (game.getTurn() == 0) {
			game.playerPlay(Card.pathToCard(path));
		}
	}

	public void onDraw(MouseEvent event) throws FileNotFoundException {
		if (game.getTurn() == 0) {
			game.playerDraw(players[game.getTurn()], 1);
		}
	}

	Game game;
	Entity[] players;
	AudioManager music;
	AudioManager draw;
	AudioManager play;
	AudioManager uno;
	AudioManager error;
	AudioManager win;
	AudioManager loss;
	
	@FXML
	public void initialize() throws FileNotFoundException {

		game = new Game();
		players = Game.players;
		playerAvatar.setImage(new Image(new FileInputStream(players[0].getAvatar())));
		playerName.setText(players[0].getNickname());
		music = new AudioManager("Still alive Radio loop.mp3");
		draw = new AudioManager("Draw.wav");
		play = new AudioManager("Play.wav");
		uno = new AudioManager("uno.wav");
		error = new AudioManager("chord.wav");
		win = new AudioManager("crowd-cheer-ii-6263.mp3");
		loss = new AudioManager("no-luck-too-bad-disappointing-sound-effect-112943.mp3");
		music.play(true);

		for (Card card : players[0].HAND) {
			System.out.println(card.toString());
			ImageView img = new ImageView();
			img.setFitWidth(60);
			img.setFitHeight(80);
			img.setImage(new Image(new FileInputStream("src/cardsImages/" + card.toString())));
			img.addEventFilter(MouseEvent.MOUSE_CLICKED, event1 -> {
				try {
					onDiscard("src/cardsImages/" + card.toString(), event1);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			});
			mano0.getChildren().add(img);
		}
		scarti.setImage(new Image(new FileInputStream("src/cardsImages/" + Game.pile.get(0).toString())));

		game.addObserver(this);
		game.aiTurn();
	}

	public void exit(ActionEvent event) throws IOException {

		for (Entity e : players) {
			// resetta le mani dei giocatori
			Deck.getInstance().refill(e.HAND);
		}
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setContentText("sei sicuro di uscire dalla Partita?");

		if (alert.showAndWait().get() == ButtonType.OK) {
			music.stop();
			root = FXMLLoader.load(getClass().getResource("/views/MainMenu.fxml"));
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
	}

	@FXML
	private void passaTurno() {
		System.out.println("passaTUrno");
		game.pass();
	}

	@FXML
	private void clickUno() {
		System.out.println("ClickUno");
		game.pressUno();
	}

	@Override
	public void update(Observable o, Object arg) {
		String action = (String) arg;
		Entity p = players[game.getTurn()];
		switch (action) {
		case "Draw":
			try {
				animateDraw(game.getTurn(game.getSkip()));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			draw.play(false);
			break;
		case "Play":
			try {
				animateDiscard(
						new ImageView(new Image(new FileInputStream("src/cardsImages/" + Game.pile.get(0).toString()))),
						game.getTurn());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			play.play(false);
			break;
		case "ChangeColor":
			openChooseColor();
			break; // TODO chiamare il metodo pop-up scegli colore
		case "Pass":
			currentColor.setText(Game.pile.getFirst().getColor().toString());
			break; // TODO aggiorna il colore
		case "Uno":
			uno.play(false);
			break;
		case "NoUno":
			break;
		case "InvalidPlay": error.play(false);
			break;
		case "Win":
			for (Entity e : players) {
				// resetta le mani dei giocatori
				Deck.getInstance().refill(e.HAND);
			}
			try {
				root = FXMLLoader.load(getClass().getResource("/views/Victory.fxml"));
				stage = (Stage) exitButton.getScene().getWindow();
				scene = new Scene(root);
				stage.setScene(scene);
				stage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			music.stop();
			win.play(false);
			break; // TODO play musica vittoriosa? e mostrare schermata risultati?
		case "Loss":
			for (Entity e : players) {
				// resetta le mani dei giocatori
				Deck.getInstance().refill(e.HAND);
			}
			try {
				root = FXMLLoader.load(getClass().getResource("/views/Loss.fxml"));
				stage = (Stage) exitButton.getScene().getWindow();
				scene = new Scene(root);
				stage.setScene(scene);
				stage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			music.stop();
			loss.play(false);
			break; // TODO play musica triste? e mostrare schermata risultati?
		}
		System.out.println("azione-eseguita: " + arg);
		System.out.println("giocatore: " + game.getTurn());
		System.out.println(game.pile.get(0));
		System.out.println("-----------");
	}

	@FXML
	public void rosso(MouseEvent e) {
		System.out.println(e.getTarget());
	}
}
