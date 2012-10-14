package chatsegmenter.paronimchainer;

import java.util.Vector;
import net.didion.jwnl.data.IndexWord;

import chatsegmenter.helper.UttTaggedWord;

public class ParonimChainer {
	public static Vector<Vector<UttTaggedWord>> createChains(Vector<UttTaggedWord> uttwords) throws Exception
	{
		Vector<Vector<UttTaggedWord>> chainList = new Vector<Vector<UttTaggedWord>>();
		Vector<Vector<Integer>> chainpoz = new Vector<Vector<Integer>>();
		int i,j,k;
		IndexWord[] indWords = new IndexWord[uttwords.size()];
		
		for (i = 0 ;i<uttwords.size();i++)
		{
			
			UttTaggedWord word = uttwords.elementAt(i);
			IndexWord aux = word.getIndexWord();
				indWords[i] = aux;
		
		}
		for ( i = 0;i<uttwords.size();i++)
		{
			int found = 0;
			for ( j = 0;j<chainList.size();j++)
			{
				Vector<UttTaggedWord> chain = chainList.elementAt(j);
				for (k = 0;k<chain.size();k++)
				{
					int wordIndex = chainpoz.elementAt(j).elementAt(k);
					if (ParoCheck.areParonims(indWords[i].getLemma(), indWords[wordIndex].getLemma())==true)
					{
						chainList.elementAt(j).add(uttwords.elementAt(i));
						chainpoz.elementAt(j).add(i);
						k = chain.size();
						j = chainList.size();
						found = 1;
					}
				}
			}
			if (found == 0)
			{
				chainList.add(new Vector<UttTaggedWord>());
				chainList.elementAt(chainList.size()-1).add(uttwords.elementAt(i));
				chainpoz.add(new Vector<Integer>());
				chainpoz.elementAt(chainList.size()-1).add(i);
			}
		}
		return chainList;
	}
}

