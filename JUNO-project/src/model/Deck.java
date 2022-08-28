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
			deck.add(new Card(colori[i],Value.DRAW2));
			deck.add(new Card(colori[i],Value.DRAW2));
			deck.add(new Card(colori[i],Value.REVERSE));
			deck.add(new Card(colori[i],Value.REVERSE));
			deck.add(new Card(colori[i],Value.SKIP));
			deck.add(new Card(colori[i],Value.SKIP));
		}
		//NUMERO CARTE SPECIALI
		
		//---------GENERAZIONE CARTE SPECIALI
		deck.add(new Card(Color.BLACK, Value.CHANGE));
		deck.add(new Card(Color.BLACK, Value.CHANGE));
		deck.add(new Card(Color.BLACK, Value.CHANGE));
		deck.add(new Card(Color.BLACK, Value.CHANGE));
		deck.add(new Card(Color.BLACK, Value.DRAW4));
		deck.add(new Card(Color.BLACK, Value.DRAW4));
		deck.add(new Card(Color.BLACK, Value.DRAW4));
		deck.add(new Card(Color.BLACK, Value.DRAW4));

		
		//stampaMazzo();
	}
	Card draw(){
		if (deck.isEmpty()) {
			Card last= Game.pile.pop();
			refill(Game.pile);
			Game.pile.addFirst(last);
		}
		return deck.pop();
	}
	//usata solo da refill?
	public void shuffleDeck(){
		Collections.shuffle(deck);
	}
	/** checks whether the deck is empty or not **/
	public boolean isEmpty() { 
		return deck.size() == 0;
	}
	
	public void refill(List<Card> pile) {
		while(!pile.isEmpty()) {
			Card card=pile.get(0);
			pile.remove(0);
			if (card.VALUE==Value.CHANGE || card.VALUE==Value.DRAW4) card.setColor(Color.BLACK);
			deck.add(card);
		}
		shuffleDeck();
	}
	
}
