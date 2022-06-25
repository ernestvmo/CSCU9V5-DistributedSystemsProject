package ernestv.stir.ac.uk;

import java.time.LocalDateTime;

/**
 * This class will represent the Request object.
 * 
 * @author 2634056
 */
public class Request implements Comparable<Request>
{
	private String address;
	private int port;
	private int priority;

	/**
	 * Constructor method for the Request class.
	 * 
	 * @param address The address of the Node sending the Request.
	 * @param port The port number of the Node sending the Request.
	 * @param priority The priority number to use to order the Request 
	 */
	public Request(String address, int port, int priority)
	{
		this.address = address;
		this.port = port;
		this.priority = priority;
	}

	/**
	 * Accessor method for the IP address of the Request.
	 * 
	 * @return The address of the Request
	 */
	public String getAddress()
	{
		return address;
	}

	/**
	 * Accessor method for the port number of the Request.
	 * 
	 * @return The port number of the Request
	 */
	public int getPort()
	{
		return port;
	}

	/**
	 * Accessor method for the priority number of the Request.
	 * 
	 * @return The priority value of the Request
	 */
	public int getPriority()
	{
		return priority;
	}

	/**
	 * Mutator method for the priority number of the Request.
	 * 
	 * @param priority The new value to set the priority
	 */
	public void setPriority(int priority)
	{
		this.priority = priority;
	}
	
	@Override
	public String toString()
	{
		return address + ":" + port + " priority: " + priority;
	}
	
	@Override
	public int compareTo(Request r)
	{
		return ((Integer) this.getPriority()).compareTo((Integer) r.getPriority());
	}
}
