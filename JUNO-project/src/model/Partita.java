package model;

import java.util.LinkedList;
import java.util.Observable;

/** a class that rapresents a game and its features */
public class Partita extends Observable {
    
    private Player p1; 
    private Player p2;
    private Player p3;
    private Player p4;

    private Mazzo mazzo = Mazzo.getInstance();
    private LinkedList<Carta> pilaScarti = new LinkedList<>();

    /** sets whether the order of players turns is to be decided in a clockwise direction or not */
    private boolean isClockwise = true;
    private int turno = 0;
    private int getTurno(){
        return ++turno % 4;
    }
    /** in order to be played, a game needs a deck,
     *  4 Players a pile of folded card the order of pl
     *  */
    public Partita(Player player) throws IncorrectPasswordException, SaveNotFoundException  {
        //imposta il gicatore umano
        // this.p1 = player;
        //mischia il mazzo
        
        p1 = Player.load("p1", "123");
        p2 = Player.load("p2", "123");
        p3 = Player.load("p3", "123");
        p4 = Player.load("p4", "123");

        mazzo.mischiaMazzo();
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


}
