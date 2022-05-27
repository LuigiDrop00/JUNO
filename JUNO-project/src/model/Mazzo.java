package model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/** a class that defines a deck to play the UNO card game**/
public class Mazzo {
	
	private static Mazzo istanza;
	public static Mazzo getInstance() {
		if(istanza == null) istanza = new Mazzo();
		return istanza;
	}
	
	List<Carta> mazzo = new LinkedList<>();
	
	private Mazzo(){
		Colore[] colori = Colore.values();
		Valore[] valori = Valore.values();
		
		//--------GENERAZIONE CARTE COMUNI-----
		for (int i = 0; i < 4; i++) {
			//indice dei valori contenuti nelle carte
			int iValori = 1;
			
			for(int j = 0; j < 19 ; j++) {
				//se siamo arrivati all'undicesimo elemento(CAMBIAGIRO) usciamo dal ciclo
				mazzo.add(new Carta(colori[i],valori[iValori%10]));
				iValori++;
			}
			mazzo.add(new Carta(colori[i],Valore.PIUDUE));
			mazzo.add(new Carta(colori[i],Valore.PIUDUE));
			mazzo.add(new Carta(colori[i],Valore.CAMBIAGIRO));
			mazzo.add(new Carta(colori[i],Valore.CAMBIAGIRO));
			mazzo.add(new Carta(colori[i],Valore.STOP));
			mazzo.add(new Carta(colori[i],Valore.STOP));
		}
		//NUMERO CARTE SPECIALI
		
		//---------GENERAZIONE CARTE SPECIALI
		mazzo.add(new Carta(Colore.NERO, Valore.CAMBIACOLORE));
		mazzo.add(new Carta(Colore.NERO, Valore.CAMBIACOLORE));
		mazzo.add(new Carta(Colore.NERO, Valore.CAMBIACOLORE));
		mazzo.add(new Carta(Colore.NERO, Valore.CAMBIACOLORE));
		mazzo.add(new Carta(Colore.NERO, Valore.PIUQUATTRO));
		mazzo.add(new Carta(Colore.NERO, Valore.PIUQUATTRO));
		mazzo.add(new Carta(Colore.NERO, Valore.PIUQUATTRO));
		mazzo.add(new Carta(Colore.NERO, Valore.PIUQUATTRO));

		
		stampaMazzo();
	}
	public void stampaMazzo() {
		int nCarte = 1;
		for(Carta c: mazzo) {
			
			System.out.println(c.VALORE + " " + c.COLORE + nCarte++);
		}
			
	}
	
	/** checks whether the deck is empty or not **/
	public boolean isEmpty() { 
		return mazzo.size() == 0;
	}
	
}
