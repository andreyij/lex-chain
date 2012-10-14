package chatsegmenter.helper;
import edu.stanford.nlp.ling.TaggedWord;

import net.didion.jwnl.data.*;
/**
 * Class used to store information regarding a POS-tagged word. Information regarding the utterance ID is
 * also stored.
 * @author Andrei
 *
 */
public class UttTaggedWord extends TaggedWord
{
	private int uttID;
	private IndexWord iw;
	private Position pos;
	
	public UttTaggedWord(String value,String tag,int id,IndexWord iw)
	{
		super(value,tag);
		uttID = id;
		this.iw = iw;
		this.pos = null;
	}
	public UttTaggedWord(TaggedWord tw,int id)
	{
		super(tw.value(), tw.tag());
		uttID = id;
	}
	public UttTaggedWord(TaggedWord tw,int id,IndexWord iw)
	{
		super(tw.value(),tw.tag());
		uttID = id;
		this.iw = iw;
	}
	public UttTaggedWord(UttTaggedWord wd)
	{
		super(wd.value(), wd.tag());
		this.uttID = wd.getUttID();
		if (wd.pos != null)
		{
			this.pos = new Position(wd.pos.line, wd.pos.index);
		}
		else
		{
			this.pos = null;
		}
		this.iw = wd.getIndexWord();
		
	}
	public int getUttID()
	{
		return uttID;
	}
	
	public IndexWord getIndexWord()
	{
		return iw;
	}
	public void setIndexWord(IndexWord iw)
	{
		this.iw = iw;
	}
	
	public void setPosition (int line, int index)
	{
		this.pos = new Position(line, index);
	}
	public Position getPosition()
	{
		return this.pos;
	}
}
