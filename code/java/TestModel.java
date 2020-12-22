import model.Model;
import server.Server;
import java.net.*;

public class TestModel {

	public static void main(String[] args){
		Model model = new Model();
		int counter = 0;
		while(true){
			System.out.println(String.format("Throwing no. %d", ++counter));
			model.throwDice();	
			int points = model.getPointsForDice("Full House");
			if(points>0){
				System.out.println(String.format("Success on attempt no %d", counter));
				break;
			}
		}
	}

}
