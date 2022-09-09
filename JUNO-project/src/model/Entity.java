package model;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.util.ArrayList;
/**
 * A class that represents a generic player that could be human or artificial
 */
public abstract class Entity {
	/**
	 * The name of the player
	 */
	private String nickname;
	/**
	 * Path of an image that will be used for the profile picture of a player
	 */
	private String avatar;
	/**
	 * An ArrayList of Card objects that represents the cards in the hand of a player
	 */
	public final ArrayList<Card> HAND = new ArrayList<>();

	/**
	 * Initializes an Entity object with a nickname and an avatar whit the default.jpg image file path
	 * @param nickname
	 */
	Entity (String nickname)  {
		this.nickname=nickname;
		String sep= FileSystems.getDefault().getSeparator();
		avatar= Paths.get("src"+sep+"profilePic"+sep+"default.jpg").toAbsolutePath().toString();
	}
	
	public String getNickname() {
		return nickname;
	}
	
	public void setNickname(String nickname) {
		this.nickname=nickname;
	}

	public String getAvatar() {
		return avatar;
	}
	
	//inserire il nome dell'immagine avatar senza il tipo del file (es. ".jpg")
	/**
	 * Sets the avatar field with the path and name of an image file in the directory profilePic 
	 * @param avatar
	 */
	public void setAvatar(String avatar) {
		this.avatar=avatar;
	}
	
	/*public String getAvatarPath() {
		String sep= FileSystems.getDefault().getSeparator();
		return Paths.get("src"+sep+"savedProfiles"+sep+avatar+".jpg").toAbsolutePath().toString();
	}*/
	/**
	 * Puts a Card from the Deck in the HAND of the Entity instance
	 */
	void drawFrom() {
		HAND.add(Deck.getInstance().draw());
	}
	/**
	 * Puts n Cards from the Deck in the HAND of the Entity instance
	 */
	void drawFrom(int n) {
		for (int i=0; i<n; i++) drawFrom();
	}

}
