
package chatsegmenter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.Synset;
import chatsegmenter.helper.Position;
import chatsegmenter.helper.UttTaggedSenseWord;
import chatsegmenter.helper.UttTaggedWord;
import chatsegmenter.helper.WordCounter;
import chatsegmenter.helper.WordNetHelper;
import chatsegmenter.lexicalchainer.DistinctChains;
import chatsegmenter.lexicalchainer.LexicalChainer;
import chatsegmenter.parser.Parser;
import chatsegmenter.parser.model.Chat;
import chatsegmenter.postagger.POSTagger;
import chatsegmenter.stemmer.Stemmer;
import chatsegmenter.wsd.WSDisambiguator;
import edu.stanford.nlp.ling.TaggedWord;

/**
 * This class should be used in order to retrieve information such as POS-tagged words, 
 * word sense disambiguation or lexical chains.
 * 
 * @author Andrei Janca
 *
 */

public class LexChain {
	
	/**
	 * When this fiels is set on <b>true</b>, then debugging and 
	 * auxiliary info will be printed: the number of words, semantic relations found etc.
	 */
	public static boolean DEBUG_PRINT = false; // afisarea informatiilor intermediare, eventual 
													//utile pt. debugging
	public static final int WSD_WINDOW = 9; //window limit
	
	static
	{
		DEBUG_PRINT = Boolean.parseBoolean(SystemParam.getInstance().getParam("DEBUG_PRINT"));
		System.out.println("debug print " + DEBUG_PRINT);
	}
	
	/**
	 * This method finds the lexical chains based on an array of sense disambiguated words 
	 * and writes the output in a text file.
	 * 
	 * 
	 * @param words      An array of UttTaggedSenseWord
	 * 
	 * @param outputFileName The name of a file name in which the output will be written
	 * 
	 * @throws IOException
	 */
	
	public static void lexicalChainsFromWSD(Vector<UttTaggedSenseWord> words, 
											String outputFileName) throws IOException
	{
		Hashtable<String,Integer> numberHash = new Hashtable<String, Integer>();
		Hashtable<String,Long> searchHash = new Hashtable<String, Long>();
		
		for (int i = 0 ; i < words.size(); i++)
		{
			
			String lema = words.elementAt(i).getIndexWord().getLemma().toLowerCase();
			
			if (numberHash.get(lema) == null)
			{
				numberHash.put(lema, 1);
			}
			else
			{
				Integer count = numberHash.get(lema);
				numberHash.remove(lema);
				numberHash.put(lema, count + 1);
			}
			if (searchHash.get(lema) == null)
			{
				long nr = WordCounter.getCount(lema);
				searchHash.put(lema, nr);
			}
			
			if (DEBUG_PRINT)
				System.out.println("searched " + i);
		}
		Hashtable<String, Integer> auxnum = new Hashtable<String, Integer>(numberHash);
		Hashtable<String, Long> auxsearch = new Hashtable<String, Long>(searchHash);
		//lanturile
		for (int i = 0; i <= 0; i++)
		{
			try
			{
				DistinctChains chains  = LexicalChainer.createWSDChains(words, numberHash, searchHash, i);
				BufferedWriter  out1 = new BufferedWriter(new FileWriter(outputFileName));
				out1.write(LexicalChainer.printChainsDistinct(chains));
				out1.close();
				if (DEBUG_PRINT)
					System.out.println("Distance "+i+" ready");
				numberHash = new Hashtable<String, Integer>(auxnum);
				searchHash = new Hashtable<String, Long>(auxsearch);
			}
			catch (JWNLException e){e.printStackTrace();}
		}
	}
	
	
	/**
	 * Method that returns an array of objects {@link UttTaggedSenseWord}, which means a list of words 
	 * associated with their meaning. Receives as input vector obtained after POST-tagging the words. 
	 * If the second parameter is true, the results obtained in this stage will be written 
	 * to the file whose path is represented by the third parameter
	 * 
	 * @param stemwords 		An array of POS-tagged words
	 * @param writeRez		 	{@code true} if output should be written in a text file, {@code false} otherwise
	 * @param outputFileName    The name of a file name in which the output will be written
	 * @return An array of sense tagged words
	 * @throws Exception
	 */
	
