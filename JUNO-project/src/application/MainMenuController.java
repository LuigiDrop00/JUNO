package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.shape.Rectangle;



public class MainMenuController {	

	@FXML
	private Rectangle myRectangle;
	private double x;
	private double y;
	
	public void moveButtonRandomly(ActionEvent e) {
		myRectangle.setX(Math.floor(Math.random()*200));
		myRectangle.setY(Math.floor(Math.random()*200));
		
	}
}
