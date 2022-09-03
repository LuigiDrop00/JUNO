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

		private Ai(String nickname) {
			super(nickname);
		}
		@Override
		public void play(Card discard) {
			while (!hasPlayerPlayed) {
			
			//restituisce una lista di carte giocabili secondo le regole              //DA LEVARE O CAMBIARE VALIDPLAY
			ArrayList<Card> playable=(ArrayList<Card>) this.HAND.stream().filter((x)->(x.getColor().equals(discard.getColor()) || x.getColor()==Color.BLACK || x.VALUE.equals(discard.VALUE))).collect(Collectors.toList());
				
			if (playable.isEmpty()) {
				playerDraw(this,1);
				Card drawn= this.HAND.get(HAND.size()-1);
				if (validPlay.run(drawn, Game.this)) {
		    		pile.addFirst(drawn);
		    		HAND.remove(drawn);
					setChanged();
					notifyObservers("Play");
					hasPlayerPlayed=true;
					cardEffect.activate(Game.this, drawn);
					
				}
				else {
					setChanged();
					notifyObservers("Pass");
					hasPlayerPlayed=true;
				}
			}
			else {
				Card drawn= playable.get(0);
				if (validPlay.run(drawn, Game.this)) {
					pile.addFirst(drawn);
					HAND.remove(drawn);
					setChanged();
					notifyObservers("Play");
					hasPlayerPlayed=true;
					cardEffect.activate(Game.this, drawn);
					}
				}	
			}
			hasPlayerPlayed=false;
		}
	}

    private Player p1;
    static public Entity[] players;
    static private GameOverConditions gameOverConditions;
    static private ValidPlay validPlay;
    static private CardEffect cardEffect;
   // private Supplier p= gameOverConditions::conditions;
    public static void setGameMode(String mode) {
    	switch(mode) {
    	case "classic": gameOverConditions = new GameOverConditionsClassic() {}; validPlay = new ValidPlayClassic() {};
    	cardEffect = new CardEffectClassic() {}; break;
    	case "teams": gameOverConditions = new GameOverConditionsTeam() {}; validPlay = new ValidPlayClassic() {};
    	cardEffect = new CardEffectClassic() {}; break;
    	case "chaos":  gameOverConditions = new GameOverConditionsClassic() {}; validPlay = new ValidPlayChaos() {};
    	cardEffect = new CardEffectChaos() {}; break;
    	default: System.out.println("errore selezione gamemode");
    	}
    }

    final static private Deck deck = Deck.getInstance();
    final static public LinkedList<Card> pile= new LinkedList<Card>();
    /**
     * Checks if player has pressed the uno button this turn */
    private boolean uno = false;
    private int draw;
    int getDraw() {
    	return draw;
    }
    void setDraw(int draw) {
    	this.draw=draw;
    }
    /**
     * Checks if player has played a card this turn */
   private boolean hasPlayerPlayed = false;
   void setHasPlayerPlayed(boolean played) {
	   hasPlayerPlayed=played;
   }
   boolean getHasPlayerPlayed() {
	   return hasPlayerPlayed;
   }
    /**
     * Checks if player has drawn a card this turn */
    boolean hasPlayerDrawn = false;
    /** sets whether the order of players turns is to be decided in a clockwise direction or not.
     *The value is true for Clockwise, false for Counterclockwise */
    private boolean isClockwise = true;
    void invertTurn() {
    	isClockwise= !isClockwise;
    }
    
	private int turn = 1;
	private int skip;
	public int getSkip() {
		return skip;
	}
	void increaseSkip() {
		++skip;
	}
    public int getTurn() {	
    	return turn;
    }
    public int getTurn(int s) {
    	return turn + s;
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
    	if(isClockwise) return increaseTurn();
    	else return decreaseTurn();
    }
    private int changeTurn() {
    	for (int i=0; i<=skip; i++) turn=nextTurn();
    	skip=0;
    	return turn;
    }
    
    /** in order to be played, a game needs a deck,
     *  4 Players a pile of folded card the order of pl
     *  */
    public Game()   {   	
    	//trasferisce tutte le carte dalla pila al mazzo e mischia il mazzo
		deck.refill(pile);	
        p1=LoginState.getLoggedPlayer();		
        Ai ai1 = new Ai("AI1");
        Ai ai2 = new Ai("AI2");
        Ai ai3 = new Ai("AI3");
        
		players= new Entity[]{p1, ai1, ai2, ai3};
		
		//i giocatori pescano le carte iniziali
		for (Entity e:players) {			
			e.drawFrom(7);
		}
		
		Card first=deck.draw();
		pile.addFirst(first);
		while (first.VALUE==Value.DRAW4) {
			deck.refill(pile);
			first=deck.draw();	
			pile.addFirst(first);
			}
		cardEffect.activate(this, first);
		System.out.println("PROMA CARTA: "+ first);
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
    
/*	private void cardEffect(Card playedCard) {
		switch(playedCard.VALUE) {
		case REVERSE:	isClockwise= !isClockwise; break;
		case SKIP:	++skip; break;
		case DRAW2:	++skip; playerDraw(players[nextTurn()],2); break;
		case DRAW4:	++skip; playerDraw(players[nextTurn()],4); changeColor(playedCard); break;
		case CHANGE:	changeColor(playedCard); break;
		default:	break;
		}		
	}  */
    
    void changeColor(Card playedCard) {
    	if (getTurn()!=0 || playedCard.VALUE==Value.SETTE) playedCard.setColor(Color.values() [(int) (4*Math.random())]);
    	else {
    		setChanged();
			notifyObservers("ChangeColor");
    	}
    }
	
	public void playerDraw(Entity e, int n) {
		if (!hasPlayerDrawn && !hasPlayerPlayed) {
			for (int i=0; i<n; i++) {
				e.drawFrom();
				setChanged();
				notifyObservers("Draw");}
		}
		if (turn==0) hasPlayerDrawn=true;
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
			hasPlayerPlayed=false;
	    	hasPlayerDrawn = false;
			changeTurn();
			aiTurn();			
		}
	}
	
	public void playerPlay(Card discard) {
		if(discard.VALUE==Value.DRAW2) {
			System.out.println("ATTENZIONE");
		}
		
		if (validPlay.run(discard, this)) { 
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
			hasPlayerPlayed=true;
			cardEffect.activate(this, discard);
			
		}	
		else {
			setChanged();
			notifyObservers("InvalidPlay");
		}	
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