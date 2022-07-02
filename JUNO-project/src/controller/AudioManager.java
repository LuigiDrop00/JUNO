package controller;
// Java program to play an Audio
// file using Clip Object
import java.io.File;
import javafx.scene.media.*;

public class AudioManager
{
	public static void play(String path) {
		MediaPlayer m= new MediaPlayer(new Media(new File(path).toURI().toString()));
		m.play();
	}

}
