package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;
import java.util.function.Function;
import java.util.stream.Collectors;

/** a class that represents a game and its features */
@SuppressWarnings("deprecation")
public class Game extends Observable {
	
	private class Ai extends Entity{

		public Ai(String nickname) {
			super(nickname);
		}
		@Override
		public void play(Card discard) {

			//restituisce una lista di carte giocabili secondo le regole
			ArrayList<Card> playable=(ArrayList<Card>) this.HAND.stream().filter((x)->x.getColor().equals(discard.getColor())||
					x.getColor()==Color.BLACK||x.VALUE.equals(discard.VALUE)).collect(Collectors.toList());
				
			if (playable.isEmpty()) {
					this.drawFrom();
					setChanged();
					notifyObservers("Draw");
				Card drawn= this.HAND.get(HAND.size()-1);
				if (drawn.getColor().equals(discard.getColor())|| drawn.getColor()==Color.BLACK||drawn.VALUE.equals(discard.VALUE)) {
		    		pile.addFirst(drawn);
		    		HAND.remove(drawn);
					setChanged();
					notifyObservers("Play");
					cardEffect(drawn);
				}
				else {
					setChanged();
					notifyObservers("Pass");
				}
			}
			else {
	    		pile.addFirst(playable.get(0));
	    		HAND.remove(playable.get(0));
				setChanged();
				notifyObservers("Play");
				cardEffect(playable.get(0));
			}
		}
	}

    private Player p1;
    final public Entity[] players;
    private GameMode mode;
    
    public void setGameMode(String mode) {
    	/*mode= switch(mode) {
    	case "classic":  GameMode::toString;
    	};*/
    }

    final static public Deck deck = Deck.getInstance();
    final static public LinkedList<Card> pile= new LinkedList<Card>();
    private boolean uno=false;

    /** sets whether the order of players turns is to be decided in a clockwise direction or not.
     *The value is true for Clockwise, false for Counterclockwise */
    private boolean isClockwise = true;
    
	int turn = 0;
    public int getTurn() {	
    	return turn;
    }
    private int increaseTurn(){
    	if (turn+1 >= 4) return turn = 0;
        else return ++turn;
    }
    private int previousTurn() {
    	if (turn-1 <= -1) return turn = 3;
        else return --turn;
    }
    private int changeTurn() {
    	if(isClockwise) return increaseTurn();
    	else return previousTurn();
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
		players= new Entity[]{p1, new Ai("AI1"),new Ai("AI2"),new Ai("AI3")};
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
    
  //TODO far chiamare questo metodo quando il giocatore preme il pulsante passaTurno o dopo che gioca una carta
    public void aiTurn() {
    	Card discarded=pile.get(0);
    	while (changeTurn()!=0) {
    		Ai ai=(Ai) players[getTurn()];
    		ai.play(discarded);
    		if (ai.HAND.size()==1) {
				setChanged();
				notifyObservers("Uno");
    		}
    		if (ai.HAND.size()==0) {
    			gameOver();
				setChanged();
				notifyObservers("Loss");
				break;
    		}
    		discarded=pile.get(0);
    	}
    }
    
    private void cardEffect(Card playedCard) {
		switch(playedCard.VALUE) {
		case REVERSE:	isClockwise= !isClockwise; break;
		//TODO 
		case SKIP:	changeTurn(); break;
		case DRAW2:	playerDraw(players[changeTurn()],2); break;
		case DRAW4:	playerDraw(players[changeTurn()],4);     
			changeColor(playedCard); break;
		case CHANGE:	changeColor(playedCard); break;
		default:
			System.out.println("CARTA NORMALE");
			break;
		}		
	}
    
    private void changeColor(Card playedCard) {
    	if (getTurn()!=0) playedCard.setColor(Color.values() [(int) (5*Math.random())]); //TODO notify?
    }
	
	public void playerDraw(Entity e, int n) {
		for (int i=0; i<n; i++) {
			e.drawFrom();
			setChanged();
			notifyObservers("Draw");}
	}
	
	public void playerPlay(Card discard) {
		Card drawn= pile.get(0);
		if (drawn.getColor().equals(discard.getColor())|| discard.getColor()==Color.BLACK||drawn.VALUE.equals(discard.VALUE)) {
			if (p1.HAND.size()==2 && uno==false) {				
				setChanged();
				notifyObservers("NoUno");
				playerDraw(p1,2);
			}
			p1.HAND.remove(discard);
			pile.addFirst(discard);
			setChanged();
			notifyObservers("Play");
			cardEffect(discard);
    		if (p1.HAND.size()==0) {
    			gameOver();
				setChanged();
				notifyObservers("Win");
    		}	
    		else	aiTurn();
    		uno=false;
		}	
		else {
			setChanged();
			notifyObservers("InvalidPlay");
		}	
	}
	
	public void playerPlay(Card discard, Color color) {
		playerPlay(discard);
		discard.setColor(color);
	}
	
	
	
	public void pressUno() {
		if (p1.HAND.size()==2 && uno==false && getTurn()==0) {
			uno=true;       
			setChanged();
			notifyObservers("Uno");
		}
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
			p1.victoriesUp();
		}
		else {
			p1.expUp(20);
			p1.lossesUp();
		}
	}


}