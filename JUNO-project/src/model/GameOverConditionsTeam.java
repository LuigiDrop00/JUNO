package model;

public interface GameOverConditionsTeam extends GameOverConditions{
	@Override
	default String conditions() {
		if (Game.players[0].HAND.size()==0 || Game.players[2].HAND.size()==0) return "Win";
		else return "Loss";
	}
}
