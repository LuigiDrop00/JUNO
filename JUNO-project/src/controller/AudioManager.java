package controller;
// Java program to play an Audio
// file using Clip Object
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.scene.media.*;
/**
 * Class that manages and plays audio files
 */
public class AudioManager
{
	MediaPlayer audio;
	/**
	 * Initializes an AudioManager object from a path of an audio file inside the directory audioFiles 
	 * @param name
	 */
	AudioManager(String name){
		String sep= FileSystems.getDefault().getSeparator();
		Path path= Paths.get("src"+sep+"audioFiles"+sep+name).toAbsolutePath();
		audio= new MediaPlayer(new Media(path.toUri().toString()));
	}
	/**
	 * Plays the audio file. If loop is true it will loop, otherwise it will stop after it's played one time.
	 * @param loop
	 */
	void play (boolean loop) {
		if (loop) {
			audio.setVolume(0.2);
			audio.setCycleCount(MediaPlayer.INDEFINITE);
		}
		else  audio.setOnEndOfMedia(this::stop);
		audio.play();
	}
	/**
	 * Stops the audio if it's playing
	 */
	void stop() {
		audio.stop();
	}
}
