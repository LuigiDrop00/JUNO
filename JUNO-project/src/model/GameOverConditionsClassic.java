package model;

public interface GameOverConditionsClassic extends GameOverConditions {
	@Override
	default String conditions() {
		if(Game.players[0].HAND.size()==0) return "Win";
		else return "Loss";
	}
}
