package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.*;

public class Model {

	private final static int AMT_DICE = 5;

	private Die dice[] = new Die[AMT_DICE];

	public Model() {
		for (int index = 0; index < dice.length; index++) 
			dice[index] = new Die();
	}

	/**
	 * Throws all dice available
	 */
	public void throwDice(){
		this.throwDice(IntStream.rangeClosed(0, AMT_DICE).toArray());
	}

	/**
	 * @param indexes list of dice to be thrown
	 */
	public void throwDice(int[] indexes){
		for (int index : indexes) {
			if(index < dice.length) {
				dice[index].throwDie();
				System.out.println(String.format("Value of die no. %d: %d", index+1, dice[index].getValue()));
			}
		}
	}

	public int getPointsForDice(String checkType){
		try {
			int points = Dice.getLowerSectionPoints(dice, checkType);
			if(points>0)
				return points;
		} catch (NullPointerException e){
			System.err.println(String.format("Unknown type: %s", checkType));
		}
		return 0;
	}

	// private void printAllUpperSectionPoints(){
	// 	String[] helper = {"ones", "twos", "threes", "fours", "fives", "sixes"};
	// 	for (int i = 1; i <= 6; i++) {
	// 		System.out.println(String.format("You get %d points if you put this for %s", Dice.getUpperSectionPoints(dice, i), helper[i-1]));
	// 	}
	// }

}

class Dice {

	// TODO static class or regular...?

	private final static Map<String, int[]> PAIRS = Map.of(
		/* Name, [Start value, End value]*/
		"Small Straight", new int[]{1, 5},
		"Large Straight", new int[]{2, 6},
		/* Name, [First demand, Second demand]*/
		"One Pair", new int[]{2, 0},
		"Two Pair", new int[]{2, 2},
		"Full House", new int[]{3, 2},
		"Three of a kind", new int[]{3, 0},
		"Four of a kind", new int[]{4, 0},
		"Yatzy", new int[]{5, 0}
	);

	/**
	 * 
	 * @param dice
	 * @param value
	 * @return array of dice with given value
	 */
	private static Die[] getListOfValue(Die[] dice, int value) {
		List<Die> listOfDice = new ArrayList<Die>();
		for (Die die : dice) {
			if(die.getValue() == value){
				listOfDice.add(die);
			}	
		}

		return listOfDice.toArray(new Die[listOfDice.size()]);
	}

	/**
	 * 
	 * @param dice
	 * @return
	 */
	static Map<Integer, Integer> getCountOfValuesMap(Die[] dice){
		Map<Integer, Integer> countOfValues = new HashMap<Integer, Integer>();
		for (Die die : dice) 
			countOfValues.compute(die.getValue(), (key, value) -> value == null ? 1 : value+1);
		return countOfValues;
	}

	/**
	 * Sums the points by the dice given in the array.
	 * Used also for chance.
	 * @param dice
	 * @return
	 */
	static int getSumOfDice(Die[] dice){
		int points = 0;
		for (Die die : dice) {
			points += die.getValue();
		}
		return points;
	}

	/**
	 * 
	 * @param dice
	 * @param type
	 * @return
	 */
	static int getLowerSectionPoints(Die[] dice, String type) throws NullPointerException {
		int points = 0;
		Map<Integer, Integer> countOfValues = getCountOfValuesMap(dice);
		int rulePairs[] = Dice.PAIRS.get(type);

		if(type.contains("Straight")){ // checks first whether type is straight or not...
			for (int value = rulePairs[0]; value <= rulePairs[1]; value++) //first argument is start value, second is end value
				if(!countOfValues.keySet().contains(value))
					return 0;
				else points += value;

			return points;
		}
		else {
			List<Die> firstDieCollection = new ArrayList<Die>(), secondDieCollection = new ArrayList<Die>();
			while(countOfValues.size() > 0){
				int largestDieValue = Collections.max(countOfValues.keySet()); 
				int count = countOfValues.get(largestDieValue);
				if(count >= rulePairs[0] && firstDieCollection.isEmpty())  //first argument is first size demand, second is second size demand
					for (int i = 0; i < rulePairs[0]; i++) 
						firstDieCollection.add(new Die(largestDieValue));
				else if(count >= rulePairs[1] && secondDieCollection.isEmpty())
					for (int i = 0; i < rulePairs[1]; i++) 
						secondDieCollection.add(new Die(largestDieValue));
				countOfValues.remove(largestDieValue);
			}
			if ((rulePairs[0] == 0 || firstDieCollection.size() > 0) && (rulePairs[1] == 0 || secondDieCollection.size() > 0)) { // both demands must be met...
				firstDieCollection.addAll(secondDieCollection);
				Die[] pointDice = firstDieCollection.toArray(new Die[firstDieCollection.size()]);
				points = getSumOfDice(pointDice);
			}
		}

		return points;
	}
	
	/**
	 * 
	 * @param dice
	 * @param value
	 * @return points of the given value
	 */
	static int getUpperSectionPoints(Die[] dice, int value){
		return Dice.getSumOfDice(Dice.getListOfValue(dice, value));
	}

} 

class Die {

	private Random dieThrower;
	private int range, value;

	public Die() {
		this(-1, 6); // default range is 6
	}

	public Die(int value) {
		this(value, 6); 
	}

	public Die(int value, int range) {
		this.range = range;
		this.value = value;
		this.dieThrower = new Random();
		if (value == -1) {
			this.throwDie();
		}
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public void throwDie(){
		this.setValue(this.dieThrower.nextInt(this.range)+1);
	}

}