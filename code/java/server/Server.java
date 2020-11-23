package server;

import java.io.*;
import java.net.*;

/**
 * @author Karl Johannes Jondell
 * @version 0.001a
 * @since 0.001a
 */
public class Server{

    private static final int PORT_NUMBER = 1234; //TODO temporary port no.

    /**
     * Starts server on creation
     */
    public Server(){
        startServer();
    }

    /**
     * Starts server
     * @return null
     */
    private void startServer(){
        System.out.println("Server starting...");
        try (
            ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
            Socket clientSocket = serverSocket.accept();
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        )
        {
            InetAddress aHost = InetAddress.getLocalHost();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    
}