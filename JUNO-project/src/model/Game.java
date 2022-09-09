package model;

import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;
import java.util.function.*;
import java.util.stream.Collectors;

/** a class that represents a game and its features */
@SuppressWarnings("deprecation")
public class Game extends Observable {
	/**The Ai class represents an artificial player that plays in a game according to certain rules defined in the class Game
	 */
	private class Ai extends Entity{
		/**Initializes a newly created Ai object using the constructor of Entity and setting a random avatar 
		 */
		private Ai(String nickname) {
			super(nickname);
			String sep= FileSystems.getDefault().getSeparator();
			setAvatar(Paths.get("src"+sep+"profilePic"+sep+"images"+((int) (20*Math.random())+1)+".jpg").toAbsolutePath().toString());
		}
		/**
		 * Makes the Ai play their turn. The resulting action depends on if the first card on the dicard pile matches any of the cards in the HAND of the Ai.
		 * In which case it will play the first card it can. If it can't play a card it will draw a new card and play if it can, or else it will pass the turn. 
		 */
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
					setChanged();
					notifyObservers("Pass");
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
					setChanged();
					notifyObservers("Pass");
					}
				}	
			}
			hasPlayerPlayed=false;
		}
	}
	/**
	 * The human player 
	 */
    private Player p1;
    /**
     * An array with all the players playing the game
     */
    static public Entity[] players;
    /**
     * Determines the behavior of Game when it has to check if and who won the game 
     */
    static private GameOverConditions gameOverConditions;
    /**
     * Determines the behavior of Game when it has to check if the human player has played according to the rules  
     */
    static private ValidPlay validPlay;
    /**
     * Determines the behavior of Game when it has to activate the effect of a Card. 
     */
    static private CardEffect cardEffect;
    /**
     * Sets the behavior for this game according to the gamemode selected
     */
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
    /**
     * Field containing the Deck from which the card are drawn
     */
    final static private Deck deck = Deck.getInstance();
    /**
     * Field containing the discard pile where the Cards played are discarded
     */
    final static public LinkedList<Card> pile= new LinkedList<Card>();
    /**
     * Checks if player has pressed the uno button this turn */
    private boolean uno = false;
    
    /**
     * How many card a player has to draw their turn. Used in the functional interfaces ValidPlayChaos and CardEffectChaos
     */
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
    private boolean hasPlayerDrawn = false;
    /** sets whether the order of players turns is to be decided in a clockwise direction or not.
     *The value is true for Clockwise, false for Counterclockwise */
    private boolean isClockwise = true;
    /**
     * Changes the direction of the turn cycle
     */
    void invertTurn() {
    	isClockwise= !isClockwise;
    }
    /**
     * The current turn
     */
	private int turn = 1;
	/**
	 * How many turns have to be skipped 
	 */
	private int skip;
	public int getSkip() {
		return skip;
	}
	/**
	 * Increases the field skip by one 
	 */
	void increaseSkip() {
		++skip;
	}
    public int getTurn() {	
    	return turn;
    }
    /**
     * Returns the next turn depending on the direction of the turn cycle and the number of skips
     * @param s
     * @return
     */
    public int nextTurn(int s) {
    	if(isClockwise) return increaseTurn(s);
    	else return decreaseTurn(s);
    }
    /**
     * Returns the next turn in a clockwise direction
     * @return
     */
    private int increaseTurn(){
    	if (turn+1 >= 4) return 0;
        else return turn + 1;
    }
    /**
     * Returns the next turn in a counterclockwise direction
     * @return
     */
    private int decreaseTurn() {
    	
    	if (turn-1 <= -1) return 3;
        else return turn-1;
    }
    /**
     * Returns the next turn in a counterclockwise direction and skips turns if s>0
     * @param s
     * @return
     */
    private int decreaseTurn(int s) {
    	int turno=turn;
    	for (int i=0; i<s; i++) {
    		turno= turn-1 <= -1 ? 3 : turn-1; 
    	}
    	return turno;
    }
    /**
     * Returns the next turn in a clockwise direction and skips turns if s>0
     * @param s
     * @return
     */
    private int increaseTurn(int s){
    	int turno=turn;
    	for (int i=0; i<s; i++) {
    		turno= turn+1 >= 4 ? 0 : turn+1; 
    	}
    	return turno;
    }
    /**
     * Returns the next turn depending on the direction of the turn cycle
     * @return
     */
    int nextTurn() {	
    	if(isClockwise) return increaseTurn();
    	else return decreaseTurn();
    }
    /**
     * Returns and changes the field turn to the next turn depending on the direction of the turn cycle and the number of skips
     * @return
     */
    private int changeTurn() {
    	System.out.println("Giocatore"+turn+": "+players[turn].HAND);
    	for (int i=0; i<=skip; i++) turn=nextTurn();
    	skip=0;
    	return turn;
    }
    
    /** Initializes a newly created Ai object by: refilling the deck with card in the pile if there are some from the previous Game instance;
     * gets the logged player; creates the 3 Ais; initializes the players array; gives 7 cards to the HAND of each player; 
     * and puts the first card of the deck in the pile and activates its effect, unless the card's value is a DRAW4 in which case it's put back in the deck and another card is drawn. 
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
    /**
     * Makes the Ais play until its the turn of the human player. Also updates the observers if an Ai remains with 1 card in HAND
     */
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
    
    /**
     * Changes the color of the played card if its a card that can change color.
     * In case its the human player that played the card the notify method will be called for the observers.
     * @param playedCard
     */
    void changeColor(Card playedCard) {
    	if (getTurn()!=0 || playedCard.VALUE==Value.SEVEN) {
    	
    		System.out.println("colore cambiato");
    		playedCard.setColor(Color.values() [(int) (4*Math.random())]);
    	}
    	else {
    		setChanged();
			notifyObservers("ChangeColor");
    	}
    }
	/**
	 * Checks if the human player can draw and if they can they will and it will also update the observers.  
	 * @param e
	 * @param n
	 */
	public void playerDraw(Entity e, int n) {
		if (!hasPlayerDrawn && !hasPlayerPlayed) {
			for (int i=0; i<n; i++) {
				e.drawFrom();
				setChanged();
				notifyObservers("Draw");}
		}
		if (turn==0) hasPlayerDrawn=true;
	}
	/**
	 * Check if a player can pass their turn or if the game is concluded because someone won
	 * @return
	 */
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
	/**
	 * Passes the turn if it can and notifies the observers
	 */
	public void pass() {
		if(canPass()) {
			setChanged();
			notifyObservers("Pass");
			uno=false;
			hasPlayerPlayed=false;
	    	hasPlayerDrawn = false;
			changeTurn();
			aiTurn();			
		}
	}
	/**
	 * Checks if the human player plays a valid Card according to the rules and if it is the card is put in the pile and its effect is activated.
	 * Also checks if the player has pressed UNO in the case it has 2 cards before playing and if they haven't they draw 2 cards.
	 * There is a notify in case of: a valid play, an invalid play and an UNO not pressed when it should.
	 * @param discard
	 */
	public void playerPlay(Card discard) {

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
	/**
	 * Changes the value of the field uno to true (or pressed) for the human player turn and updates the observers. 
	 */
	public void pressUno() {
		if (p1.HAND.size()==2 && uno==false && getTurn()==0) {
			uno=true;       
			setChanged();
			notifyObservers("Uno");
		}
	}
	/**
	 * Gives the human player exp according to the cards in the HAND of the Ais, increases their victory field by 1 and saves the changes
	 */
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
	/**
	 * Gives the human player 20 exp, increases their losses field by 1 and saves the changes
	 */
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