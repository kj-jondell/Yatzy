import model.Model;
import server.Server;

import java.net.*;

public class TestModel {

	public static void main(String[] args){
		Model model = new Model();
		for (int i = 0; i < 10; i++) {
			System.out.println(String.format("Throw no. %d: ", i+1));
			//model.throwDice(new int[]{1, 2, 3});	
			model.throwDice();	
		}
	}

}
