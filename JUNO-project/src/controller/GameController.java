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
		ImageView nuovaImg = new ImageView();
		nuovaImg.setFitWidth(60);
		nuovaImg.setFitHeight(80);
		System.out.println("giocatore: "+giocatore % 4);
		Point2D xyCarta = img.localToScene(img.getLayoutBounds().getMinX(), img.getLayoutBounds().getMinY());
		System.out.println("COORDINATE ASSOLUTE CARTA CLICCATA");
		System.out.println(scarti.getX());
		System.out.println(scarti.getY());
		Point2D xyScarti = scarti.localToScene(scarti.getLayoutBounds().getMinX(), scarti.getLayoutBounds().getMinY());
		double coordXFinali = 0;
		double coordYFinali = 0;
		
		nuovaImg.setImage(new Image(new FileInputStream("src/cardsImages/"+Game.pile.get(0).toString())));			
		switch (giocatore % 4) {
		case 0: 
		    nuovaImg.setLayoutX(420);
			nuovaImg.setLayoutY(610);
			scenePane.getChildren().add(nuovaImg);
			coordXFinali = xyScarti.getX() - 420 + 60/2;
			coordYFinali = xyScarti.getY() - 610 + 80/2;
			img = nuovaImg;
			img.toFront();
		break;
		case 1:
			nuovaImg.setLayoutX(-54);
			nuovaImg.setLayoutY(242);
			scenePane.getChildren().add(nuovaImg);
			coordXFinali = xyScarti.getX() + 54 + 60/2;
			coordYFinali = xyScarti.getY() - 242 + 80/2;
			img = nuovaImg;
			break;
		case 2:
			nuovaImg.setLayoutX(442);
			nuovaImg.setLayoutY(-80);
			scenePane.getChildren().add(nuovaImg);
			coordXFinali = xyScarti.getX() - 442 + 60/2;
			coordYFinali = xyScarti.getY() + 80 + 60/2;
			img = nuovaImg;
			break;
		case 3:
			nuovaImg.setLayoutX(900);
			nuovaImg.setLayoutY(242);
			scenePane.getChildren().add(nuovaImg);
			coordXFinali = xyScarti.getX() - nuovaImg.getLayoutX() + 60/2;
			coordYFinali = xyScarti.getY() - nuovaImg.getLayoutY() + 60/2;
			img = nuovaImg;
			break;
		default:
			break;	
		}
		img.toFront();
		Path path = new Path();
		path.getElements().add(new MoveTo(0,0));
		path.getElements().add(new LineTo(coordXFinali,coordYFinali));
		PathTransition transition = new PathTransition(Duration.millis(1000), path, img);
		transition.setOnFinished(event -> System.out.println("ANIMAZIUONE FINITA"));
		transition.play();
	}	
	public void animateDraw(int turno) throws FileNotFoundException {
		ImageView nuovaImg = new ImageView();
		nuovaImg.setFitWidth(60);
		nuovaImg.setFitHeight(80);
		System.out.println("giocatore: "+turno % 4);
		Point2D xyPilaMazzo = mazzo.localToScene(mazzo.getLayoutBounds().getMinX(), mazzo.getLayoutBounds().getMinY());
		nuovaImg.setLayoutX(xyPilaMazzo.getX());
		nuovaImg.setLayoutY(xyPilaMazzo.getY());
		//immagine sul mazzo
		
		double coordXFinali = 0;
		double coordYFinali = 0;
		
//			nuovaImg.setImage(new Image(new FileInputStream("src/cardsImages/"+game.players[turno].HAND.get(game.players[turno].HAND.size()-1))));
		switch (turno % 4) {
		case 0: 
			coordXFinali = 80;
			coordYFinali = 500;
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
			System.out.println(mazzo.getX());
			System.out.println(mazzo.getY());
			coordXFinali = 600;
			coordYFinali = 5;
			nuovaImg.setImage(new Image(new FileInputStream("src/cardsImages/UNO.jpg")));
			nuovaImg.toFront();
			break;
		default:
			break;
		}
		//aggiunta carta nuova
		scenePane.getChildren().add(nuovaImg);
		
		Path path = new Path();
		path.getElements().add(new MoveTo(0f, 0f));
		LineTo line = new LineTo(coordXFinali, coordYFinali);
		System.out.println(line.getX());
		System.out.println(line.getY());
		path.getElements().add(line);
		PathTransition transition = new PathTransition(Duration.millis(1000), path, nuovaImg);
		transition.play();
	
	}
	
	int turno = 0;
	public void onClick(MouseEvent event) throws FileNotFoundException {
		
		ImageView img = (ImageView) event.getTarget();
		//animateDiscard(img,turno++);
		animateDraw( turno++);
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

	@Override
	public void update(Observable o, Object arg) {
		String action= (String) arg;
		Entity p= players[game.getTurn()];
		switch (action) {
		case "Draw": p.HAND.get(p.HAND.size()-1);
		break; //TODO play animazione e suono di pesca; e aggiorna le carte in mano
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
