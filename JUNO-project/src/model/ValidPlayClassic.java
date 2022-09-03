package model;

interface ValidPlayClassic extends ValidPlay {
	default boolean run (Card discard, Game game) {
		Card drawn= Game.pile.getFirst();
		return !game.getHasPlayerPlayed() && (drawn.getColor().equals(discard.getColor())|| discard.getColor()==Color.BLACK||drawn.VALUE.equals(discard.VALUE));
	}
}
