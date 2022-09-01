package controller;
// Java program to play an Audio
// file using Clip Object
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.scene.media.*;

public class AudioManager
{
	static void play(String name, boolean loop) {
		String sep= FileSystems.getDefault().getSeparator();
		Path path= Paths.get("src"+sep+"audioFiles"+sep+name).toAbsolutePath();
		MediaPlayer m= new MediaPlayer(new Media(path.toUri().toString()));
		if(loop) m.setCycleCount(MediaPlayer.INDEFINITE);
		m.play();
	}

}
