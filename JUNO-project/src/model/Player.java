package model;

import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.IOException;

import java.io.ObjectInputStream;

import java.io.ObjectOutputStream;
import java.nio.file.*;
/**
 * Class that represents an human player and their account
 */
public class Player extends Entity implements java.io.Serializable {
	/**
	 * The password of the account associated with the nickname of the Player
	 */
	private String password;
	/**
	 * The number of Games lost
	 */
	private int losses;
	/**
	 * The number of Games won
	 */
	private int victories;
	/**
	 * The number of experience points gained 
	 */
	private int exp;
	private static final long serialVersionUID = 2451423441245l;
	
	public Player(String nickname, String password){
		super(nickname);
		this.password=password;
	}
	/**
	 * Returns the level of the Player calculated with a function with the number of exp as a variable
	 * @return
	 */
	public int getLevel() {
		return (int) (Math.log(exp/10 +2)/Math.log(2));
	}
	public int getExp() {
		return exp;
	}
	/**
	 * Adds up exp
	 * @param up
	 */
	public void expUp(int up) {
		exp+=up;
	}
	/**
	 * Changes the nickname and updates the saved profile
	 */
	@Override
	public void setNickname(String nickname) {
		
		String sep= FileSystems.getDefault().getSeparator();
		Path path= Paths.get("src"+sep+"savedProfiles"+sep+getNickname()+".txt").toAbsolutePath();	
		try {
			Files.delete(path);
		} catch (IOException e) {	//questo errore capita solo se c'è un interruzione inaspettata
			e.printStackTrace();
		}
		
		super.setNickname(nickname);
		this.create();
	}
	/**
	 * Creates a text file in savedProfiles to save the Player object in, with the name as the nickname of the Player
	 * @return
	 */
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
			os.writeObject(losses);
			os.writeObject(victories);
			os.close();
			
			return this;
			}
		
		//createFile() scatena un'eccezione quando il file giï¿½ esiste
		catch (IOException e) {		
			return null;
		}
			
	}
	/**
	 * Loads and returns a Player object from a text file named after in which it was saved. 
	 * Also checks if the password in input is the same password used for the Player object with the same nickname as the one in input.
	 * @param nickname
	 * @param password
	 * @return
	 * @throws IncorrectPasswordException
	 * @throws SaveNotFoundException
	 */
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
		player.losses=games;
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
	/**
	 * Saves the changes to an already created text file in which the Player object is saved in
	 * @throws SaveNotFoundException
	 */
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

	public int getLosses() {
		return losses;
	}
	/**
	 * Increases losses field by 1
	 */
	void lossesUp() {
		++losses;
	}
	
	public int getVictories() {
		return victories;
	}
	/**
	 * Increases wins field by 1
	 */
	void victoriesUp() {
		++victories;
	}
	/**
	 * 
	 * Return the number of total Games played
	 * @return
	 */
	public int getGames() {
		return losses+victories;
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
