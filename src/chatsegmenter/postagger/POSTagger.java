package chatsegmenter.postagger;
import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import net.didion.jwnl.data.POS;
import chatsegmenter.LexChain;
import chatsegmenter.SystemParam;
import chatsegmenter.helper.Position;
import chatsegmenter.helper.UttTaggedWord;
import chatsegmenter.parser.model.Chat;
import chatsegmenter.parser.model.Utterance;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
public class POSTagger {
	public static POS getPosFromTag(UttTaggedWord word)
	{
		if (word.tag().length()<2)
			return null;
		if (word.tag().substring(0, 2).equals("JJ"))
		{
			return POS.ADJECTIVE;
		}
		if (word.tag().substring(0, 2).equals("NN"))
		{
			return POS.NOUN;
		}
		if (word.tag().substring(0, 2).equals("VB"))
		{
			return POS.VERB;
		}
		if (word.tag().substring(0, 2).equals("RB"))
		{
			return POS.ADVERB;
		}
		return null;
	}
	public static Vector<UttTaggedWord> tagWords (String text, Vector<Position> positions) throws Exception
	{
		Vector<UttTaggedWord> tokens = new Vector<UttTaggedWord>();
		MaxentTagger tagger = new MaxentTagger(SystemParam.getInstance().getParam("TAGGER_LIB"));
		
		if (LexChain.DEBUG_PRINT)
			System.out.println(text.length());

		int i = 0;
		List<List<HasWord>> sentences = 
										MaxentTagger.tokenizeText(new BufferedReader(new StringReader(text)));
		for (List<HasWord> sentence : sentences) 
			{
				ArrayList<TaggedWord> tSentence = tagger.tagSentence(sentence);
				for (int j = 0; j < tSentence.size(); j++)
					{
						//System.out.println(tSentence.get(j).beginPosition() + " " + tSentence.get(j).endPosition());
						UttTaggedWord tw = new UttTaggedWord(tSentence.get(j),-1);
						if (positions != null && Character.isLetter(tw.tag().charAt(0)) == true
							&& tw.value().contains("'") == false)
						{
							tw.setPosition(positions.elementAt(i).line, positions.elementAt(i).index);
							i++;
						}
						POS pos = getPosFromTag(tw);
						if (pos!=null)
							tokens.add(tw);
					}
			}
		return tokens;
		
	}
	public static Vector<UttTaggedWord> tagChat(Chat ch) throws Exception
	{
		Vector<UttTaggedWord> tokens = new Vector<UttTaggedWord>();
		MaxentTagger tagger = new MaxentTagger(SystemParam.getInstance().getParam("TAGGER_LIB"));
	
		Vector<Utterance> utv = ch.getUtterances();
		
		if (LexChain.DEBUG_PRINT)
			System.out.println(utv.size());
		
		for (int i = 0; i < utv.size(); i++)
		{
			Utterance ut = utv.elementAt(i);
			
			if (LexChain.DEBUG_PRINT)
				System.out.println(i);
			
			if (ut != null)
			{
			List<List<HasWord>> sentences = MaxentTagger.tokenizeText(new BufferedReader(new StringReader(ut.getText())));
			for (List<HasWord> sentence : sentences) 
				{
			      ArrayList<TaggedWord> tSentence = tagger.tagSentence(sentence);
			      for (int j = 0; j < tSentence.size(); j++)
			      {
			    	  UttTaggedWord tw = new UttTaggedWord(tSentence.get(j),ut.getUtteranceId());
			    	 
			    	  POS pos = getPosFromTag(tw);
			    	  
			    	  if (pos != null)
			    		  tokens.add(tw);
			      }
				}
			}
		}
		return tokens;
		
	}
}


