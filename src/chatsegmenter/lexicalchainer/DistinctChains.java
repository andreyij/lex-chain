package chatsegmenter.lexicalchainer;
import java.util.*;

import chatsegmenter.helper.*;
/**
 * This class stores the list of lexical chains as well as the list of the distinct lexical chains.
 * @author Andrei
 *
 */
public class DistinctChains
{
	private Vector<Vector<UttTaggedWord>> chainListDistinct;
	private Vector<Vector<UttTaggedWord>> chainList;
	private Hashtable<String, Integer> numberHash;
	
	public DistinctChains(Vector chainsDistinct, Hashtable<String, Integer> numberHash, Vector chains)
	{
		this.chainListDistinct = chainsDistinct;
		this.numberHash = numberHash;
		this.chainList = chains;
	}
	/**
	 * Returns the list of distinct lexical chains
	 * @return		The list of distinct lexical chains
	 */
	public Vector<Vector<UttTaggedWord>> getChainsDistinct()
	{
		return chainListDistinct;
	}
	
	/**
	 * Returns the list of lexical chains
	 * @return		The list of all lexical chains
	 */
	public Vector<Vector<UttTaggedWord>> getChains()
	{
		return chainList;
	}
	public Hashtable<String, Integer> getWordCount()
	{
		return numberHash;
	}
}