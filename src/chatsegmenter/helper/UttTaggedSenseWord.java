package chatsegmenter.helper;


import net.didion.jwnl.data.*;
/**
 * Class used to store sense information about a word which has already been stemmed and labeled
 * with a POS-tag. The sense used to label a word is repersented by a WordNet {@link Synset} object.
 * 
 * @author Andrei
 *
 */
public class UttTaggedSenseWord extends UttTaggedWord{
	private Synset sense;
	
	/**
	 * Constructor for a {@code UttTaggedSenseWord} object
	 * @param wd		The POS-tagged word
	 * @param sense		The sense used to labeled the word
	 */
	public UttTaggedSenseWord(UttTaggedWord wd,Synset sense)
	{
		super(wd);
		this.sense = sense;
	}
	
	public Synset getSense()
	{
		return sense;
	}
	
	public void setSense(Synset sense)
	{
		this.sense = sense;
	}
	
}
