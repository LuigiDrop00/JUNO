package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

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
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Card;
import model.Entity;
import model.Game;

@SuppressWarnings("deprecation")
public class GameController implements Observer{
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	private Button exitButton;
	@FXML
	private AnchorPane scenePane;
	@FXML
	private TilePane mazzoCarte;
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
	
	public void animateDiscard(ImageView img, int giocatore) throws FileNotFoundException {
		//Point2D xyCarta = img.localToScene(img.getLayoutBounds().getMinX(), img.getLayoutBounds().getMinY());
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
			coordXFinali = xyScarti.getX() - 420 + 60/2;
			coordYFinali = xyScarti.getY() - 610 + 80/2;
			img.toFront();
			mano0.getChildren().remove(img);
		break;
		case 1:
			nuovaImg.setLayoutX(-54);
			nuovaImg.setLayoutY(242);
			scenePane.getChildren().add(nuovaImg);
			coordXFinali = xyScarti.getX() + 54 + 60/2;
			coordYFinali = xyScarti.getY() - 242 + 80/2;
			mano1.getChildren().remove(img);
			break;
		case 2:
			nuovaImg.setLayoutX(442);
			nuovaImg.setLayoutY(-80);
			scenePane.getChildren().add(nuovaImg);
			coordXFinali = xyScarti.getX() - 442 + 60/2;
			coordYFinali = xyScarti.getY() + 80 + 60/2;
			mano2.getChildren().remove(img);
			break;
		case 3:
			nuovaImg.setLayoutX(900);
			nuovaImg.setLayoutY(242);
			scenePane.getChildren().add(nuovaImg);
			coordXFinali = xyScarti.getX() - nuovaImg.getLayoutX() + 60/2;
			coordYFinali = xyScarti.getY() - nuovaImg.getLayoutY() + 60/2;
			mano3.getChildren().remove(img);
			break;
		default:
			break;	
		}
		nuovaImg.toFront();
		Path path = new Path();
		path.getElements().add(new MoveTo(0,0));
		path.getElements().add(new LineTo(coordXFinali,coordYFinali));
		PathTransition transition = new PathTransition(Duration.millis(1000), path, nuovaImg);
		transition.play();
	}	
	public void animateDraw(int giocatore) throws FileNotFoundException {
		ImageView nuovaImg = new ImageView();
		nuovaImg.setFitWidth(60);
		nuovaImg.setFitHeight(80);
		nuovaImg.setOnMouseClicked(event -> {
			try { onDiscard(event); } catch (FileNotFoundException e) {}
		});
		Point2D xyPilaMazzo = mazzo.localToScene(mazzo.getLayoutBounds().getMinX(), mazzo.getLayoutBounds().getMinY());
		nuovaImg.setLayoutX(xyPilaMazzo.getX());
		nuovaImg.setLayoutY(xyPilaMazzo.getY());
		//immagine sul mazzo
		
		double coordXFinali = 0;
		double coordYFinali = 0;
		
		switch (giocatore) {
		case 0: 
			coordXFinali = 80;
			coordYFinali = 500;
			//nuovaImg.setImage(new Image(new FileInputStream("src/cardsImages/UNO.jpg")));
			nuovaImg.setImage(new Image(new FileInputStream("src/cardsImages/"+Game.pile.get(0).toString())));
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
		//aggiunta carta nuova
		scenePane.getChildren().add(nuovaImg);
		
		Path path = new Path();
		path.getElements().add(new MoveTo(0f, 0f));
		LineTo line = new LineTo(coordXFinali, coordYFinali);
		path.getElements().add(line);
		
		PathTransition transition = new PathTransition(Duration.millis(1000), path, nuovaImg);
		transition.setOnFinished(event -> {
			ImageView img = new ImageView();
			img.setFitWidth(60);
			img.setFitHeight(80);
			img.setImage(nuovaImg.getImage());

			switch (turno % 4) {
				case 0: mano0.getChildren().addAll(img); break;
				case 1: mano1.getChildren().addAll(img); break;
				case 2: mano2.getChildren().addAll(img); break;
				case 3: mano3.getChildren().addAll(img); break;
			}
		});
		transition.play();
	}
	
	public void onDiscard(MouseEvent event) throws FileNotFoundException {
		if (game.getTurn() == 0) {
			ImageView img = (ImageView) event.getTarget();
			game.playerPlay(Card.pathToCard(img.getImage().getUrl()));
		}
	}
	public void onDraw(MouseEvent event) throws FileNotFoundException {
		if (game.getTurn() == 0) {
			game.playerDraw(players[game.getTurn()], 1);
		}
	}
	
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
	public void testTurno() {
		System.out.println(game.getTurn());
	}

	@Override
	public void update(Observable o, Object arg) {
		String action= (String) arg;
		Entity p= players[game.getTurn()];
		switch (action) {
		case "Draw": try { animateDraw(game.getTurn());} catch (FileNotFoundException e) { e.printStackTrace();} break; //TODO play animazione e suono di pesca; e aggiorna le carte in mano
		case "Play": try {
				animateDiscard(new ImageView(new Image(new FileInputStream(game.pile.get(0).toString()))), game.getTurn()); } catch (FileNotFoundException e) {} break; //TODO play animazione e suono di carta giocata; e aggiorna le carte in mano e la pila
		case "Pass": break; //TODO aggiorna il colore
		case "Uno": break; //TODO play suono uno
		case "NoUno": break; 
		case "IncorrectPlay": break;
		case "Victory": break; //TODO play musica vittoriosa? e mostrare schermata risultati?
		case "Loss": break; //TODO play musica triste? e mostrare schermata risultati?
		}
		System.out.println("azione-eseguita: "+arg);
		System.out.println("giocatore: "+ game.getTurn());
		System.out.println(game.pile.get(0));
		System.out.println("-----------");
		
	}
}
