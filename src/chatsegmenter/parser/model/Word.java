package chatsegmenter.parser.model;

import java.util.ArrayList;

public class Word {

	/*
 * (non-javadoc)
 */
private String text;

	
	 
	/*
 * (non-javadoc)
 */
private String POS;



	/*
 * (non-javadoc)
 */
private String stem;


/*
 * the list of this word's synonyms
 * each synonym is represented as another Word object
 */
private ArrayList<Word> synonyms;

private boolean isStopWord;

/*
 * true if the field text contains only letters
 */
private boolean onlyLetters;

	/**
	 * Getter of the property <tt>text</tt>
	 *
	 * @return Returns the text.
	 * 
	 */
	
	public String getText()
	{
		return text;
	}

	
	
	/**
	 * Setter of the property <tt>text</tt>
	 *
	 * @param text The text to set.
	 *
	 */
	public void setText(String text ){
		this.text = text;
	}



	
	 
	/**
	 * Getter of the property <tt>POS</tt>
	 *
	 * @return Returns the POS.
	 * 
	 */
	
	public String getPOS()
	{
		return POS;
	}



	
	
	/**
	 * Setter of the property <tt>POS</tt>
	 *
	 * @param POS The POS to set.
	 *
	 */
	public void setPOS(String POS ){
		this.POS = POS;
	}



	
	 
	/**
	 * Getter of the property <tt>stem</tt>
	 *
	 * @return Returns the stem.
	 * 
	 */
	
	public String getStem()
	{
		return stem;
	}



	
	
	/**
	 * Setter of the property <tt>stem</tt>
	 *
	 * @param stem The stem to set.
	 *
	 */
	public void setStem(String stem ){
		this.stem = stem;
	}
	
	public ArrayList<Word> getSynonyms()
	{
		return synonyms;
	}
	
	public void setSynonyms(ArrayList<Word> synonyms)
	{
		this.synonyms =  synonyms;
	}

	public boolean getIsStopWord()
	{
		return isStopWord;
	}
	
	public void setIsStopWord(boolean isStopWord)
	{
		this.isStopWord = isStopWord;
	}
	
	public boolean getOnlyLetters()
	{
		return onlyLetters;
	}
	
	public void setOnlyLetters(boolean onlyLetters)
	{
		this.onlyLetters = onlyLetters;
	}
}
