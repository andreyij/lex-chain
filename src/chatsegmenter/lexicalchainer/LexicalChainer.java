package chatsegmenter.lexicalchainer;

import java.util.*;
import java.io.*;

import net.didion.jwnl.*;
import net.didion.jwnl.data.*;
import net.didion.jwnl.data.relationship.*;

import chatsegmenter.LexChain;
import chatsegmenter.helper.*;

/**
 * This class contains the lexical chaining algorithm
 * @author Andrei
 *
 */
public class LexicalChainer
{
	
	public static final double limits[] = {-16,0.5,25,30,0.8,1};
	public static final double FRACTION = 0.9;
	public static final int LENGTH_LIMIT = 3;
	
	/**
	 * This method returns a display form of the list of lexical chains , as a {@link String} 
	 * 
	 * @param chains		The list of lexical chains
	 * @return				The print form as a {@link String}
	 */
	public static String printChains(Vector<Vector<UttTaggedWord>> chains)
	{
		StringBuffer str = new StringBuffer("");
		for (int i = 0; i < chains.size(); i++)
		{
			Vector<UttTaggedWord> chain = chains.elementAt(i);
			str.append("Lantul " + (i + 1) + ":\n");
			for (int j = 0; j < chain.size() - 1; j++)
			{
				
				str.append(chain.elementAt(j).value() + " | ");
			}
			str.append(chain.elementAt(chain.size() - 1).value() + "\n");
		}
		return str.toString();
	}
	
	/**
	 * This method returns a display form of the list of distinct lexical chains , as a {@link String} 
	 * 
	 * @param chainList		The list of distinct lexical chains
	 * @return				The print form as a {@link String}
	 */
	
	public static String printChainsDistinct(DistinctChains chainList)
	{
		StringBuffer str = new StringBuffer("");
		Vector<Vector<UttTaggedWord>> chains = chainList.getChainsDistinct();
		Hashtable<String, Integer> numberHash = chainList.getWordCount();
		for (int i = 0;i<chains.size();i++)
		{
			Vector<UttTaggedWord> chain = chains.elementAt(i);
			str.append("Lantul " + (i + 1) + ":\n");
			for (int j = 0; j < chain.size() - 1; j++)
			{
				str.append(chain.elementAt(j).value() + "(" + numberHash.get(chain.elementAt(j).value()) + ")" + " | ");
			}
			str.append(chain.elementAt(chain.size() - 1).value() + "(" +
					   numberHash.get(chain.elementAt(chain.size() - 1).value()) + ")" + "\n");
		}
		return str.toString();
	}
	
