package model;

interface ValidPlayClassic extends ValidPlay {
	default boolean run (Card discard, boolean hasPlayerPlayed) {
		Card drawn= Game.pile.getFirst();
		return drawn.getColor().equals(discard.getColor())|| discard.getColor()==Color.BLACK||drawn.VALUE.equals(discard.VALUE);
	}
}
