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
				playerDraw(this,4);
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
    static public Entity[] players;
    private GameOverConditions gameOverConditions;
    public void setGameMode(String mode) {
    	switch(mode) {
    	case "classic": gameOverConditions = new GameOverConditionsClassic() {}; break;
    	case "teams": gameOverConditions = new GameOverConditionsTeam() {}; break;
    	case "caos":  gameOverConditions = new GameOverConditionsChaos() {}; break;
    	}
    }

    final static private Deck deck = Deck.getInstance();
    final static public LinkedList<Card> pile= new LinkedList<Card>();
    private boolean uno=false;
    /**
     * Checks if player has not drawn a card this turn */
    boolean hasPlayerNotDrawn=true;
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
    	hasPlayerNotDrawn = true;
    	if(isClockwise) return increaseTurn();
    	else return previousTurn();
    }
    
    /** in order to be played, a game needs a deck,
     *  4 Players a pile of folded card the order of pl
     *  */
    public Game()   {   	
    	//trasferisce tutte le carte dalla pila al mazzo e mischia il mazzo
		deck.refill(pile);	
        p1=LoginState.getLoggedPlayer();		
		players= new Entity[]{p1, new Ai("AI1"),new Ai("AI2"),new Ai("AI3")};
		for (Entity e:players) {
			//resetta le mani dei giocatori
			e.HAND.removeAll(e.HAND);
			//i giocatori pescano le carte iniziali
			e.drawFrom(7);
		}
		Card first=deck.draw();
		pile.addFirst(first);
		if (first.VALUE!=Value.DRAW4) cardEffect(first);
    }
    
    public void aiTurn() {
    	Card discarded=pile.get(0);
    	while (getTurn()!=0) {
    		Ai ai=(Ai) players[getTurn()];
    		ai.play(discarded);
    		if (ai.HAND.size()==1) {
				setChanged();
				notifyObservers("Uno");
    		}
    		discarded=pile.get(0);
    		if(pass()) increaseTurn();
    	}
    }
    
	private void cardEffect(Card playedCard) {
		switch(playedCard.VALUE) {
		case REVERSE:	isClockwise= !isClockwise; break;
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
    	if (getTurn()!=0) playedCard.setColor(Color.values() [(int) (5*Math.random())]);
    	else {
    		setChanged();
			notifyObservers("ChangeColor");
    	}
    }
	
	public void playerDraw(Entity e, int n) {
		if (hasPlayerNotDrawn) {
			for (int i=0; i<n; i++) {
				e.drawFrom();
				setChanged();
				notifyObservers("Draw");}
		}
		hasPlayerNotDrawn=false;
	}
	
	public boolean pass() {
		if(gameOverConditions.conditions()=="Loss") {
    			loss();
				setChanged();
				notifyObservers("Loss");
				return false;
    		}
		else if(gameOverConditions.conditions()=="Win") {
			win();
			setChanged();
			notifyObservers("Win");
			return false;
			}
		else return true;
		}
	
	public void playerPlay(Card discard) {
		Card drawn= pile.get(0);
		//TODO creare un metodo cartaGiocabile? per sostituire sta roba nell'if
		if (drawn.getColor().equals(discard.getColor())|| discard.getColor()==Color.BLACK||drawn.VALUE.equals(discard.VALUE)) {
			if (p1.HAND.size()==2 && uno==false) {				
				setChanged();
				notifyObservers("NoUno");
				playerDraw(p1,2);
			}
			//scarta la carta
			p1.HAND.remove(discard);
			pile.addFirst(discard);
			setChanged();
			notifyObservers("Play");
			//l'effetto della carta si attiva
			cardEffect(discard);
			uno=false;
			//passa il turno se la partita non è finita
			if(pass()) aiTurn();	//if discard=cambia colore non lo chiamare
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

	private void win() { 	
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
			p1.victoriesUp(); }
	
	private void loss() {
			p1.expUp(20);
			p1.lossesUp();
	}

}