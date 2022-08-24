package model;

public interface GameOverConditionsTeam extends GameOverConditions{
	@Override
	default String conditions() {
		String result=null;
		if (Game.players[0].HAND.size()==0 || Game.players[2].HAND.size()==0) result="Win";
		else if (Game.players[1].HAND.size()==0 || Game.players[3].HAND.size()==0) result= "Loss";
		return result;
	}
}
