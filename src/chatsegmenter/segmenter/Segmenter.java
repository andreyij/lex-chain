package chatsegmenter.segmenter;
import chatsegmenter.helper.UttTaggedWord;
import chatsegmenter.parser.model.*;

import java.util.*;
public class Segmenter {
	public static final int SEG_LIMIT = 30;//un segment are minim SEG_LIMIT replici
	public static Vector<Integer> segment(Vector<Vector<UttTaggedWord>> chains,Chat ch)
	{
		Vector<Integer> segments = new Vector<Integer>();
		int nrUtt = ch.getNoOfUtterances();
		Vector<Integer> nrChEnd = new Vector<Integer>();//numarul de lanturi care se incheie in dreptul propozitiei i
		Vector<Integer> nrChBegin = new Vector<Integer>();//numarul de lanturi care incep in dreptul propozitiei i
		
		for (int i = 0; i < nrUtt; i++)
		{
			nrChEnd.add(0);
			nrChBegin.add(0);
		}
		for (int i = 0; i < chains.size(); i++)
		{
			Vector<UttTaggedWord> chain = chains.elementAt(i);
			int uttid1 = chain.elementAt(0).getUttID();
			int uttid2 = chain.elementAt(chain.size() - 1).getUttID();
			nrChBegin.set(uttid1, nrChBegin.elementAt(uttid1) + 1);
			nrChEnd.set(uttid2,nrChEnd.elementAt(uttid2) + 1);
		}
		
		double mean = 0;
		int nn = 0;
		for (int i = 0; i < nrUtt - 1; i++)
		{
			int boundValue = nrChEnd.elementAt(i) + nrChBegin.elementAt(i + 1);
			if (boundValue > 0)
			{
				nn++;
				mean = mean + boundValue;
			}
		}
		mean = mean / nn;
		int lastseg = 1;
		segments.add(1);
		for (int i = 0; i < nrUtt - 1; i++)
		{
			if ((nrChEnd.elementAt(i) + nrChBegin.elementAt(i + 1) > mean)
					&&(i - lastseg > Segmenter.SEG_LIMIT))
			{
				segments.add(i);
				lastseg = i;
			}
		}
		segments.add(nrUtt - 1);
		return segments;
	}
	public static String printSegments(Chat ch,Vector<Integer> segments)
	{
		StringBuffer str = new StringBuffer("");
		Vector<Utterance> utts = ch.getUtterances();
		for (int i = 0; i < segments.size() - 1; i++)
		{
			str.append("Segmentul " + (i + 1)+":\n");
			for (int j = segments.elementAt(i); j < segments.elementAt(i + 1) - 1; j++)
			{
				str.append(utts.elementAt(j).getParticipantId() + ":" + utts.elementAt(j).getText() + "\n");
			}
		}
		return str.toString();
	}

}
