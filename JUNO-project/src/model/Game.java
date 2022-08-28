package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;
import java.util.function.*;
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
				playerDraw(this,1);
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
    private ValidPlay validPlay;
   // private Supplier p= gameOverConditions::conditions;
    public void setGameMode(String mode) {
    	switch(mode) {
    	case "classic": gameOverConditions = new GameOverConditionsClassic() {}; validPlay = new ValidPlayClassic() {}; break;
    	case "teams": gameOverConditions = new GameOverConditionsTeam() {}; validPlay = new ValidPlayClassic() {}; break;
    	case "chaos":  gameOverConditions = new GameOverConditionsClassic() {}; validPlay = new ValidPlayChaos() {};  break;
    	}
    }

    final static private Deck deck = Deck.getInstance();
    final static public LinkedList<Card> pile= new LinkedList<Card>();
    /**
     * Checks if player has pressed the uno button this turn */
    private boolean uno = false;
    int counter2;
    /**
     * Checks if player has played a card this turn */
    boolean hasPlayerPlayed = false;
    /**
     * Checks if player has drawn a card this turn */
    boolean hasPlayerDrawn = false;
    /** sets whether the order of players turns is to be decided in a clockwise direction or not.
     *The value is true for Clockwise, false for Counterclockwise */
    private boolean isClockwise = true;
    
	private int turn = 0;
	private int realTurn;
    public int getTurn() {	
    	return turn;
    }
    private int increaseTurn(){
    	if (turn+1 >= 4) return 0;
        else return turn + 1;
    }
    private int decreaseTurn() {
    	if (turn-1 <= -1) return 3;
        else return turn-1;
    }
    int nextTurn() {	
    	hasPlayerDrawn = false;
    	if(isClockwise) return increaseTurn();
    	else return decreaseTurn();
    }
    int changeTurn() {
    	turn=nextTurn();
    	return turn;
    }
    
    /** in order to be played, a game needs a deck,
     *  4 Players a pile of folded card the order of pl
     *  */
    public Game()   {   	
    	setGameMode("classic");
    	//trasferisce tutte le carte dalla pila al mazzo e mischia il mazzo
		deck.refill(pile);	
        p1=LoginState.getLoggedPlayer();		
		players= new Entity[]{p1, new Ai("AI1"),new Ai("AI2"),new Ai("AI3")};
		
		//i giocatori pescano le carte iniziali
		for (Entity e:players) {			
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
    		if(canPass()) changeTurn();
    		else break;
    	}
    }
    
	private void cardEffect(Card playedCard) {
		switch(playedCard.VALUE) {
		case REVERSE:	isClockwise= !isClockwise; break;
		case SKIP:	changeTurn(); break;
		case DRAW2:	playerDraw(players[changeTurn()],2); break;
		case DRAW4:	playerDraw(players[changeTurn()],4); isClockwise= !isClockwise; changeTurn(); isClockwise= !isClockwise;    
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
		if (!hasPlayerDrawn) {
			for (int i=0; i<n; i++) {
				e.drawFrom();
				setChanged();
				notifyObservers("Draw");}
		}
		hasPlayerDrawn=true;
	}
	
	private boolean canPass() {
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
	
	public void pass() {
		if(canPass()) {
			uno=false;
			changeTurn();
			aiTurn();			
		}
	}
	
	public void playerPlay(Card discard) {
		if (validPlay.run(discard, hasPlayerPlayed)) {  //TODO
			if (p1.HAND.size()==2 && uno==false) {			
				setChanged();
				notifyObservers("NoUno");
				hasPlayerDrawn=false;
				playerDraw(p1,2);
			}
			//scarta la carta
			p1.HAND.remove(discard);
			pile.addFirst(discard);
			setChanged();
			notifyObservers("Play");
			//l'effetto della carta si attiva
			cardEffect(discard);
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
			p1.victoriesUp(); 
			try {
				p1.save();
			} catch (SaveNotFoundException e) {
				e.printStackTrace();
			}}
	
	private void loss() {
			p1.expUp(20);
			p1.lossesUp();
			try {
				p1.save();
			} catch (SaveNotFoundException e) {
				e.printStackTrace();
			}
	}

}