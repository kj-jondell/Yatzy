package model;

import model.dice.Dice;
import model.player.Player;

public class Model {

	private final static int AMT_DICE = 5;
	private Dice dice;
	private Player player;

	public Model() {
		dice = new Dice(AMT_DICE);
		player = new Player();

		int counter = 0;
		while(true){
			System.out.println(String.format("Throwing no. %d", ++counter));
			dice.throwDice();	
			int points = getPointsForDice("Yatzy");
			if(points>0){
				System.out.println(String.format("Success on attempt no %d", counter));
				break;
			}
		}

	}

	/**
	 * TODO temporary helper function
	 * @param checkType
	 * @return
	 */
	public int getPointsForDice(String checkType){
		try {
			int points = dice.getLowerSectionPoints(checkType);
			if(points>0)
				return points;
		} catch (NullPointerException e){
			System.err.println(String.format("Unknown type: %s", checkType));
		}
		return 0;
	}
}