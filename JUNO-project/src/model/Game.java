package model;

import java.util.LinkedList;
import java.util.Observable;

/** a class that rapresents a game and its features */
public class Game extends Observable {

    private Player p1; 
    private Ai p2;
    private Ai p3;
    private Ai p4;

    private Deck deck = Deck.getInstance();
    //private List<Card> pila;
    private Card discarded;
    private int toDraw; 

    /** sets whether the order of players turns is to be decided in a clockwise direction or not.
     *The value is 1 for Clockwise, -1 for Counterclockwise */
    private int isClockwise = 1;
    private int turn = 0;
    private int getTurn(){
        return ++turn % 4;
    }
    /** in order to be played, a game needs a deck,
     *  4 Players a pile of folded card the order of pl
     *  */
    public Game()   {
        //imposta il gicatore umano
        // this.p1 = player;

      /*  p1 = Player.load("p1", "123");
        p2 = Player.load("p2", "123");
        p3 = Player.load("p3", "123");
        p4 = Player.load("p4", "123");*/
    	
    	//mischia il mazzo
        deck.shuffleDeck();
        try {
			discarded=deck.draw();
		} catch (EmptyDeckException e) {
			e.printStackTrace();
		}
        /*
           1. Partita generata, giocatori caricati(addObserver),
             mazzo/scarti creato, giroOrario(ordine dei turni con id)
            2.ogni turno viene inviata una notifica dalla partita con id
                eventi (pesca carte, cambiacolore, cambiagtiro, saltaTurno)
                GIOCATORE CON DUE CARTE - UNO prima di scartare la seconda carta
            3. gameOver() assegnazione exp, partite vinte/perse/giocate ritorna a menu
                principale
        */

    }
    
    public void aiTurn() throws EmptyDeckException {
    	Card playedCard=new Card(null, null);
    	while (getTurn()!=0) {
    		
    		switch(getTurn()) {
    		case 1:	playedCard=p2.play(discarded, deck); break;
    		case 2:	playedCard=p3.play(discarded, deck); break;
    		case 3:	playedCard=p4.play(discarded, deck); break;
    		}
    		
    		cardEffect(playedCard);
    		discarded=playedCard;
    	}
    }
    
    private void cardEffect(Card playedCard) {
		switch(playedCard.VALUE) {
		case REVERSE:	isClockwise*=-1; break;
		case SKIP:	turn+=isClockwise; break;
		case DRAW2:	toDraw+=2;	turn+=isClockwise; break;
		case DRAW4:	toDraw+=4;	turn+=isClockwise;     //TODO aggiungere argomento toDraw ad Entity()
			playedCard.setColor(Color.values() [(int) (5*Math.random())]); break;
		case CHANGE:	playedCard.setColor(Color.values() [(int) (5*Math.random())]); break;
		}
		
	}
	public void playerTurn() {
    	
    }


}