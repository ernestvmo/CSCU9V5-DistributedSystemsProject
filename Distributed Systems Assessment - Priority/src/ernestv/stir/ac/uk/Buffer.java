package ernestv.stir.ac.uk;

import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Vector;

/** 
 * This class will be used as Queue Data Structure to hold the requests that the Coordinator might receive.
 * 
 * @author 2634056
 */
public class Buffer
{
	/** A Queue containing all the Requests sent to the Coordinator. */
	private PriorityQueue<Request> data;
	
	/**
	 * Constructor method for the Buffer object.
	 */
	public Buffer()
	{
		data = new PriorityQueue<>();
	}
	
	/**
	 * Method that will return the size of the Queue.
	 * 
	 * @return The size of the Queue
	 */
	public int size()
	{
		return data.size();
	}
	
	/**
	 * This method handles the starvation and then adds the new request.
	 * 
	 * @param request The request to be added to the Queue.
	 */
	public void addRequest(Request request)
	{
		data = handleStarvation();
		data.add(request);
	}
	
	/**
	 * This method handles any starvation issue.
	 * 
	 * @return This method will return a Queue after it was updated.
	 */
	private PriorityQueue<Request> handleStarvation()
	{
		// Create a temporary queue with all the data from the buffer
		PriorityQueue<Request> tempQueue1 = data;
		// create a new queue with all the requests from the buffer, this is where we will update the priority value
		PriorityQueue<Request> tempQueue2 = new PriorityQueue<>();
		// placeholder Request object
		Request tempRequest = null;
		
		// loop through all the requests to update the priority value and add it to a new queue
		while (!tempQueue1.isEmpty())
		{
			tempRequest = tempQueue1.poll();
			tempRequest.setPriority(tempRequest.getPriority() - 1);
			tempQueue2.add(tempRequest);
		}
		
		return tempQueue2;
	}
	
	/**
	 * This method will return the first Request and remove it from the queue.
	 * 
	 * @return An array of Strings containing the IP of the first Request.
	 */
	synchronized public String[] get()
	{
		Request o = null;
		
		if (data.size() > 0)
		{
			o = data.poll();
		}
		
		String[] request = {o.getAddress(), String.valueOf(o.getPort()), String.valueOf(o.getPriority())};
		
		return request;
	}

	/**
	 * This method will create a Request object from the passed String array parameter.
	 * 
	 * @param request An array of String containing the IP address, the port, and the priority number.
	 */
	public synchronized void saveRequest(String[] request)
	{
		Request r = new Request(request[0], Integer.parseInt(request[1]), Integer.parseInt(request[2]));
		
		addRequest(r);
	}
	
	/**
	 * This method will display all the requests in the buffer.
	 */
	public void show()
	{
		System.out.println("[BUFFER]---> Showing the buffer");
		int count = 1;
		PriorityQueue<Request> tempData = new PriorityQueue<>(data);
		
		while (!tempData.isEmpty())
		{
			System.out.print("\t" + count + "\t");
			System.out.println(tempData.poll().toString());
			count++;
		}
		
		System.out.println(" ");
	}
}
