package model;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public abstract class Entity {
	
	private String nickname;

	private String avatar;
	private int level;
	private final ArrayList<Carta> HAND = new ArrayList<>();

	//TODO
	//private Path avatar;

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
	public void setAvatar(String avatar) {
		this.avatar=avatar;
	}
	
	public String getAvatarPath() {
		String sep= FileSystems.getDefault().getSeparator();
		return Paths.get("src"+sep+"savedProfiles"+sep+avatar+".jpg").toAbsolutePath().toString();
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}




}
