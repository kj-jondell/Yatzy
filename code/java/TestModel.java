import model.Model;
import java.net.*;

public class TestModel {

	public static void main(String[] args){
		System.out.println("");
		try {
			Model model = new Model();
			System.out.println(model.getAttribute());
		} catch(UnknownHostException e){
			e.printStackTrace();
		}
	}

}
