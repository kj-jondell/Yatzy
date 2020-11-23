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
		// printAllUpperSectionPoints();
		Rules.getLowerSectionPoints(dice, "Two Pair");
		Rules.getLowerSectionPoints(dice, "One Pair");
		Rules.getLowerSectionPoints(dice, "Full House");
		Rules.getLowerSectionPoints(dice, "Three of a kind");
		Rules.getLowerSectionPoints(dice, "Four of a kind");
		Rules.getLowerSectionPoints(dice, "Yatzy");
	}

	private void printAllUpperSectionPoints(){
		String[] helper = {"ones", "twos", "threes", "fours", "fives", "sixes"};
		for (int i = 1; i <= 6; i++) {
			System.out.println(String.format("You get %d points if you put this for %s", Rules.getUpperSectionPoints(dice, i), helper[i-1]));
		}
	}

}

class Rules {

	// TODO static class or regular...?

	private static Map<String, int[]> pairs = Map.of(
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
	 * 
	 * @param dice
	 * @param type
	 * @return
	 */
	static int getLowerSectionPoints(Die[] dice, String type){
		//TODO throw error if uncorrect key given??
		int rulePairs[] = Rules.pairs.get(type);
		int firstSizeDemand = rulePairs[0], secondSizeDemand = rulePairs[1];

		Map<Integer, Integer> countOfValues = getCountOfValuesMap(dice);
		
		List<Die> firstDieCollection = new ArrayList<Die>(), secondDieCollection = new ArrayList<Die>();
		while(countOfValues.size() > 0){
			int largestDieValue = Collections.max(countOfValues.keySet()); 
			int count = countOfValues.get(largestDieValue);
			if(count >= firstSizeDemand && firstDieCollection.isEmpty())  
				for (int i = 0; i < firstSizeDemand; i++) 
					firstDieCollection.add(new Die(largestDieValue));
			else if(count >= secondSizeDemand && secondDieCollection.isEmpty())
				for (int i = 0; i < secondSizeDemand; i++) 
					secondDieCollection.add(new Die(largestDieValue));
			countOfValues.remove(largestDieValue);
		}

		int points = 0;
		if ((firstSizeDemand == 0 || firstDieCollection.size() > 0) && (secondSizeDemand == 0 || secondDieCollection.size() > 0)) { // both demands must be met...
			firstDieCollection.addAll(secondDieCollection);
			Die[] pointDice = firstDieCollection.toArray(new Die[firstDieCollection.size()]);
			points = getSumOfDice(pointDice);
		}

		return points;
	}
	
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
	 * @param value
	 * @return points of the given value
	 */
	static int getUpperSectionPoints(Die[] dice, int value){
		return Rules.getSumOfDice(Rules.getListOfValue(dice, value));
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