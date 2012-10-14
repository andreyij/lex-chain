package chatsegmenter.parser.model;
public class ImplicitLink extends Link 
/*
 * we consider that <utterance2> property is an implicit reference to 
 * <utterance1> property
 */
{
	/*
	 * the type of the implicit link
	 * 0: lexical analysis
	 * 1: cue words
	 * 2: participant nickname reference
	 */
	private int type;
	
	public static final int lexicalType = 0;
	public static final int linkingWordsType = 1;
	public static final int refPatternsType = 2;
	public static final int refNicknameType = 3;
	
	/*
	 * the proximity between the 2 utterances
	 * set only if type = lexicalType or type = linkingWordsType
	 */ 
	private double proximityValue;
	
	/*
	 * the linking / cue words found
	 * set only if type = linkingWordsType
	 */
	private String linkingWords;
	
	/*
	 *  the maximum distance between <utterance2> and <utterance1>
	 *  in the case of linking words type implicit link
	*/
	public static final int linkingWordsMaxUttDistance = 5;
	
	/*
	 * the category of the linking / cue words found
	 * 
	 * currently implemented are: contrast, addition, summarization, result,
	 * agreement, disagreement
	 */
	private String linkingWordsCategory;
	
	/*
	 * the maximum distance between <utterance2> and <utterance1>
	 * in the case of refPatterns type implicit link
	*/
	public static final int refPatternsMaxUttDistance = 5;
	
	/*
	 * 
	 * the referred concept (<ref>) is assumed to appear in 
	 * a list of words having the size refPatternsWordsSize
	 * 
	 * used in the case of refPatterns type implicit link
	 */
	public static final int refPatternsWordsSize = 3;
	
	/*
	 * one of the patterns used to detect refPatterns type
	 * implicit links
	 */
	private String refPattern;
	
	/*
	 * the referred concept in the form of a word (especially a noun)
	 * stored as the matching stem
	 */
	private String referredConcept;
	
	/*
	 * the nick referenced in <utterance2>
	 * possibly not the exact match of a participant's name
	 * set only if type = refNicknameType
	 */
	private String refNick;
	
	/*
	 * the referenced participant
	 * set only if type = refNicknameType
	 */
	private Participant refParticipant;
	
	/*
	 * if type = cueWordsType or type = refNicknameType
	 * utterance1 may not be set
	 */
	private boolean isUtterance1Set;
	

	public int getType()
	{
		return type;
	}
	
	public void setType(int type)
	{
		this.type = type;
	}
	
	public double getProximityValue()
	{
		return proximityValue;
	}
	
	public void setProximityValue(double proximityValue)
	{
		this.proximityValue = proximityValue;
	}
	
	public String getLinkingWords()
	{
		return linkingWords;
	}
	
	public void setLinkingWords(String linkingWords)
	{
		this.linkingWords = linkingWords;
	}
	
	public String getLinkingWordsCategory()
	{
		return linkingWordsCategory;
	}
	
	public void setLinkingWordsCategory(String linkingWordsCategory)
	{
		this.linkingWordsCategory = linkingWordsCategory;
	}
	
	public String getRefPattern()
	{
		return refPattern;
	}
	
	public void setRefPattern(String refPattern)
	{
		this.refPattern = refPattern;
	}
	
	public String getReferredConcept()
	{
		return referredConcept;
	}
	
	public void setReferredConcept(String referredConcept)
	{
		this.referredConcept = referredConcept;
	}
	
	public String getRefNick()
	{
		return refNick;
	}
	
	public void setRefNick(String refNick)
	{
		this.refNick = refNick;
	}
	
	public Participant getRefParticipant()
	{
		return refParticipant;
	}
	
	public void setRefParticipant(Participant refParticipant)
	{
		this.refParticipant = refParticipant;
	}
	
	public boolean getIsUtterance1Set()
	{
		return isUtterance1Set;
	}
	
	public void setIsUtterance1Set(boolean isUtterance1Set)
	{
		this.isUtterance1Set = isUtterance1Set;
	}
}
