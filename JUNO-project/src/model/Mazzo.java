package model;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/** a class that defines a deck to play the UNO card game**/
public class Mazzo {
	
	private static Mazzo istanza;
	
	private Mazzo(){
		mazzo.addAll(generaCarteComuni());
		//mazzo.addAll(generaCarteComuni());
	}
	
	private Collection<Carta> generaCarteComuni() {
		//int numCartePerColore = 19;
		Collection<Carta> carte = new HashSet<>();
		for (Colore colore : Colore.values()) {
			for (Valore valore : Valore.values()) {
				
			}
		}
		
		return carte;
		
	}
	private void generaCarteSpeciali() {}
	
	List<Carta> mazzo = new LinkedList<>();
	
	public static Mazzo getInstance() {
		if(istanza == null) istanza = new Mazzo();
		return istanza;
	}
	/** checks whether the deck is empty or not **/
	public boolean isEmpty() { 
		return mazzo.size() == 0;
	}
	
}
