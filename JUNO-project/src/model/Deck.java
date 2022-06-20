package model;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/** a class that defines a deck to play the UNO card game**/
public class Deck {
	
	private static Deck istanza;
	public static Deck getInstance() {
		if(istanza == null) istanza = new Deck();
		return istanza;
	}
	
	private LinkedList<Card> deck = new LinkedList<>();
	
	private Deck(){
		Color[] colori = Color.values();
		Value[] valori = Value.values();
		
		//--------GENERAZIONE CARTE COMUNI-----
		for (int i = 0; i < 4; i++) {
			//indice dei valori contenuti nelle carte
			int iValori = 1;
			
			for(int j = 0; j < 19 ; j++) {
				//se siamo arrivati all'undicesimo elemento(CAMBIAGIRO) usciamo dal ciclo
				deck.add(new Card(colori[i],valori[iValori%10]));
				iValori++;
			}
			deck.add(new Card(colori[i],Value.PIUDUE));
			deck.add(new Card(colori[i],Value.PIUDUE));
			deck.add(new Card(colori[i],Value.CAMBIAGIRO));
			deck.add(new Card(colori[i],Value.CAMBIAGIRO));
			deck.add(new Card(colori[i],Value.STOP));
			deck.add(new Card(colori[i],Value.STOP));
		}
		//NUMERO CARTE SPECIALI
		
		//---------GENERAZIONE CARTE SPECIALI
		deck.add(new Card(Color.NERO, Value.CAMBIACOLORE));
		deck.add(new Card(Color.NERO, Value.CAMBIACOLORE));
		deck.add(new Card(Color.NERO, Value.CAMBIACOLORE));
		deck.add(new Card(Color.NERO, Value.CAMBIACOLORE));
		deck.add(new Card(Color.NERO, Value.PIUQUATTRO));
		deck.add(new Card(Color.NERO, Value.PIUQUATTRO));
		deck.add(new Card(Color.NERO, Value.PIUQUATTRO));
		deck.add(new Card(Color.NERO, Value.PIUQUATTRO));

		
		//stampaMazzo();
	}
	public Card draw() throws EmptyDeckException{
		return deck.pop();
	}
	
	public void shuffleDeck(){
		Collections.shuffle(deck);
	}
	/** checks whether the deck is empty or not **/
	public boolean isEmpty() { 
		return deck.size() == 0;
	}
	
}
