package model;

interface ValidPlayChaos extends ValidPlay{
	default boolean run (Card discard, Game game) {
		if (game.getDraw()!=0 && discard.VALUE!=Value.DRAW2) {
			game.playerDraw(Game.players[game.getTurn()], game.getDraw());
			game.setDraw(0);
			game.setHasPlayerPlayed(true);
			return false;
		}
		Card drawn= Game.pile.getFirst();
		return (game.getHasPlayerPlayed() ? drawn.VALUE.equals(discard.VALUE) : (drawn.getColor().equals(discard.getColor()) || discard.getColor()==Color.BLACK || drawn.VALUE.equals(discard.VALUE)));
	}
}
