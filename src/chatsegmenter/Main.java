package chatsegmenter;
import chatsegmenter.helper.*;
import java.util.*;

public class Main {
	

	public static void main(String args[]) throws Exception
	{
		int argc = args.length;

		String usageMessage = "Wrong usage!\n" + "Usage: OPTIONS [--debug-print] [--output-all] \n" + "Use --help for more information\n";
		switch (argc)
		{
			case 1:
				if (args[0].equals("-h") || args[0].equals("--help"))
				{
					System.out.println("OPTIONS:");
					System.out.println("-p chat_file out_file...................process xml from  chat_file and output lexical chains in out_file");
					System.out.println("-ptext text_file out_file...............process words from text_file and output lexical chains in out_file");
					System.out.println("--wsd file out_file.....................process list of POS-tagged words from file and out WSD words in out_file");
					System.out.println("--lc wsd_file out_file..................process list of WSD words from wsd_file and output lexical chain in out_file");
					System.out.println("--ps chat_file out_file.................process xml from chat_file and output POS-tagged words in out_file");
					System.out.println("--pstext text_file out_file.............process words from text_file and output POS-tagged words in out_file");
					
				}
				else
				{
					System.out.println(usageMessage);
				}
				break;
			case 2:
				System.out.println(usageMessage);
				break;
			default:
				System.out.println(args[0]);
				if ((args[0].equals("-p")==false) && (args[0].equals("--wsd")==false) &&
				   (args[0].equals("--lc")==false) && (args[0].equals("--ps")==false) && 
				   (args[0].equals("-ptext")==false) && (args[0].equals("--pstext")==false))
				{
					System.out.println(usageMessage);
				}
				else
				{
					String posOutputFile = null;
					String wsdOutputFile = null;
					
					LexChain.DEBUG_PRINT = false;
					
					if (argc > 3)
					{
						for (int i = 3; i < argc; i++)
						{
							if (args[i].equals("--debug-print"))
							{
								LexChain.DEBUG_PRINT = true;
							}
							if (args[i].equals("--output-all"))
							{
								posOutputFile = new String (args[1] + "_pos.txt");
								wsdOutputFile = new String (args[1] + "_wsd.txt");
							}
						}
					}
					WordNetHelper.getInstance().initialize(SystemParam.getInstance().getParam("WORDNET_PROPERTIES"));
						if (args[0].equals( "-p"))
						{
							Vector<UttTaggedWord> words = LexChain.wordsFromFile(args[1], true, false, posOutputFile);
							Vector<UttTaggedSenseWord> wsdWords = LexChain.WSDFromWords(words, false, wsdOutputFile);
							LexChain.lexicalChainsFromWSD(wsdWords, args[2]);
							break;
						}
						if (args[0].equals( "--wsd"))
						{
							Vector<UttTaggedWord> words = LexChain.loadWordsFromFile(args[1]);
							LexChain.WSDFromWords(words, true, args[2]);
							break;
						}
						if (args[0].equals( "--lc"))
						{
							Vector<UttTaggedSenseWord> wsdWords = LexChain.loadSDWordsFromFile(args[1]);
							LexChain.lexicalChainsFromWSD(wsdWords, args[2]);
							break;
						}
						if (args[0].equals( "--ps"))
						{
							LexChain.wordsFromFile(args[1], true,true, args[2]);
							break;
						}
						if (args[0].equals("-ptext"))
						{
							Vector<UttTaggedWord> words = LexChain.wordsFromFile(args[1], false, false, posOutputFile);
							Vector<UttTaggedSenseWord> wsdWords = LexChain.WSDFromWords(words, false, wsdOutputFile);
							LexChain.lexicalChainsFromWSD(wsdWords, args[2]);
							break;
						}
						if (args[0].equals( "--pstext"))
						{
							LexChain.wordsFromFile(args[1], false,true, args[2]);
							break;
						}
						
							
				}
				break;
			
		}
				
	}
}
