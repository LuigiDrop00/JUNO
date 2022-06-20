package model;

import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.IOException;

import java.io.ObjectInputStream;

import java.io.ObjectOutputStream;
import java.nio.file.*;



public class Player extends Entity implements java.io.Serializable {
	
	private String password;
	private int games;
	private int victories;
	private int exp;
	private static final long serialVersionUID = 2451423441245l;
	
	public Player(String nickname, String password){
		super(nickname);
		this.password=password;
	}
	
	public int getLevel() {
		return (int) (Math.log(exp/10 +2)/Math.log(2));
	}
	
	public void expUp(int up) {
		exp+=up;
	}
	
	public Player create() {
		
		String sep= FileSystems.getDefault().getSeparator();
		Path path= Paths.get("src"+sep+"savedProfiles"+sep+getNickname()+".txt").toAbsolutePath();
		
		try {
			
			Files.createFile(path);

			FileOutputStream fos= new FileOutputStream(path.toString());
			ObjectOutputStream os= new ObjectOutputStream(fos); 
			
			os.writeObject(password);
			os.writeObject(getNickname());
			os.writeObject(getAvatar());
			os.writeObject(exp);
			os.writeObject(games);
			os.writeObject(victories);
			os.close();
			
			return this;
			}
		
		//createFile() scatena un'eccezione quando il file giï¿½ esiste
		catch (IOException e) {		
			return null;
		}
			
	}
	
	public static Player load(String nickname, String password) throws IncorrectPasswordException, SaveNotFoundException {
		
		String sep= FileSystems.getDefault().getSeparator();
		String path= Paths.get("src"+sep+"savedProfiles"+sep+nickname+".txt").toAbsolutePath().toString();
		
		try {
		FileInputStream fis= new FileInputStream(path);
		ObjectInputStream is = new ObjectInputStream(fis);
		
		String passwordCorrect = (String) is.readObject();
		if (!passwordCorrect.equals(password)) throw new IncorrectPasswordException();
		
		nickname = (String) is.readObject();
		String avatar = (String) is.readObject();
		int exp = (int) is.readObject();
		int games = (int) is.readObject();
		int victories = (int) is.readObject();
		
		is.close();
		
		Player player = new Player(nickname, password);
		player.setAvatar(avatar);
		player.exp=exp;
		player.games=games;
		player.victories=victories;
		
		return player;
		}

		catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		catch (IOException e) {
			throw new SaveNotFoundException();
		}
	}
	
	public void save() throws SaveNotFoundException {
		
		String sep= FileSystems.getDefault().getSeparator();
		Path path= Paths.get("src"+sep+"savedProfiles"+sep+getNickname()+".txt").toAbsolutePath();		
		if (Files.exists(path)) {
			try {
				Files.delete(path);
			} catch (IOException e) {   //questo errore capita solo se c'è un interruzione inaspettata
				e.printStackTrace();
			}
			this.create();
		}
		else throw new SaveNotFoundException();
	}
	
/*	 public static void main(String[] args) {
		

		
        ClassLoader loader = Player.class.getClassLoader();
        java.net.URL url = loader.getResource("model/Player.class");
        
        System.getProperty("user.dir");
       
        String sep= FileSystems.getDefault().getSeparator();
        Path path= Paths.get("src"+sep+"savedProfiles"+sep+"save.txt").toAbsolutePath();		
        System.out.println(path);
        
        Player p= new Player("bo","big");
        System.out.println(p.create());
        
        Player p2 = null;
		try {
			p2 = Player.load("bo","big");
		} catch (IncorrectPasswordException | SaveNotFoundException e) {
			e.printStackTrace();
		}
		p2.expUp(59);
        System.out.println(p2.getLevel());
        
	} */

}
