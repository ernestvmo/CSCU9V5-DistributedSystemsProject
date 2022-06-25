package ernestv.stir.ac.uk;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Receiver extends Thread
{
	private Buffer buffer;
	private int port;

	private ServerSocket serverSocket;
	private Socket socket;

	private Connection connection;

	private int priority;

	public Receiver(Buffer buffer, int port)
	{
		this.buffer = buffer;
		this.port = port;
	}

	public void run()
	{
		try
		{
			// Create a server socket to receiver any connection from a Node's
			// socket.
			serverSocket = new ServerSocket(port);

			while (true)
			{
				System.out.println("[RECEIVER]--> Wating for requests...");
				try
				{
					System.out.println("");
					// Accept a connection from a Node's socket
					socket = serverSocket.accept();
					System.out.println("[RECEIVER]--> Connection accepted.");
					// create a Connection object using the Socket from the Node and the Buffer object
					connection = new Connection(socket, buffer);
					// Start the Connection Thread
					connection.start();

					Thread.sleep(1000);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
