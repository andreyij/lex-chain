package chatsegmenter.parser.model;

import java.util.Vector;

public class ChatGroup {

	/*
 * (non-javadoc)
 */
private Vector<Chat> chats;

	
	 
	/*
 * (non-javadoc)
 */
private String subject;



	/*
 * (non-javadoc)
 */
private long startTime;





	/*
 * (non-javadoc)
 */
private long endTime;





	/**
	 * Getter of the property <tt>chats</tt>
	 *
	 * @return Returns the chats.
	 * 
	 */
	
	public Vector<Chat> getChats()
	{
		return chats;
	}

	
	
	/**
	 * Setter of the property <tt>chats</tt>
	 *
	 * @param chats The chats to set.
	 *
	 */
	public void setChats(Vector<Chat> chats ){
		this.chats = chats;
	}



	
	 
	/**
	 * Getter of the property <tt>subject</tt>
	 *
	 * @return Returns the subject.
	 * 
	 */
	
	public String getSubject()
	{
		return subject;
	}



	
	
	/**
	 * Setter of the property <tt>subject</tt>
	 *
	 * @param subject The subject to set.
	 *
	 */
	public void setSubject(String subject ){
		this.subject = subject;
	}



	
	 
	/**
	 * Getter of the property <tt>startTime</tt>
	 *
	 * @return Returns the startTime.
	 * 
	 */
	
	public long getStartTime()
	{
		return startTime;
	}



	
	
	/**
	 * Setter of the property <tt>startTime</tt>
	 *
	 * @param startTime The startTime to set.
	 *
	 */
	public void setStartTime(long startTime ){
		this.startTime = startTime;
	}



	
	 
	/**
	 * Getter of the property <tt>endTime</tt>
	 *
	 * @return Returns the endTime.
	 * 
	 */
	
	public long getEndTime()
	{
		return endTime;
	}



	
	
	/**
	 * Setter of the property <tt>endTime</tt>
	 *
	 * @param endTime The endTime to set.
	 *
	 */
	public void setEndTime(long endTime ){
		this.endTime = endTime;
	}

}
