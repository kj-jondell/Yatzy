package model.player;

import model.Model;

public class Player {

    private int totalPoints;

    public Player() {
        Protocol protocol = new Protocol();
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }
    
}

class Protocol{

    enum Slot {

       ONES("Ones", true),  TWOS("Twos", true), THREES("Threes", true), FOURS("Fours", true), FIVES("Fives", true), SIXES("Sixes", true),
       ONE_PAIR("One Pair", false), TWO_PAIR("Two Pair", false), THREE_OF_A_KIND("Three of a kind", false), FOUR_OF_A_KIND("Four of a kind", false), SMALL_STRAIGHT("Small Straight", false), LARGE_STRAIGHT("Large Straight", false), FULL_HOUSE("Full House", false), CHANCE("Chance", false), YATZY("Yatzy", false);

       String name;
       boolean isUpper;

       Slot(String name, boolean isUpper){
           this.name = name;
           this.isUpper = isUpper;
       }

    }

    Protocol(){
    }


}