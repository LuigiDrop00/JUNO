package model;

import java.util.Collections;
import java.util.LinkedList;

/** a class that defines a deck to play the UNO card game **/
public class Mazzo {

	private static Mazzo istanza;
	private LinkedList<Carta> mazzo = new LinkedList<>();

	public static Mazzo getInstance() {
		if (istanza == null)
			istanza = new Mazzo();
		return istanza;
		
	}

	private Mazzo() {
		// VALORI ENUM
		Colore[] colori = Colore.values();
		Valore[] valori = Valore.values();
		
		// --------GENERAZIONE CARTE COMUNI-----
		for (int i = 0; i < 4; i++) {
			// indice dei valori contenuti nelle carte
			int iValori = 1;

			for (int j = 0; j < 19; j++) {
				// se siamo arrivati all'undicesimo elemento(CAMBIAGIRO) usciamo dal ciclo
				mazzo.add(new Carta(colori[i], valori[iValori % 10]));
				iValori++;
			}
			// AGGIUNTA CARTE SPECIALI NON NERE
			mazzo.add(new Carta(colori[i], Valore.PIUDUE));
			mazzo.add(new Carta(colori[i], Valore.PIUDUE));
			mazzo.add(new Carta(colori[i], Valore.CAMBIAGIRO));
			mazzo.add(new Carta(colori[i], Valore.CAMBIAGIRO));
			mazzo.add(new Carta(colori[i], Valore.STOP));
			mazzo.add(new Carta(colori[i], Valore.STOP));
		}

		// ---------GENERAZIONE CARTE NERE(jolly)
		mazzo.add(new Carta(Colore.NERO, Valore.CAMBIACOLORE));
		mazzo.add(new Carta(Colore.NERO, Valore.CAMBIACOLORE));
		mazzo.add(new Carta(Colore.NERO, Valore.CAMBIACOLORE));
		mazzo.add(new Carta(Colore.NERO, Valore.CAMBIACOLORE));
		mazzo.add(new Carta(Colore.NERO, Valore.PIUQUATTRO));
		mazzo.add(new Carta(Colore.NERO, Valore.PIUQUATTRO));
		mazzo.add(new Carta(Colore.NERO, Valore.PIUQUATTRO));
		mazzo.add(new Carta(Colore.NERO, Valore.PIUQUATTRO));

<<<<<<< Updated upstream
		
		stampaMazzo();
	}
	public void stampaMazzo() {
		int nCarte = 1;
		for(Carta c: mazzo) {
			
			System.out.println(c.VALORE + " " + c.COLORE + nCarte++);
		}
			
=======
		// stampaMazzo();
	}
	public void mischiaMazzo(){
		Collections.shuffle(mazzo);
	}

	public Carta pescaCarta(){
		return mazzo.pop();
>>>>>>> Stashed changes
	}
	/** checks whether the deck is empty or not **/
	public boolean isEmpty() {
		return mazzo.size() == 0;
	}

}
