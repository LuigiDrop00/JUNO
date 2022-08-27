package model;

interface cardEffectClassic extends cardEffect {
	default void activate(Game game, Card playedCard) {
		switch(playedCard.VALUE) {
		case REVERSE:	game.isClockwise= !game.isClockwise; break;
		case SKIP:	game.changeTurn(); break;
		case DRAW2:	playerDraw(Game.players[changeTurn()],2); break;
		case DRAW4:	playerDraw(Game.players[changeTurn()],4);     
			changeColor(playedCard); break;
		case CHANGE:	changeColor(playedCard); break;
		default:
			System.out.println("CARTA NORMALE");
			break;
		}
	}
}
