package ernestv.stir.ac.uk;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * This class will represent the Connection object.
 * 
 * @author 2634056
 */
@SuppressWarnings("unused")
public class Connection extends Thread
{
	/** The Socket received from the Node. */
	private Socket socket;
	/** The buffer object to send the request to. */
	private Buffer buffer;
	
	private InputStream inputStream;
	private BufferedReader bufferReader;
	
	private Token token;

	/**
	 * Constructor method for the Connection object.
	 * 
	 * @param socket The Socket to talk to the Node.
	 * @param buffer The buffer to send the Request to.
	 */
	public Connection(Socket socket, Buffer buffer)
	{
		this.socket = socket;
		this.buffer = buffer;
	}
	
	public void run()
	{// this is where the section where the connection with the Coordinator happens.
		
		String[] requests = new String[3];

		try 
		{
			inputStream = socket.getInputStream();
			bufferReader = new BufferedReader(new InputStreamReader(inputStream));

			// read the values from the bufferReader and save in into an array.
			requests[0] = socket.getInetAddress().toString();
			requests[0] = requests[0].substring(1, requests[0].length());
			requests[1] = bufferReader.readLine();
			requests[2] = bufferReader.readLine();

			// Send the request to the buffer and add it to the buffer queue.
			buffer.saveRequest(requests);

			// Close the Socket
			socket.close();
		}
		catch (Exception e)
		{
			System.out.println("Something went wrong adding the request.");
			e.printStackTrace();
		}
	}
}