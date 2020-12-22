/**
 * @author Karl Johannes Jondell
 * Communicator/model for Yatzy-game
 * 
 * Messages (e.g. UDP or from sys.in) has namespace:
 * <action> [<arguments>]
 * by the following table:
 * CHOOSE_DICE "x,y,z"
 * ROLL_DICE
 * SET_POINTS
 * 
 * Responses given as OK or ERROR ...
 */

package model;

import java.util.*;

import model.dice.Dice;
import model.player.Player;

public class Model {

	private final static int AMT_DICE = 5, AMT_PLAYERS = 2, AMT_MAX_ROLLS = 3; //TODO variables...
	private Dice dice;
	private Player[] players = new Player[AMT_PLAYERS];
	private int[] chosenDice = new int[0];
	private int currentRoll = 0, currentPlayer = 0;

	public Model() {
		this.initilizeGame();
		this.systemInputAction();
	}

	private void initilizeGame(){
		dice = new Dice(AMT_DICE);
		for (int index = 0; index < players.length; index++) {
			players[index] = new Player(); //first player starts
		}
	}
	
	private void handlePlayerAction(PlayerAction action, String arguments){
		switch (action) {
			case CHOOSE_DICE:
				String[] digits = arguments.split(",");
				this.chosenDice = new int[digits.length];
				for (int i = 0; i < digits.length; i++) 
					this.chosenDice[i] = Integer.parseInt(digits[i])-1;
				break;
		
			case SET_POINTS:
				//	TODO check if valid move...
				// players[currentPlayer].validMove(move);
				currentPlayer = (currentPlayer+1)%AMT_PLAYERS; //next player
				break;
		
			case GET_POINTS:
				System.out.println("Get");
				break;
		
			case ROLL_DICE:
				if(currentRoll < AMT_MAX_ROLLS){
					if(this.chosenDice.length > 0)
						dice.throwDice(this.chosenDice);
					currentRoll++;
				}
				break;
		
			default:
			// TODO return false?
				break;
		}
		// dice.printDice();
	}

	/**
	 * temporary...
	 */
	private void systemInputAction(){
		Map<String, PlayerAction> chosenAction = Map.of(
			"choose", PlayerAction.CHOOSE_DICE,
			"roll", PlayerAction.ROLL_DICE,
			"get", PlayerAction.GET_POINTS,
			"set", PlayerAction.SET_POINTS
		);

		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNextLine()){
			try {
				String[] splits = scanner.nextLine().split(" ");
				PlayerAction action = chosenAction.get(splits[0]);
				this.handlePlayerAction(action, splits.length > 1 ? splits[1] : "");
			}catch (NullPointerException e){
				System.err.println("Choose a correct option!");
			}
		}
	}

}

enum PlayerAction {

	CHOOSE_DICE, ROLL_DICE, SET_POINTS, GET_POINTS;

}