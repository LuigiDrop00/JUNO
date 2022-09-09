package model;
/**
 * Functional interface which determines the behavior of Game when it has to activate the effect of a Card. 
 */
interface CardEffect {
	void activate(Game game, Card playedCard);
}