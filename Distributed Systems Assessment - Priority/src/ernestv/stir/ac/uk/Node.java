package ernestv.stir.ac.uk;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.Random;

/**
 * This class will represent the Node class.
 * 
 * @author 2634056
 */
public class Node
{
	private PrintWriter printWriter;
	
	private Socket next_socket;
	
	private int receiver_port = 7000;
	private int mutex_port = 7001;

	public static void main(String[] args)
	{// this will display an error if the run configuration doesn't have enough arguments.
		if ((args.length < 1) || (args.length > 5))
		{
			System.out.println("Usage: [host] <port>");
			System.out.println("Only " + args.length + " parameters entered");
			System.exit(1);
		}

		// get the name of the Node from the run configuration
		String name = args[0];
		// get the port number from the run configuration
		int port = Integer.parseInt(args[1]);
		// get the IP address from the run configuration
		String address = args[2];
		// get the priority of the Node from the run configuration
		int priority = Integer.parseInt(args[3]);
		// get the time the Node has to sleep from the run configuration
		int timeSleeping = Integer.parseInt(args[4]);

		Node node = new Node(name, address, port, priority, timeSleeping);
	}

	/**
	 * Constructor method for the Constructor class.
	 * 
	 * @param name The name of the Node.
	 * @param host The IP address of the Node.
	 * @param port The port number of the Node.
	 * @param priority The priority set to the Node.
	 * @param secondsSeeping The time the Node has to sleep before making another connection.
	 */
	public Node(String name, String host, int port, int priority, int secondsSeeping)
	{
		System.out.println("[" + name + "]---> Node active on port " + port);
		
		Random rdm = new Random();
		
		while (true)
		{
			try
			{
				Thread.sleep((rdm.nextInt(secondsSeeping) + 9000));
//				Thread.sleep(1000);
			}
			catch (InterruptedException ie)
			{
				System.out.println("[" + name + "]---> Error sleeping.");
				ie.printStackTrace();
			}
			
			try
			{
				next_socket = new Socket(host, receiver_port);
				// create the printWriter to pass the IP address, port number, and priority number through the socket
				printWriter = new PrintWriter(next_socket.getOutputStream(), true);
				// write the port on the printWriter
				printWriter.println(port);
				// write the priority on the printWriter
				printWriter.println(priority);
				// close the socket
				next_socket.close();
				
				System.out.println("[" + name + "]---> Request sent to the Coordinator.");

				// create a serverSocket to wait for a reply from the Mutex
				ServerSocket serverSocket = new ServerSocket(port);
				// accept the connection with the Mutex
				next_socket = serverSocket.accept();
				
				System.out.println("[" + name + "]---> Request accepted!");
				System.out.println();

				//////////////////////////////////////////////////////////////////////////////
				
				// create an inputStream from the Socket
				InputStream inputStream = next_socket.getInputStream();
				// create an objectInputStream using the inputStream
				ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

				// assign the token using the objectInputStream
				Token token = (Token) objectInputStream.readObject();
				
				System.out.println("[" + name + "]---> Received the token.");

				//////////////////////////////////////////////////////////////////////////////
				
				System.out.println();
				System.out.println("[" + name + "]---> Entered critical section.");
				// Sleep after the Node exited the critical section
				Thread.sleep(500);
				// increment the counter in the Token to show that it has been accessed
				token.incrementCounter();
				// set the content of the Token
				token.setContent(name);
				// close the socket
				next_socket.close();
				// close the server socket
				serverSocket.close();
				System.out.println("[" + name + "]---> Exited critical section.");
				System.out.println();

				//////////////////////////////////////////////////////////////////////////////
				
				// create a socket to send the token back to the Mutex
				next_socket = new Socket(host, mutex_port);
				// create an outputStream object to pass the token to the Mutex
				OutputStream outputStream = next_socket.getOutputStream();
				// create an objectOutputStream using the outputStream
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
				// write the Token on the objectOutputStream
				objectOutputStream.writeObject(token);
				// close the socket
				next_socket.close();
				System.out.println("[" + name + "]---> Token sent back to Mutex.");
				System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				System.out.println();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				System.exit(0);
			}
		}
	}		
}
