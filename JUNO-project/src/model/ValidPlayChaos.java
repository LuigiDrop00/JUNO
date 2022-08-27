package model;

interface ValidPlayChaos extends ValidPlay{
	default boolean run (Card discard, boolean hasPlayerPlayed) {
		Card drawn= Game.pile.getFirst();
		return hasPlayerPlayed ? drawn.VALUE.equals(discard.VALUE) : (drawn.getColor().equals(discard.getColor()) || discard.getColor()==Color.BLACK || drawn.VALUE.equals(discard.VALUE));
	}
}
