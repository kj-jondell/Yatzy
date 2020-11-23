package model;

import java.net.*;
public class Model {

	public String getAttribute() throws UnknownHostException {
		InetAddress aHost = InetAddress.getLocalHost();
		System.out.println(aHost);
		return "HELLO";
	}

}
