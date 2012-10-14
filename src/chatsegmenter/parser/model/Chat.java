package chatsegmenter.parser.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;

public class Chat {

	/*
	 * (non-javadoc)
	 */
	private Vector<Utterance> utterances;

	/*
	 * (non-javadoc)
	 */
	private int chatId;

	/*
	 * (non-javadoc)
	 */
	private long startTime;

	/*
	 * (non-javadoc)
	 */
	private long endTime;

	// overall number of participants
	private int noParticipants;

	// a list of participants
	private ArrayList<Participant> participants;

	/*
	 * (non-javadoc)
	 */
	private Vector<Link> links;

	private Vector<LinkedList<Integer>> utteranceGraph;

	public Chat() {
		utterances = new Vector<Utterance>();
		participants = new ArrayList<Participant>();
		links = new Vector<Link>();
		utteranceGraph = new Vector<LinkedList<Integer>>();
	}

	/**
	 * @return the noParticipants
	 */
	public int getNoParticipants() {
		return noParticipants;
	}

	/**
	 * @param noParticipants
	 *            the noParticipants to set
	 */
	public void setNoParticipants(int noParticipants) {
		this.noParticipants = noParticipants;
	}

	/**
	 * @return the participants
	 */
	public ArrayList<Participant> getParticipants() {
		return participants;
	}

	/**
	 * @param participants
	 *            the participants to set
	 */
	public void setParticipants(ArrayList<Participant> participants) {
		this.participants = participants;
	}

	/**
	 * Getter of the property <tt>chatId</tt>
	 * 
	 * @return Returns the chatId.
	 * 
	 */

	public int getChatId() {
		return chatId;
	}

	/**
	 * Setter of the property <tt>chatId</tt>
	 * 
	 * @param chatId
	 *            The chatId to set.
	 * 
	 */
	public void setChatId(int chatId) {
		this.chatId = chatId;
	}

	/**
	 * Getter of the property <tt>utterances</tt>
	 * 
	 * @return Returns the utterances.
	 * 
	 */

	public Vector<Utterance> getUtterances() {
		return utterances;
	}
	public int getNoOfUtterances()
	{
		return utterances.size();
	}
	/**
	 * Setter of the property <tt>utterances</tt>
	 * 
	 * @param utterances
	 *            The utterances to set.
	 * 
	 */
	public void setUtterances(Vector<Utterance> utterances) {
		this.utterances = utterances;
	}

	/**
	 * Getter of the property <tt>startTime</tt>
	 * 
	 * @return Returns the startTime.
	 * 
	 */

	public long getStartTime() {
		return startTime;
	}

	/**
	 * Setter of the property <tt>startTime</tt>
	 * 
	 * @param startTime
	 *            The startTime to set.
	 * 
	 */
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	/**
	 * Getter of the property <tt>endTime</tt>
	 * 
	 * @return Returns the endTime.
	 * 
	 */

	public long getEndTime() {
		return endTime;
	}

	/**
	 * Setter of the property <tt>endTime</tt>
	 * 
	 * @param endTime
	 *            The endTime to set.
	 * 
	 */
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	/**
	 * Getter of the property <tt>links</tt>
	 * 
	 * @return Returns the links.
	 * 
	 */

	public Vector<Link> getLinks() {
		return links;
	}

	/**
	 * Setter of the property <tt>links</tt>
	 * 
	 * @param links
	 *            The links to set.
	 * 
	 */
	public void setLinks(Vector<Link> links) {
		this.links = links;
	}

	/**
	 * @return the utteranceGraph
	 */
	public Vector<LinkedList<Integer>> getUtteranceGraph() {
		return utteranceGraph;
	}

	/**
	 * @param utteranceGraph
	 *            the utteranceGraph to set
	 */
	public void setUtteranceGraph(Vector<LinkedList<Integer>> utteranceGraph) {
		this.utteranceGraph = utteranceGraph;
	}

	//print participants in a chat environment
	
	public void printParticipants() {
		System.out.println(noParticipants + " participants:");
		for (int i = 0; i < noParticipants; i++)
			System.out.println("-"+participants.get(i).getName());
		System.out.println("\n");
	}

	// write output in a txt file

	public void printUtterances() {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("other/chseg/replici.txt"));
			for (int i = 1; i < utterances.size(); i++) {
				if (utterances.get(i) != null) {
					if (utterances.get(i).getParticipantId() >= 0)
						out.write(utterances.get(i).getUtteranceId()
								+ ":"
								+ utterances.get(i).getRefId()
								+ "; "
								+ participants.get(
												utterances.get(i).getSpeakerId())
												.getName()
								+ "->"
								+ participants.get(
												utterances.get(i).getParticipantId())
												.getName() + "\n");
					else
						out.write(utterances.get(i).getUtteranceId()
								+ ":"
								+ utterances.get(i).getRefId()
								+ "; "
								+ participants.get(
										utterances.get(i).getSpeakerId())
										.getName() + "\n");
					out.write("\t" + utterances.get(i).getText()
							+ "\n\t---------------------------------\n");
				}
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
