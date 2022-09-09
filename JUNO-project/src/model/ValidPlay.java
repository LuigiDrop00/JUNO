package model;
/**
 * Functional interface which determines the behavior of Game when it has to check if the human player has played according to the rules
 */
interface ValidPlay {
	boolean run (Card discard, Game game);
}
