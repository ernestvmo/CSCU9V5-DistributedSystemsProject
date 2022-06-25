package ernestv.stir.ac.uk;

/**
 * This class will represent the coordinator of the Ring algorithm.
 * 
 * @author 2634056
 */
public class Coordinator
{	
	public static void main(String[] args)
	{
		if ((args.length < 1) || (args.length > 2))
		{// this will display an error if the run configuration doesn't have enough arguments.
			System.out.println("Usage: [host] <port>");
			System.out.println("Only " + args.length + " parameters entered");
			System.exit(1);
		}
		
		Buffer buffer = new Buffer();

		// Fetch the port number for the Receiver from the run configuration.
		int port_receiver = Integer.parseInt(args[0]);
		// Fetch the port number for the Mutex from the run configuration.
		int port_mutex = Integer.parseInt(args[1]);
		
		Receiver r = new Receiver(buffer, port_receiver);
		Mutex m = new Mutex(buffer, port_mutex);
		
		r.start();
		m.start();
	}
	
}