	public static Vector<UttTaggedSenseWord> WSDFromWords(Vector<UttTaggedWord> stemwords,boolean writeRez, String outputFileName) throws Exception
	{
		int k = 0;
		
		Vector<UttTaggedSenseWord> wsdWords = new Vector<UttTaggedSenseWord>();
		System.out.println("total words : " + stemwords.size());
		
		while (k < stemwords.size())
		{
			Vector<UttTaggedSenseWord> wsdtempWords = new Vector<UttTaggedSenseWord>();
			Vector<UttTaggedWord> words = new Vector<UttTaggedWord>();
			int uttID = stemwords.elementAt(k).getUttID();
			int start = k ;
			while ((k < stemwords.size()) && (stemwords.elementAt(k).getUttID() < uttID + 3)
					&&(k - start <= WSD_WINDOW))
			{
				words.add(stemwords.elementAt(k));
				k++;
			}
			if (DEBUG_PRINT)
			{
				System.out.println("Nr of words to SD : "+words.size());
				System.out.println("At words : "+start+" "+k);
			}
			WSDisambiguator wsd = new WSDisambiguator(words);
			wsdtempWords = wsd.wsd(); 
			for (int i = 0; i < wsdtempWords.size(); i++)
			{
				wsdWords.add(wsdtempWords.elementAt(i));
			}
		}
		if (writeRez == true)
		{
			BufferedWriter out = new BufferedWriter(new FileWriter(outputFileName));
			for (int j = 0; j < wsdWords.size(); j++)
			{
				UttTaggedSenseWord sdWord = wsdWords.elementAt(j);
				out.write(sdWord.value().toLowerCase()+" "+sdWord.tag()+" "+sdWord.getUttID()+" "+
						  sdWord.getSense().getGloss()+" "+sdWord.getSense().getOffset()+"\n");
			}
			
			out.close();
		}
		return wsdWords;
	}
	
	/**
	 *  Method used for processing a chat from a given file and outputs (if requested) the result in a 
	 *  text file. 
	
	 * @param fileName  		The name of the chat file
	 * @param isXML     		{@code true} if the chat is provided in XML format, {@code false} if it is text file
	 * @param writeRez  		{@code true} if output should be written in a text file, {@code false} otherwise
	 * @param outputFileName 	Name of the file in which results are written (only if writeRez is {@code true})
	 * @return An array of POS-tagged words
	 * @throws Exception
	 */
	public static Vector<UttTaggedWord> wordsFromFile(String fileName,
													  boolean isXML, 
													  boolean writeRez, 
													  String outputFileName) throws Exception
	{
		Vector<UttTaggedWord> taggedWords;
		String delim = " ,.:!?)([]{}\";/\\+=-_@#$%^&*|";
		Vector<Position> positions = new Vector<Position>();
		
		if (isXML)
		{
			Parser parser = new Parser();	
			Chat ch = parser.parseDocument(fileName);//parsare xml
			if (DEBUG_PRINT)
				System.out.println("parsed...");
			taggedWords = POSTagger.tagChat(ch); //pos-tagging
		}
		else
		{
			BufferedReader in = new BufferedReader (new FileReader(fileName));
			String line = new String();
			String text = new String();
			int lineCount = 0;
			while ((line = in.readLine())!=null)
			{
				text = text.concat(line);
				int tkCount = 0;
				StringTokenizer st = new StringTokenizer(line, delim);
				while (st.hasMoreTokens())
				{
					positions.add(new Position(lineCount, tkCount));
					st.nextToken();
					tkCount++;
				}
				lineCount++;
			}
			taggedWords = POSTagger.tagWords(text, positions);
		}
		if (DEBUG_PRINT)
			System.out.println("tagged..");
		
		Vector<UttTaggedWord> stemwords = new Vector<UttTaggedWord>();
		
		for (int j = 0; j < taggedWords.size(); j++)
		{
			UttTaggedWord tw = Stemmer.stemWord(taggedWords.elementAt(j));
			//se returneaza un cuvant corect, 
			//sau null daca acesta nu a fost gasit in dictionar
			
			if (tw != null)
			{
				stemwords.add(tw );//doar daca cuvantul este unul corect,il adaugam
			}
		}
		
		if (DEBUG_PRINT)
			System.out.println("stemmed...");
		
		if (writeRez == true)
		{
			BufferedWriter out = new BufferedWriter(new FileWriter(outputFileName));
			for (int j = 0; j < stemwords.size(); j++)
			{
				UttTaggedWord word = stemwords.elementAt(j);
				out.write(word.value().toLowerCase()+" "+word.tag()+" "+word.getUttID()+"\n");
			}
			
			out.close();
		}
		return stemwords;
	}
	
