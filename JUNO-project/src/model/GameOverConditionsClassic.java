package model;
/**
 * Functional interface which determines that, the behavior of Game when it has to check if and who won the game, has to be accordant with the classic gamemode 
 */
public interface GameOverConditionsClassic extends GameOverConditions {
	@Override
	default String conditions() {
		String result=null;
		if(Game.players[0].HAND.size()==0) result="Win";
		else if (Game.players[1].HAND.size()==0 || Game.players[2].HAND.size()==0 || Game.players[3].HAND.size()==0) result="Loss";
		return result;
	}
}
