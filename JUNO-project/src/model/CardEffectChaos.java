package model;
/**
 * Functional interface which determines that, the behavior of Game when it has to activate the effect of a Card, has to be accordant with the chaos gamemode 
 */
interface CardEffectChaos extends CardEffect{
	default void activate(Game game, Card playedCard) {
//		if (game.getDraw()==0){
			switch(playedCard.VALUE) {
			case REVERSE:	game.invertTurn(); break;
			case SKIP:	game.increaseSkip(); break;
			case DRAW2: game.setDraw(game.getDraw()+2); break;
			case DRAW4: game.setHasPlayerPlayed(false);	game.increaseSkip(); game.playerDraw(Game.players[game.nextTurn()],4); game.changeColor(playedCard); game.setHasPlayerPlayed(true); break;
			case CHANGE:	game.changeColor(playedCard); break;
			case ZERO: game.setHasPlayerPlayed(false); break;
			case SEVEN: game.changeColor(playedCard); break;
			default:	break;
			}
//		}
//		else game.increaseSkip(); game.playerDraw(Game.players[game.nextTurn()],game.getDraw());

	}
}