	/*
	public static void processMultipleChats(String pathOfFiles,String filelist,String output) throws Exception
	{
		Parser parser = new Parser();
		BufferedReader file = new BufferedReader(new FileReader(pathOfFiles+filelist));
		BufferedWriter out = new BufferedWriter(new FileWriter(pathOfFiles+output));
		int vect[] = new int[100];
		String line = new String();
		int c = 0;
		while ((line = file.readLine())!=null)
		{
			vect[c] = Integer.parseInt(line);
			c++;
		}
		for (int i = 0;i<vect.length;i++)
		{
			Chat ch = parser.parseDocument(pathOfFiles+"xmls/"+vect[i]+".xml");//parsare xml
			
			if (DEBUG_PRINT)
				System.out.println("parsed..."+vect[i]);
			
			Vector<UttTaggedWord> taggedWordsChat = POSTagger.tagChat(ch); //pos-tagging
			
			if (DEBUG_PRINT)
				System.out.println("tagged.."+vect[i]);
			
			Vector<UttTaggedWord> stemwords = new Vector<UttTaggedWord>();
			
			for (int j = 0;j<taggedWordsChat.size();j++)
			{
				UttTaggedWord tw = Stemmer.stemWord(taggedWordsChat.elementAt(j));//se returneaza un cuvant corect, sau null daca acesta nu a fost gasit in dictionar
				if (tw!=null)
					stemwords.add(tw );//doar daca cuvantul este unul corect,il adaugam
				
			}
			if (DEBUG_PRINT)
				System.out.println("stemmed..."+vect[i]);
			
			
			BufferedWriter bla = new BufferedWriter(new FileWriter(pathOfFiles+"xmls/"+"words_"+vect[i]+".txt"));
			for (int j = 0;j<stemwords.size();j++)
			{
				bla.write(stemwords.elementAt(j).value().toLowerCase()+" "+stemwords.elementAt(j).tag()+" "+stemwords.elementAt(j).getUttID()+"\n");
				out.write(stemwords.elementAt(j).value().toLowerCase()+" "+stemwords.elementAt(j).tag()+" "+stemwords.elementAt(j).getUttID()+"\n");
			}
			bla.close();
			
		}
		out.close();
	}
	*/
	
	/**
	 * This method reads a list of sense tagged (obtained after word sense disambiguation) words
	 * from a file and stores them in an Vector<UttTaggedSenseWord> in order to be used for
	 * lexical chains computing
	 *  
	 * @param filepath Path of the input text file
	 * @return An array of sense tagged words
	 */
	
	public static Vector<UttTaggedSenseWord> loadSDWordsFromFile(String filepath) throws Exception
	{
		if (DEBUG_PRINT)
			System.out.println("load words from" +filepath+ "...");
		
		BufferedReader file = new BufferedReader(new FileReader(filepath));
		String line = new String("");
		Vector<UttTaggedSenseWord> words = new Vector<UttTaggedSenseWord>();
		
		while ((line = file.readLine()) != null)
		{
			StringTokenizer st = new StringTokenizer(line," ");
			String lema = st.nextToken();
			String pos = st.nextToken();
			int id = Integer.parseInt(st.nextToken());
			String token = new String("");
			while (st.hasMoreTokens())
			{
				token = st.nextToken();
			}
			long offset = Long.parseLong(token);
			UttTaggedWord aux = new UttTaggedWord(new TaggedWord(lema,pos),id);
			IndexWord iw = WordNetHelper.getInstance().getWord(POSTagger.getPosFromTag(aux), lema);
			aux.setIndexWord(iw);
			Synset senses[] = iw.getSenses();
			Synset sense = null;
			
			for (int i = 0; i < senses.length; i++)
			{
				if ((senses[i].getPOS() == POSTagger.getPosFromTag(aux))
					 &&(senses[i].getOffset() == offset))
				{
					sense = senses[i];
				}
			}
			words.add(new UttTaggedSenseWord(aux,sense));
		}
		if (DEBUG_PRINT)
			System.out.println("words loaded...");
		
		return words;
	}
	
	/**
	 * This method reads a list of POS-tagged (obtained after POS-tagging) words
	 * from a file and stores them in an Vector<UttTaggedWord> in order to be used for
	 * the word sense disambiguation stage and lexical chains computing
	 *  
	 * @param filepath Path of the input text file
	 * @return An array of sense tagged words
	 */
	
	public static Vector<UttTaggedWord>  loadWordsFromFile(String filepath) throws Exception
	{
		BufferedReader file = new BufferedReader(new FileReader(filepath));
		String line = new String("");
		Vector<UttTaggedWord> words = new Vector<UttTaggedWord>();
		
		while ((line = file.readLine()) != null)
		{
			StringTokenizer st = new StringTokenizer(line," ");
			String lema = st.nextToken();
			String pos = st.nextToken();
			int id = Integer.parseInt(st.nextToken());
			UttTaggedWord aux = new UttTaggedWord(new TaggedWord(lema,pos), id);
			IndexWord iw = WordNetHelper.getInstance().getWord(POSTagger.getPosFromTag(aux), lema);
			aux.setIndexWord(iw);
			words.add(aux);
			
		}
		if (DEBUG_PRINT)
			System.out.println("words loaded...");
		
		return words;
	}
	
}
