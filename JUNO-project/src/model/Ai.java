package model;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Ai extends Entity{

	public Ai(String nickname) {
		super(nickname);
	}
	
	public Card play(Card discard, Deck deck) throws EmptyDeckException {

		//restituisce una lista di carte giocabili secondo le regole
		ArrayList<Card> playable=(ArrayList<Card>) this.HAND.stream().filter((x)->x.getColor().equals(discard.getColor())||
				x.getColor()==Color.BLACK||x.VALUE.equals(discard.VALUE)).collect(Collectors.toList());
			
		if (playable.isEmpty()) {
			this.drawFrom(deck);
			Card drawn= this.HAND.get(HAND.size()-1);
			if (drawn.getColor().equals(discard.getColor())|| drawn.getColor()==Color.BLACK||drawn.VALUE.equals(discard.VALUE)) return drawn;
			else return null;
		}

		else {
			return playable.get(0);
		}
	}

}
