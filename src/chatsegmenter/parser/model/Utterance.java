package chatsegmenter.parser.model;

import java.util.Vector;

public class Utterance {

	/*
	 * (non-javadoc)
	 */
	private int utteranceId;

	// id of referenced utterance
	private int refId;

	// id of speaker
	private int speakerId;

	// id of participant to whom he is speaking
	private int participantId;

	/*
	 * (non-javadoc)
	 */
	private String text;

	/*
	 * (non-javadoc)
	 */
	private String annotatedText;

	/*
	 * (non-javadoc)
	 */
	private double importance;

	/*
	 * (non-javadoc)
	 */
	private long startTime;

	/*
	 * (non-javadoc)
	 */
	private long endTime;

	/*
	 * (non-javadoc)
	 */
	private Vector<Word> words;

	public Utterance(int utteranceId) {
		super();
		this.utteranceId = utteranceId;
		
		this.participantId = -1;	
	}

	public Utterance(int utteranceId, int refId, int speakerId, String text) {
		super();
		this.utteranceId = utteranceId;
		this.refId = refId;
		this.speakerId = speakerId;
		this.text = text;
		this.words = new Vector<Word>();
		
		this.participantId = -1;
	}

	/**
	 * Getter of the property <tt>utteranceId</tt>
	 * 
	 * @return Returns the utteranceId.
	 * 
	 */

	public int getUtteranceId() {
		return utteranceId;
	}

	/**
	 * Setter of the property <tt>utteranceId</tt>
	 * 
	 * @param utteranceId
	 *            The utteranceId to set.
	 * 
	 */
	public void setUtteranceId(int utteranceId) {
		this.utteranceId = utteranceId;
	}

	/**
	 * Getter of the property <tt>text</tt>
	 * 
	 * @return Returns the text.
	 * 
	 */

	public String getText() {
		return text;
	}

	/**
	 * Setter of the property <tt>text</tt>
	 * 
	 * @param text
	 *            The text to set.
	 * 
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Getter of the property <tt>annotatedText</tt>
	 * 
	 * @return Returns the annotatedText.
	 * 
	 */

	public String getAnnotatedText() {
		return annotatedText;
	}

	/**
	 * Setter of the property <tt>annotatedText</tt>
	 * 
	 * @param annotatedText
	 *            The annotatedText to set.
	 * 
	 */
	public void setAnnotatedText(String annotatedText) {
		this.annotatedText = annotatedText;
	}

	/**
	 * Getter of the property <tt>importance</tt>
	 * 
	 * @return Returns the importance.
	 * 
	 */

	public double getImportance() {
		return importance;
	}

	/**
	 * Setter of the property <tt>importance</tt>
	 * 
	 * @param importance
	 *            The importance to set.
	 * 
	 */
	public void setImportance(double importance) {
		this.importance = importance;
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
	 * Getter of the property <tt>words</tt>
	 * 
	 * @return Returns the words.
	 * 
	 */

	public Vector<Word> getWords() {
		return words;
	}

	/**
	 * Setter of the property <tt>words</tt>
	 * 
	 * @param words
	 *            The words to set.
	 * 
	 */
	public void setWords(Vector<Word> words) {
		this.words = words;
	}

	/**
	 * @return the refId
	 */
	public int getRefId() {
		return refId;
	}

	/**
	 * @param refId
	 *            the refId to set
	 */
	public void setRefId(int refId) {
		this.refId = refId;
	}

	/**
	 * @return the speakerId
	 */
	public int getSpeakerId() {
		return speakerId;
	}

	/**
	 * @param speakerId
	 *            the speakerId to set
	 */
	public void setSpeakerId(int speakerId) {
		this.speakerId = speakerId;
	}

	/**
	 * @return the participantId
	 */
	public int getParticipantId() {
		return participantId;
	}

	/**
	 * @param participantId
	 *            the participantId to set
	 */
	public void setParticipantId(int participantId) {
		this.participantId = participantId;
	}

	public boolean equals(Object obj) {
		Utterance ut = (Utterance) obj;
		if (ut.getUtteranceId() == this.getUtteranceId())
			return true;
		return false;
	}

}
