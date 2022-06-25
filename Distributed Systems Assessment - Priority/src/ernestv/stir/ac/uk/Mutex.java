package ernestv.stir.ac.uk;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class will represent the Coordinator class.
 * 
 * @author 2634056
 */
public class Mutex extends Thread
{
	Buffer buffer;
	Socket socket;
	int port;
	Token token;

	// the ip and port of the node requesting the token
	String next_host;
	int next_port;

	/**
	 * Constructor method for the Mutex object.
	 * 
	 * @param buffer a reference to the Buffer object.
	 * @param port The port to use for the Mutex object.
	 */
	public Mutex(Buffer buffer, int port)
	{
		this.buffer = buffer;
		this.port = port;
		token = new Token();
	}
	
	public void run()
	{
		try
		{
//			System.out.println(port);
			ServerSocket serverSocket = new ServerSocket(port);
			
			while (true)
			{// this loop will repeat constantly
				try
				{
					Thread.sleep(10000);
				}
				catch (Exception e)
				{
					System.out.println("[MUTEX]-> Error sleeping.");
					e.printStackTrace();
				}
				
				System.out.println("[MUTEX]-> The current buffer size is: " + buffer.size());
				buffer.show();
				
				if (buffer.size() != 0)
				{
					String[] o = (String[]) buffer.get();
					
					next_host = o[0];
					next_port = Integer.parseInt(o[1]);
//					buffer.get();
					
//					System.out.println(next_host + ":" + next_port);
					
					try 
					{// connection to the node
						System.out.println("[MUTEX]-> Connection to node.");
						socket = new Socket(next_host, next_port);
						// get an outputStream from the socket
						OutputStream outputStream = socket.getOutputStream();
						// create an objectOutputStream using an outputStream object
						ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
						// send the token to the Node through the Socket.
						objectOutputStream.writeObject(token);
						// close the socket sending the token
						socket.close();
						System.out.println("[MUTEX]-> Token sent.");
					}
					catch (Exception e)
					{
						System.out.println("CRASH: Error connecting to the Node!");
						e.printStackTrace();
					}
					
					System.out.println("\nThe Node is using the Token.");
					System.out.println(token.getContent());
					System.out.println();
					
					try 
					{
						Thread.sleep(2000);
					}
					catch (Exception e)
					{
						System.out.println("[MUTEX]-> Error sleeping.");
						e.printStackTrace();
					}
					
					try
					{
						System.out.println("[MUTEX]-> Waiting on token to be returned.");
						// accept a connection from the Node to receive the token
						socket = serverSocket.accept();
						// create an inputStream using the Socket
						InputStream inputStream = socket.getInputStream();
						// create an objectInputStream using the inputStream
				        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
				        // reassign the token object pulled from the objectInputStream
				        token = (Token) objectInputStream.readObject();
						System.out.println("[MUTEX]-> Received the Token back!");
						System.out.println();
					}
					catch (Exception e)
					{
						System.out.println("CRASH: Error waiting for token back!");
						e.printStackTrace();
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
