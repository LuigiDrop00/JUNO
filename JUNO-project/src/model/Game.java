package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Observable;
import java.util.function.Function;
import java.util.stream.Collectors;

/** a class that represents a game and its features */
public class Game extends Observable {
	
	private class Ai extends Entity{

		public Ai(String nickname) {
			super(nickname);
		}
	
		public void play(Card discard, Deck deck) {

			//restituisce una lista di carte giocabili secondo le regole
			ArrayList<Card> playable=(ArrayList<Card>) this.HAND.stream().filter((x)->x.getColor().equals(discard.getColor())||
					x.getColor()==Color.BLACK||x.VALUE.equals(discard.VALUE)).collect(Collectors.toList());
				
			if (playable.isEmpty()) {
					this.drawFrom();
					setChanged();
					notifyObservers("aiDraw"+turn);
				Card drawn= this.HAND.get(HAND.size()-1);
				if (drawn.getColor().equals(discard.getColor())|| drawn.getColor()==Color.BLACK||drawn.VALUE.equals(discard.VALUE)) {
					cardEffect(drawn);
		    		pile.addFirst(drawn);
					setChanged();
					notifyObservers("aiPlay"+turn);
				}
				else {
					setChanged();
					notifyObservers("aiPass"+turn);
				}
			}

			else {
				cardEffect(playable.get(0));
	    		pile.addFirst(playable.get(0));
				setChanged();
				notifyObservers("aiPlay"+turn);
			}
		}
	}

    private Player p1;
    final public Entity[] players= {p1, new Ai("AI1"),new Ai("AI2"),new Ai("AI3")};

    final static private Deck deck = Deck.getInstance();
    final static public LinkedList<Card> pile= new LinkedList<Card>();
    private boolean uno=false;

    /** sets whether the order of players turns is to be decided in a clockwise direction or not.
     *The value is true for Clockwise, false for Counterclockwise */
    private boolean isClockwise = true;
    private int turn = 0;
    public int getTurn() {
    	return turn % 4;
    }
    private int increaseTurn(){
        if (isClockwise)	return ++turn % 4;
        else	return --turn % 4;
    }
    /** in order to be played, a game needs a deck,
     *  4 Players a pile of folded card the order of pl
     *  */
    public Game()   {   	
    	//mischia il mazzo
        deck.shuffleDeck();
        //TODO cardEffect?
        p1=LoginState.getLoggedPlayer();
		pile.addFirst(deck.draw());
		for (Entity e:players) {
			e.drawFrom(7);
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
    
    public void aiTurn() {
    	Card discarded=pile.get(0);
    	while (increaseTurn()!=0) {
    		
    		players[turn].play(discarded, deck);
    		discarded=pile.get(0);

    	}
    	//playerTurn=true;
    }
    
    private void cardEffect(Card playedCard) {
		switch(playedCard.VALUE) {
		case REVERSE:	isClockwise= !isClockwise; break;
		case SKIP:	increaseTurn(); break;
		case DRAW2:	players[increaseTurn()].drawFrom(2); break;
		case DRAW4:	players[increaseTurn()].drawFrom(4);     
			changeColor(playedCard); break;
		case CHANGE:	changeColor(playedCard); break;
		
		}		
	}
    
    private void changeColor(Card playedCard) {
    	if (turn==0) {
			setChanged();
			notifyObservers("changeColor"); //TODO far scegliere il colore al giocatore nella view
    	}
    	else playedCard.setColor(Color.values() [(int) (5*Math.random())]);
    }
    
    //TODO far chiamare questo metodo quando il giocatore preme il pulsante passaTurno o dopo che gioca una carta
	public void playerEndTurn() {
		increaseTurn();
    	aiTurn();
    }
	
	public void playerDraw() {
			p1.drawFrom();
			setChanged();
			notifyObservers("playerDraw");
	}
	
	public void gameOver() {  //TODO capire da dove chiamarlo
		deck.refill(pile);	
		if (p1.HAND.size()==0) {
			for (int i=1; i<4; i++) p1.expUp(players[i].HAND.stream().map(new Function<Card,Integer>(){
				@Override
				public Integer apply(Card t) {
					switch(t.VALUE) {
					case DRAW2: case SKIP: case REVERSE: return 20;
					case CHANGE: case DRAW4: return 50;
					default:	return t.VALUE.toInt();	
					}
				} 	
			}).reduce((x,y)->x+y).get());
		}
		else p1.expUp(20);
	}


}