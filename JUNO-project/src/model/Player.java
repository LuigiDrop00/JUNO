package model;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.File;
import java.io.Serializable;

public class Player extends Entity implements Serializable {
	
	private int partite;
	private int vittorie;
	private static final long serialVersionUID = 145145314134l;
	
	public void save() throws IOException {
		
		File f= new File("C:\\Users\\ilyas\\Desktop\\JUNO\\JUNO-project\\src\\savedProfiles\\save.txt");

		FileOutputStream fos= new FileOutputStream(f.getAbsolutePath());
		ObjectOutputStream os= new ObjectOutputStream(fos); 
		
		
		
	}
	
	public static void main(String[] args) {
		
		File f= new File("C:\\Users\\ilyas\\Desktop\\JUNO\\JUNO-project\\src\\savedProfiles\\save.txt");
		System.out.print(f.getAbsolutePath());
		
	}

}
