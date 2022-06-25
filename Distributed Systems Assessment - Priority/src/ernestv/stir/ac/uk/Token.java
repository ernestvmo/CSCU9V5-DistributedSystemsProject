package ernestv.stir.ac.uk;

import java.io.Serializable;

/**
 * This class will represent the Token object.
 * 
 * @author 2634056
 */
@SuppressWarnings("serial")
public class Token implements Serializable
{
	private int counter;
	private String content;

	/**
	 * Constructor method for the Token class.
	 */
	public Token()
	{
		this.counter = 0;
		content = "[TOKEN]----> Token not accessed.";
	}

	/**
	 * Method to increment the counter in the Token.
	 */
	public void incrementCounter()
	{
		counter++;
	}

	/**
	 * Accessor method for the content of the Token.
	 * 
	 * @return The content of the Token
	 */
	public String getContent()
	{
		return content;
	}

	/**
	 * This method will set the content of the Token using the Node's name.
	 * 
	 * @param nodeName The name of the Node
	 */
	public void setContent(String nodeName)
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("[TOKEN]----> Token accessed " + counter + " times.\n");
		sb.append("[TOKEN]----> Token last accessed by " + nodeName);
		
		content = sb.toString();
	}
}
