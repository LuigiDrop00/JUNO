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
	MediaPlayer audio;
	
	AudioManager(String name){
		String sep= FileSystems.getDefault().getSeparator();
		Path path= Paths.get("src"+sep+"audioFiles"+sep+name).toAbsolutePath();
		audio= new MediaPlayer(new Media(path.toUri().toString()));
	}
	
	void play (boolean loop) {
		if (loop) {
			audio.setVolume(0.2);
			audio.setCycleCount(MediaPlayer.INDEFINITE);
		}
		else  audio.setOnEndOfMedia(this::stop);
		audio.play();
	}
	
	void stop() {
		audio.stop();
	}
}
