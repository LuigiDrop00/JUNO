package model;
/**
 * Functional interface which determines that, the behavior of Game when it has to check if the human player has played according to the rules, has to be accordant with the classic gamemode 
 */
interface ValidPlayClassic extends ValidPlay {
	default boolean run (Card discard, Game game) {
		Card drawn= Game.pile.getFirst();
		return !game.getHasPlayerPlayed() && (drawn.getColor().equals(discard.getColor())|| discard.getColor()==Color.BLACK||drawn.VALUE.equals(discard.VALUE));
	}
}
