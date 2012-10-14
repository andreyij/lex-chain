package chatsegmenter.helper;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.*;
/**
 * Class used for retrieving the probability of encountering a given word. Since a relevant corpus was
 * not available for this task, the number of hits returned by a Google search of that word is returned.
 * This information is needed by the semantic distance computation methods.
 * 
 * @author Andrei
 *
 */
// metoda propriu-zisa
	public class WordCounter
	{
		/**
		 * Returns the hits of Google search of the given string
		 * @param word			The string representing the word to search for
		 * @return				The number of Google results
		 */
		
	public static long getCount(String word){ // se conecteaza la google, citeste informatia de pe pagina si intoarce numarul de hituri pentru cuvantul 'word'

		String page;
		
		page = "http://www.google.com/search?hl=en&q=" + word+ "&btnG=Search"; // creez linkul google pentru cautarea cuvantului 'word'
		//page = "http://www.google.ro/search?hl=ro&source=hp&q="+word+"google&btnG=Cautare+Google&meta=&aq=f&oq=";
		long numarLong = -1; // numarul de hituri ce va fi intors de metoda

		try{
			URL url = new URL(page); // creez urlul la care ma conectez
			HttpURLConnection huc = (HttpURLConnection) url.openConnection(); // realizez conexiunea
			
			String line = null; // se citeste cate o linie din pagina
			String pag = new String(""); // string ce contine pagina respectiva
			int index; // index folosit pentru citirea numarului de hituri
			String numar=""; // numarul in format string
			
			
			huc.setRequestMethod("GET"); // sunt trimise header-ele
			huc.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; JVM)");
			huc.setRequestProperty("Pragma", "no-cache");
			huc.connect(); // conectarea efectiva

			BufferedReader reader = new BufferedReader(new InputStreamReader(huc.getInputStream())); //citesc informatia primita
			//pag = "";
			
			while ((line = reader.readLine()) != null)
				pag = pag + line; // memorez informatia
		
			huc.disconnect(); // ma deconectez

			//return pag; // am memorat continutul paginii

			index = pag.indexOf("Results"); // ma pozitionez in pagina pentru a lua rezultatul
			index = pag.indexOf("of about",index);
			index = pag.indexOf("<b>",index);
			index = index + 3;
			while (pag.charAt(index)!='<') // iau cuvantul sub forma string (< vine de la </b>)
			{
				numar = numar + pag.charAt(index);
				index ++;
			}
			
			numar = numar.replace(",",""); // elimin virgulele
			numar = numar.replace(".",""); // elimin punctele

			try{ 
				numarLong = Long.parseLong(numar); // se transforma cuvantul intr-un long
				return numarLong;
			} catch (Exception e) {
				System.out.println("Nu merge transformarea cuvantului intr-un long!");
				e.printStackTrace();
				//System.exit(-1);
				}

			return -1;

		} catch (Exception e) { 
			System.out.println("Conexiunea cu google nu a putut fi stabilita!");
			e.printStackTrace();
			//System.exit(-1);
			}
		
		return -1;
		//return 1;
	}
	}
