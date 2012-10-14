/**
 * 
 */

package chatsegmenter.parser;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import chatsegmenter.SystemParam;
import chatsegmenter.SystemParameters;
import chatsegmenter.parser.model.Chat;
import chatsegmenter.parser.model.Participant;
import chatsegmenter.parser.model.Utterance;
import chatsegmenter.parser.model.Weights;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import com.sun.org.apache.xml.internal.serialize.*;

/**
 * The main class used for parsing an XML file as an input
 * 
 * @author Mihai Dascalu
 * 
 */
public class Parser {

	public static Weights weights;
	// initialization of weights from configuration file
	public Parser ()
	{
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document d = db.parse(this.getClass().getClassLoader().
								getResourceAsStream(SystemParam.getInstance().
										getParam("PARSER_CONF")));
			Element doc = d.getDocumentElement();

			weights = new Weights();
			weights.setNoChars(Double.valueOf(((Element) doc
					.getElementsByTagName("noChars").item(0))
					.getAttribute("value")));
			weights.setNoCharsUtterance(Double.valueOf(((Element) doc
					.getElementsByTagName("noCharsUtterance").item(0))
					.getAttribute("value")));
			weights.setInDegreeNo(Double.valueOf(((Element) doc
					.getElementsByTagName("inDegreeNo").item(0))
					.getAttribute("value")));
			weights.setOutDegreeNo(Double.valueOf(((Element) doc
					.getElementsByTagName("outDegreeNo").item(0))
					.getAttribute("value")));
			weights.setRankNo(Double.valueOf(((Element) doc
					.getElementsByTagName("rankNo").item(0))
					.getAttribute("value")));
			weights.setEigenNo(Double.valueOf(((Element) doc
					.getElementsByTagName("eigenNo").item(0))
					.getAttribute("value")));
			weights.setInDegreeMark(Double.valueOf(((Element) doc
					.getElementsByTagName("inDegreeMark").item(0))
					.getAttribute("value")));
			weights.setOutDegreeMark(Double.valueOf(((Element) doc
					.getElementsByTagName("outDegreeMark").item(0))
					.getAttribute("value")));
			weights.setRankMark(Double.valueOf(((Element) doc
					.getElementsByTagName("rankMark").item(0))
					.getAttribute("value")));
			weights.setEigenMark(Double.valueOf(((Element) doc
					.getElementsByTagName("eigenMark").item(0))
					.getAttribute("value")));
		} catch (Exception e) {
			System.err.println("Error reading configuration file!");
			e.printStackTrace();
		}
	}
	/*public void splitDocument(String path)
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document dom = db.parse(new File(path));

			Element doc = dom.getDocumentElement();

			

			// determine number of participants
			NodeList nl = doc.getElementsByTagName("Dialog");
			for (int i = 0;i<nl.getLength();i++)
			{
				Element node = (Element)nl.item(i);
				DocumentBuilder db1 = dbf.newDocumentBuilder();
				Document dom1 = db1.newDocument();
				Element root = (Element)dom1.importNode(node, true);
				dom1.appendChild(root);
				
				try
				{
					OutputFormat format = new OutputFormat(dom);
					format.setIndenting(true);
					XMLSerializer serializer = new XMLSerializer(
					new FileOutputStream(new File("other/chseg/xmls/"+i+".xml")), format);

					serializer.serialize(dom1);

				} catch(IOException ie) {
				    ie.printStackTrace();
				}

			}
			
		} catch (Exception e) {
			System.err.print("Error evaluating input file: "+path+"!");
			e.printStackTrace();
		}
	}*/
	
	public Chat parseDocument(String path) {
		Chat c = new Chat();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document dom = db.parse(new File(path));

			Element doc = dom.getDocumentElement();

			Element el;

			ArrayList<String> names = new ArrayList<String>();
			int idMax = 0;
			int noChars = 0;
			NodeList nl1, nl2;

			// determine number of participants
			nl1 = doc.getElementsByTagName("Turn");
			if (nl1 != null && nl1.getLength() > 0) {
				for (int i = 0; i < nl1.getLength(); i++) {
					el = (Element) nl1.item(i);
					String name_aux = el.getAttribute("nickname");
					int no = names.indexOf(name_aux);
					if (no == -1) {
						names.add(name_aux);
						c.getParticipants()
								.add(
										new Participant(c.getNoParticipants(),
												name_aux));
						c.setNoParticipants(c.getNoParticipants() + 1);
					}
				}
			}

			nl2 = doc.getElementsByTagName("Event");
			if (nl2 != null && nl2.getLength() > 0) {
				for (int i = 0; i < nl2.getLength(); i++) {
					el = (Element) nl2.item(i);
					int id = Integer.parseInt(el.getAttribute("genid"));
					if (id > idMax)
						idMax = id;
				}
			}

			// utterance determination

			nl1 = doc.getElementsByTagName("Turn");
			if (nl1 != null && nl1.getLength() > 0) {
				for (int i = 0; i < nl1.getLength(); i++) {
					el = (Element) nl1.item(i);
					String name_aux = el.getAttribute("nickname");
					int no = names.indexOf(name_aux);
					if (no < 0)
						System.err
								.println("Error parsing input (user has not been signed in)!");
					nl2 = el.getElementsByTagName("Utterance");
					if (nl2 != null && nl2.getLength() > 0) {
						for (int j = 0; j < nl2.getLength(); j++) {
							el = (Element) nl2.item(j);
							int index = -2;		// initialized to -2
							try {
								index = Integer
										.parseInt(el.getAttribute("ref"));
							} catch (Exception e) {
								index = -2;
							}
							int id = Integer.parseInt(el.getAttribute("genid"));
							if (el.getFirstChild() != null) {
								Utterance r = new Utterance(id, index, no, el
										.getFirstChild().getNodeValue());
								if (id > idMax)
									idMax = id;

								// TODO utterance evaluation

								if (index != -1) {
									for (int k = 0; k < c.getNoParticipants(); k++) {
										if ((c.getParticipants().get(k))
												.searchUtterance(index) == true) {
											r.setParticipantId(k);
										}
									}
								}
								(c.getParticipants().get(no)).addUtterance(r);
								noChars += r.getText().length();
							}
						}
					}
				}
			}

			// build the utterance graph
			for (int i = 0; i <= idMax; i++) {
				c.getUtterances().add(i, null);
				c.getUtteranceGraph().add(i, null);
			}
			doc = dom.getDocumentElement();
			// determine utterances
			nl1 = doc.getElementsByTagName("Turn");
			if (nl1 != null && nl1.getLength() > 0) {
				for (int i = 0; i < nl1.getLength(); i++) {
					el = (Element) nl1.item(i);
					String nume1 = el.getAttribute("nickname");
					int nr = names.indexOf(nume1);
					if (nr < 0)
						System.err.println("Error!");
					nl2 = el.getElementsByTagName("Utterance");
					if (nl2 != null && nl2.getLength() > 0) {
						for (int j = 0; j < nl2.getLength(); j++) {
							el = (Element) nl2.item(j);
							int index = -2;
							try {
								index = Integer
										.parseInt(el.getAttribute("ref"));
							} catch (Exception e) {
								index = -2;
							}
							int id = Integer.parseInt(el.getAttribute("genid"));
							if ((index != -1) && (index != -2) 
								&& (el.getFirstChild() != null)) {
								if (c.getUtteranceGraph().get(index) == null) {
									c.getUtteranceGraph().set(index,
											new LinkedList<Integer>());
									c.getUtteranceGraph().get(index).add(
											new Integer(id));
								} else {
									c.getUtteranceGraph().get(index).add(
											new Integer(id));
								}
							}
						}
					}
				}
			}

			// build list of utterances based on events
			nl2 = doc.getElementsByTagName("Event");
			if (nl2 != null && nl2.getLength() > 0) {
				for (int i = 0; i < nl2.getLength(); i++) {
					el = (Element) nl2.item(i);
					c.getUtterances().set(
							Integer.parseInt(el.getAttribute("genid")),
							new Utterance(Integer.parseInt(el
									.getAttribute("genid")), -1, -1, el
									.getAttribute("type")
									+ " " + el.getAttribute("nickname")));
				}
			}

			// build utterance list
			for (Participant p : c.getParticipants()) {
				if (noChars != 0)
					p.setPNoChars((double) (p.getNoChars()) / noChars * 100);
				for (Utterance ut : p.getUtteranceList())
					c.getUtterances().set(ut.getUtteranceId(), ut);
			}
		} catch (Exception e) {
			System.err.print("Error evaluating input file: "+path+"!");
			e.printStackTrace();
		}
		return c;
	}

	

}
