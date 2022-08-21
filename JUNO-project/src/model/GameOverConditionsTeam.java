package model;

public interface GameOverConditionsTeam extends GameOverConditions{
	@Override
	default String conditions() {
		String result;
		if (Game.players[0].HAND.size()==0 || Game.players[2].HAND.size()==0) result="Win";
		else result= "Loss";
		return result;
	}
}
