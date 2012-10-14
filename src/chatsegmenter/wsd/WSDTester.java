package chatsegmenter.wsd;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.StringTokenizer;
import java.util.Vector;

import net.didion.jwnl.data.IndexWord;
import chatsegmenter.helper.UttTaggedSenseWord;
import chatsegmenter.helper.UttTaggedWord;
import chatsegmenter.helper.WordNet;
import chatsegmenter.helper.WordNetHelper;
import chatsegmenter.postagger.POSTagger;
import chatsegmenter.postagger.POSTagger;
import edu.stanford.nlp.ling.TaggedWord;

public class WSDTester {
	public static Vector<UttTaggedWord>  loadWordsFromFile(String filepath) throws Exception
	{
		BufferedReader file = new BufferedReader(new FileReader(filepath));
		String line = new String("");
		Vector<UttTaggedWord> words = new Vector<UttTaggedWord>();
		while ((line=file.readLine())!=null)
		{
			StringTokenizer st = new StringTokenizer(line," ");
			String lema = st.nextToken();
			String pos = st.nextToken();
			int id = Integer.parseInt(st.nextToken());
			UttTaggedWord aux = new UttTaggedWord(new TaggedWord(lema,pos),id);
			IndexWord iw = WordNetHelper.getInstance().getWord(POSTagger.getPosFromTag(aux), lema);
			aux.setIndexWord(iw);
			words.add(aux);
			
			
		}
		System.out.println("words loaded...");
		return words;
	}
	public static void main(String args[]) throws Exception
	{
		String pathOfFiles = new String("other/chseg/");
		String arg = new String("test");
		WordNetHelper.getInstance().initialize(pathOfFiles+"file_properties.xml");
		Vector<UttTaggedWord> words = loadWordsFromFile(pathOfFiles+"words_"+arg+".txt");
		WSDisambiguator disambiguator = new WSDisambiguator(words);
		Vector<UttTaggedSenseWord> sdWords = disambiguator.wsd();
		
		BufferedWriter bla = new BufferedWriter(new FileWriter(pathOfFiles+"wsd_"+arg+".txt"));
		for (int j = 0;j<sdWords.size();j++)
		{
			UttTaggedSenseWord sdWord = sdWords.elementAt(j);
			bla.write(sdWord.value()+" "+sdWord.tag()+" "+sdWord.getUttID()+" "+" "+sdWord.getSense().getGloss()+" "+sdWord.getSense().getOffset()+"\n");

		}
		bla.close();
		
	}

}