	public static DistinctChains createChains(Vector<UttTaggedWord> uttwords,
												Hashtable<String, Integer> numberHash,
												Hashtable<String, Long> searchHash, int distance) 
												throws JWNLException, IOException
	{
		
		Vector<Vector<UttTaggedWord>> chainList = new Vector<Vector<UttTaggedWord>>();
		Vector<Vector<Integer>> chainpoz = new Vector<Vector<Integer>>();
		Vector<Vector<UttTaggedWord>> chainListDistinct = new Vector<Vector<UttTaggedWord>>();
		int i,j,k;
		IndexWord[] indWords = new IndexWord[uttwords.size()];
		Hashtable<String,Double> distanceHash = new Hashtable<String,Double>();
		
		Hashtable<String,Integer> pathHash = new Hashtable<String,Integer>();
		Hashtable<String,Integer> chainHash = new Hashtable<String,Integer>();
	
		long t1 = System.currentTimeMillis();
		
		double limit_inf = LexicalChainer.limits[2*distance];
		double limit_sup = LexicalChainer.limits[2*distance+1];
		for ( i = 0; i < uttwords.size(); i++)
		{
		
			indWords[i] = uttwords.elementAt(i).getIndexWord();
		}
		for ( i = 0; i < uttwords.size(); i++)
		{
		
			if (LexChain.DEBUG_PRINT)
				System.out.println(i);
			
			int ok = 0;
			Integer chainIndex = chainHash.get(uttwords.elementAt(i).getIndexWord().getLemma());
			if (chainIndex != null)
			{
				ok = 1;
				chainList.elementAt(chainIndex).add(uttwords.elementAt(i));
				chainpoz.elementAt(chainIndex).add(i);		
			}
			else
			{
				
				for ( j = 0;j < chainList.size(); j++)
				{
					Vector<UttTaggedWord> chain = chainList.elementAt(j);
					int found = 0;
					for (k = 0; k < chain.size(); k++)
					{
						//---Relationship rel;
						RelParent rel;
							int wordIndex = chainpoz.elementAt(j).elementAt(k);
							//System.out.println(i+" "+j+" "+k);

							String word1 = indWords[i].getLemma();
							String word2 = indWords[wordIndex].getLemma();
							String key = word1 + " " + word2;
							String key1 = word2 + " " + word1;//cele 2 cuv concatenate, in ambele moduri posibile
							Double dist = distanceHash.get(key);
							int splength = Integer.MAX_VALUE;
							if (dist == null)//daca nu se afla in hash distanta intre cele 2 cuvinte
							{
								//---rel = WordNetHelper.getShortestPath(indWords[i], indWords[wordIndex]);
								rel = WordNetHelper.getInstance().getShortestPathLCA(indWords[i], indWords[wordIndex]);
								//---if (rel!=null)
								if (rel.getParentSynset() != null)
								{
								//decomentarea acestor  linii de mai jos si comentarea liniei rel.getDepth()<6 
								//are ca efect folosirea distantei Jiang
									
									//--splength = rel.getDepth();
									splength = rel.getLength();
									//System.out.println("sp="+splength);
									//WordNetHelper.printRelationship(rel);
									pathHash.put(key, splength);
									pathHash.put(key1,splength);
									//---Synset lca = WordNetHelper.getLCA(rel);
									Synset lca = rel.getParentSynset();
									if (lca != null)
									{
										String lcaString = lca.getWord(0).getLemma();
										if (searchHash.get(lcaString) == null)
										{
											long nr = WordCounter.getCount(lcaString);
											searchHash.put(lcaString, nr);
										}
										dist = Distance.getDistance(searchHash.get(lcaString), searchHash.get(word1), searchHash.get(word2),distance);
										
										distanceHash.put(key, dist);//adaugam in hash
										distanceHash.put(key1, dist);
									}
									else
									{
										dist = Double.MAX_VALUE;
										distanceHash.put(key, Double.MAX_VALUE);
										distanceHash.put(key1, Double.MAX_VALUE);
									}
								//comentam cele 3 linii de mai jos pentru folosirea distantei Jiang
								/*dist = (double)rel.getDepth();
								hash.put(key, dist);//adaugam in hash
								hash.put(key1, dist);*/
								}
								else
								{
									//System.out.println("no rel found");
									dist = Double.MAX_VALUE;
									distanceHash.put(key, Double.MAX_VALUE);
									distanceHash.put(key1, Double.MAX_VALUE);
									pathHash.put(key, Integer.MAX_VALUE);
									pathHash.put(key1, Integer.MAX_VALUE);
								}
							}
							else
							{
								splength = pathHash.get(key);
							}
							
							//System.out.println(key + " "+splength);

							//if (dist<6)
							if ((splength <= 1) || ((splength <= LENGTH_LIMIT) && (dist > limit_inf) && (dist < limit_sup))) //Jiang
							{
								found++;
							}
							if ((splength <= 1) || ((word2.length() > 2) && (word1.startsWith(word2)))
								|| ((word2.startsWith(word1)) && (word1.length() > 2)))
							{
								//System.out.println("depth = "+splength);
								found = Integer.MAX_VALUE;
								k = chain.size();
							}
							
					}
					if (found >= chainList.elementAt(j).size() * FRACTION)
					{
						chainList.elementAt(j).add(uttwords.elementAt(i));
						chainpoz.elementAt(j).add(i);
						chainListDistinct.elementAt(j).add(uttwords.elementAt(i));
						chainHash.put(indWords[i].getLemma(), j);
						j = chainList.size();
						ok = 1;
						
					}
				}
				
				if (ok == 0 )
				{
					chainList.add(new Vector<UttTaggedWord>());
					chainList.elementAt(chainList.size() - 1).add(uttwords.elementAt(i));
					chainListDistinct.add(new Vector<UttTaggedWord>());
					chainListDistinct.elementAt(chainList.size() - 1).add(uttwords.elementAt(i));
					chainpoz.add(new Vector<Integer>());
					chainpoz.elementAt(chainList.size() - 1).add(i);
					
					chainHash.put(indWords[i].getLemma(), chainList.size() - 1);
					
				}
			}
		}

		long t2 = System.currentTimeMillis();
		
		if (LexChain.DEBUG_PRINT)
			System.out.println("time for chaining : " + (t2 - t1));
		
		return new DistinctChains(chainListDistinct,numberHash,chainList);
	}
	
	
	public static DistinctChains createChains(Vector<UttTaggedWord> uttwords,int distance) throws JWNLException,IOException
	{
		
		Vector<Vector<UttTaggedWord>> chainList = new Vector<Vector<UttTaggedWord>>();
		Vector<Vector<Integer>> chainpoz = new Vector<Vector<Integer>>();
		Vector<Vector<UttTaggedWord>> chainListDistinct = new Vector<Vector<UttTaggedWord>>();
		int i,j,k;
		IndexWord[] indWords = new IndexWord[uttwords.size()];
		Hashtable<String,Double> distanceHash = new Hashtable<String,Double>();
		Hashtable<String,Long> searchHash = new Hashtable<String, Long>();
		Hashtable<String,Integer> pathHash = new Hashtable<String,Integer>();
		Hashtable<String,Integer> chainHash = new Hashtable<String,Integer>();
		Hashtable<String,Integer> numberHash = new Hashtable<String, Integer>();
	
		long t1 = System.currentTimeMillis();
		//cautare frecventa online
		for (i = 0; i < uttwords.size(); i++)
		{
			
			indWords[i] = uttwords.elementAt(i).getIndexWord();
			String lema = indWords[i].getLemma();
			if (numberHash.get(lema) == null)
			{
				numberHash.put(lema, 1);
			}
			else
			{
				Integer count = numberHash.get(lema);
				numberHash.remove(lema);
				numberHash.put(lema, count+1);
			}
			if (searchHash.get(lema) == null)
			{
				long nr = WordCounter.getCount(lema);
				//long nr = 1;
				searchHash.put(lema, nr);
			}
			
			if (LexChain.DEBUG_PRINT)
				System.out.println("searched " + i);
		}
		double limit_inf = LexicalChainer.limits[2 * distance];
		double limit_sup = LexicalChainer.limits[2 * distance + 1];
		for ( i = 0;i<uttwords.size();i++)
		{
			if (LexChain.DEBUG_PRINT)
				System.out.println(i);
			
			int ok = 0;
			Integer chainIndex = chainHash.get(uttwords.elementAt(i).getIndexWord().getLemma());
			if (chainIndex != null)
			{
				ok = 1;
				chainList.elementAt(chainIndex).add(uttwords.elementAt(i));
				chainpoz.elementAt(chainIndex).add(i);
				
				
			}
			else
			{
				
				for ( j = 0; j < chainList.size(); j++)
				{
					Vector<UttTaggedWord> chain = chainList.elementAt(j);
					int found = 0;
					for (k = 0; k < chain.size(); k++)
					{
						Relationship rel;
							int wordIndex = chainpoz.elementAt(j).elementAt(k);
							//System.out.println(i+" "+j+" "+k);

							String word1 = indWords[i].getLemma();
							String word2 = indWords[wordIndex].getLemma();
							String key = word1 + " " + word2;
							String key1 = word2 + " " + word1;//cele 2 cuv concatenate, in ambele moduri posibile
							Double dist = distanceHash.get(key);
							int splength = Integer.MAX_VALUE;
							if (dist == null)//daca nu se afla in hash distanta intre cele 2 cuvinte
							{
								rel = WordNetHelper.getInstance().getShortestPath(indWords[i], indWords[wordIndex]);
								if (rel != null)
								{
								//decomentarea acestor  linii de mai jos si comentarea liniei rel.getDepth()<6 
								//are ca efect folosirea distantei Jiang
									
									splength = rel.getDepth();
									//System.out.println("sp="+splength);
									//WordNetHelper.printRelationship(rel);
									pathHash.put(key, splength);
									pathHash.put(key1,splength);
									Synset lca = WordNetHelper.getInstance().getLCA(rel);
									if (lca != null)
									{
										String lcaString = lca.getWord(0).getLemma();
										if (searchHash.get(lcaString) == null)
										{
											long nr = WordCounter.getCount(lcaString);
											searchHash.put(lcaString, nr);
										}
										dist = Distance.getDistance(searchHash.get(lcaString), searchHash.get(word1), searchHash.get(word2), distance);
										
										distanceHash.put(key, dist);//adaugam in hash
										distanceHash.put(key1, dist);
									}
									else
									{
										dist = Double.MAX_VALUE;
										distanceHash.put(key, Double.MAX_VALUE);
										distanceHash.put(key1, Double.MAX_VALUE);
									}
								//comentam cele 3 linii de mai jos pentru folosirea distantei Jiang
								/*dist = (double)rel.getDepth();
								hash.put(key, dist);//adaugam in hash
								hash.put(key1, dist);*/
								}
								else
								{
									//System.out.println("no rel found");
									dist = Double.MAX_VALUE;
									distanceHash.put(key, Double.MAX_VALUE);
									distanceHash.put(key1, Double.MAX_VALUE);
									pathHash.put(key, Integer.MAX_VALUE);
									pathHash.put(key1, Integer.MAX_VALUE);
								}
							}
							else
							{
								splength = pathHash.get(key);
							}
							

							//if (dist<6)
							if ((splength <= 1) || ((splength <= LENGTH_LIMIT)/*&&(dist>limit_inf)&&(dist<limit_sup)*/)) //Jiang
							{
								found++;
							}
							if ((splength <= 1) || ((word2.length() > 2) && (word1.startsWith(word2)))
									|| ((word2.startsWith(word1)) && (word1.length() > 2)))
							{
								//System.out.println("depth = "+splength);
								found = Integer.MAX_VALUE;
								k = chain.size();
							}
							
					}
					if (found >= chainList.elementAt(j).size() * FRACTION)
					{
						chainList.elementAt(j).add(uttwords.elementAt(i));
						chainpoz.elementAt(j).add(i);
						chainListDistinct.elementAt(j).add(uttwords.elementAt(i));
						chainHash.put(indWords[i].getLemma(), j);
						j = chainList.size();
						ok = 1;
						
					}
				}
				
				if (ok == 0 )
				{
					chainList.add(new Vector<UttTaggedWord>());
					chainList.elementAt(chainList.size() - 1).add(uttwords.elementAt(i));
					chainListDistinct.add(new Vector<UttTaggedWord>());
					chainListDistinct.elementAt(chainList.size() - 1).add(uttwords.elementAt(i));
					chainpoz.add(new Vector<Integer>());
					chainpoz.elementAt(chainList.size() - 1).add(i);
					
					chainHash.put(indWords[i].getLemma(), chainList.size() - 1);
					
				}
			}
		}

		long t2 = System.currentTimeMillis();
		
		if (LexChain.DEBUG_PRINT)
			System.out.println("time for chaining : " + (t2 - t1));
		
		return new DistinctChains(chainListDistinct,numberHash,chainList);
	}
	public static DistinctChains createWSDChains(Vector<UttTaggedSenseWord> uttwords,Hashtable<String, Integer> numberHash,Hashtable<String, Long> searchHash,int distance) throws JWNLException,IOException
	{
		
		Vector<Vector<UttTaggedSenseWord>> chainList = new Vector<Vector<UttTaggedSenseWord>>();
		Vector<Vector<Integer>> chainpoz = new Vector<Vector<Integer>>();
		Vector<Vector<UttTaggedSenseWord>> chainListDistinct = new Vector<Vector<UttTaggedSenseWord>>();
		int i,j,k;
		Synset[] indSyns = new Synset[uttwords.size()];
		Hashtable<String,Double> distanceHash = new Hashtable<String,Double>();
		
		Hashtable<String,Integer> pathHash = new Hashtable<String,Integer>();
		Hashtable<String,Integer> chainHash = new Hashtable<String,Integer>();
		
		long t1 = System.currentTimeMillis();
		
		double limit_inf = LexicalChainer.limits[2*distance];
		double limit_sup = LexicalChainer.limits[2*distance+1];
		for ( i = 0;i < uttwords.size(); i++)
		{
		
			indSyns[i] = uttwords.elementAt(i).getSense();
		}
		for ( i = 0; i < uttwords.size(); i++)
		{
		
			if (LexChain.DEBUG_PRINT)
				System.out.println(i);
			
			int ok = 0;
			Integer chainIndex = chainHash.get(uttwords.elementAt(i).getIndexWord().getLemma());
			if (chainIndex != null)
			{
				ok = 1;
				chainList.elementAt(chainIndex).add(uttwords.elementAt(i));
				chainpoz.elementAt(chainIndex).add(i);
				
				
			}
			else
			{
				
				for (j = 0; j < chainList.size(); j++)
				{
					Vector<UttTaggedSenseWord> chain = chainList.elementAt(j);
					int found = 0;
					for (k = 0; k < chain.size(); k++)
					{
						//---Relationship rel;
						RelParent rel;
							int wordIndex = chainpoz.elementAt(j).elementAt(k);
							//System.out.println(i+" "+j+" "+k);

							//fara wsd
							//String word1 = uttwords.elementAt(i).getIndexWord().getLemma();
							//String word2 = uttwords.elementAt(wordIndex).getIndexWord().getLemma();
							String word1lema = uttwords.elementAt(i).getIndexWord().getLemma();
							String word2lema = uttwords.elementAt(wordIndex).getIndexWord().getLemma();
							String word1 = uttwords.elementAt(i).getIndexWord().getLemma()+" "+uttwords.elementAt(i).getIndexWord().getPOS()+" "+uttwords.elementAt(i).getSense().getOffset();
							String word2 = uttwords.elementAt(wordIndex).getIndexWord().getLemma()+" "+uttwords.elementAt(wordIndex).getIndexWord().getPOS()+" "+uttwords.elementAt(wordIndex).getSense().getOffset();
							String key = word1 + " " + word2;
							String key1 = word2 +" " + word1;//cele 2 cuv concatenate, in ambele moduri posibile
							Double dist = distanceHash.get(key);
							int splength = Integer.MAX_VALUE;
							if (dist == null)//daca nu se afla in hash distanta intre cele 2 cuvinte
							{
							
								rel = WordNetHelper.getInstance().areRelated(indSyns[i], indSyns[wordIndex]);
								//---if (rel!=null)
								if (rel.getParentSynset() != null)
								{
								//decomentarea acestor  linii de mai jos si comentarea liniei rel.getDepth()<6 
								//are ca efect folosirea distantei Jiang
									
									//--splength = rel.getDepth();
									splength = rel.getLength();
									//System.out.println("sp="+splength);
									//WordNetHelper.printRelationship(rel);
									pathHash.put(key, splength);
									pathHash.put(key1,splength);
									//---Synset lca = WordNetHelper.getLCA(rel);
									Synset lca = rel.getParentSynset();
									if (lca != null)
									{
										String lcaString = lca.getWord(0).getLemma();
										if (searchHash.get(lcaString) == null)
										{
											long nr = WordCounter.getCount(lcaString);
											searchHash.put(lcaString, nr);
										}
										dist = Distance.getDistance(searchHash.get(lcaString), searchHash.get(word1lema), searchHash.get(word2lema),distance);
										
										distanceHash.put(key, dist);//adaugam in hash
										distanceHash.put(key1, dist);
									}
									else
									{
										dist = Double.MAX_VALUE;
										distanceHash.put(key,Double.MAX_VALUE);
										distanceHash.put(key1,Double.MAX_VALUE);
									}
								//comentam cele 3 linii de mai jos pentru folosirea distantei Jiang
								/*dist = (double)rel.getDepth();
								hash.put(key, dist);//adaugam in hash
								hash.put(key1, dist);*/
								}
								else
								{
									//System.out.println("no rel found");
									dist = Double.MAX_VALUE;
									distanceHash.put(key,Double.MAX_VALUE);
									distanceHash.put(key1,Double.MAX_VALUE);
									pathHash.put(key, Integer.MAX_VALUE);
									pathHash.put(key1,Integer.MAX_VALUE);
								}
							}
							else
							{
								splength = pathHash.get(key);
							}
							
							//System.out.println(key + " "+splength);

							//if (dist<6)
							if ((splength <= 1) || ((splength <= LENGTH_LIMIT)/*&&(dist>limit_inf)&&(dist<limit_sup)*/)) //Jiang
							{
								found++;
							}
							if ((splength <= 1) || ((word2lema.length() > 2) && (word1lema.startsWith(word2lema)))
									|| ((word2lema.startsWith(word1lema)) && (word1lema.length() > 2)))
							{
								//System.out.println("depth = "+splength);
								found = Integer.MAX_VALUE;
								k = chain.size();
							}
							
					}
					if (found >= chainList.elementAt(j).size() * FRACTION)
					{
						chainList.elementAt(j).add(uttwords.elementAt(i));
						chainpoz.elementAt(j).add(i);
						chainListDistinct.elementAt(j).add(uttwords.elementAt(i));
						chainHash.put(uttwords.elementAt(i).getIndexWord().getLemma(), j);
						j = chainList.size();
						ok = 1;
						
					}
				}
				
				if (ok == 0 )
				{
					chainList.add(new Vector<UttTaggedSenseWord>());
					chainList.elementAt(chainList.size() - 1).add(uttwords.elementAt(i));
					chainListDistinct.add(new Vector<UttTaggedSenseWord>());
					chainListDistinct.elementAt(chainList.size() - 1).add(uttwords.elementAt(i));
					chainpoz.add(new Vector<Integer>());
					chainpoz.elementAt(chainList.size() - 1).add(i);
					
					chainHash.put(uttwords.elementAt(i).getIndexWord().getLemma(), chainList.size() - 1);
					
				}
			}
		}
		long t2 = System.currentTimeMillis();
		
		if (LexChain.DEBUG_PRINT)
			System.out.println("time for chaining : " + (t2-t1));
		
		return new DistinctChains(chainListDistinct,numberHash,chainList);
	}
}
							

 