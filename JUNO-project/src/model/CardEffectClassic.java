package model;

interface CardEffectClassic extends CardEffect {
	default void activate(Game game, Card playedCard) {

			switch(playedCard.VALUE) {
			case REVERSE:	game.invertTurn(); break;
			case SKIP:	game.increaseSkip(); break;
			case DRAW2:	game.increaseSkip(); game.playerDraw(Game.players[game.nextTurn()],2); break;
			case DRAW4:	game.increaseSkip(); game.playerDraw(Game.players[game.nextTurn()],4); game.changeColor(playedCard); break;
			case CHANGE:	game.changeColor(playedCard); break;
			default:	break;
			}				
	}
}
