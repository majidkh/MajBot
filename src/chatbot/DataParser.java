/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chatbot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Majid
 */
public class DataParser {

    private Document dom;
    private HashMap<String, State> states = new HashMap<String, State>();
    private ArrayList<String> invalidMessages = new ArrayList();
    private int invalidMessageIndex = 0;

    // default constructor
    public DataParser() {

        // Load the XML file and parse it
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {

            //Using factory get an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder();

            //parse using builder to get DOM representation of the XML file
            dom = db.parse("src/chatbot/data.xml");

            // Load states from XML file
            loadConfiguration();
            loadStates();
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (SAXException se) {
            se.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    // Load states from XML file
    private void loadStates() {

        // get elements
        Element docEle = dom.getDocumentElement();

        // get all State node names
        NodeList nl = docEle.getElementsByTagName("State");

        // if node is not null and has children
        if (nl != null && nl.getLength() > 0) {

            // loop through all children
            for (int i = 0; i < nl.getLength(); i++) {

                // get state element
                Element el = (Element) nl.item(i);

                // get state id, message and keywords
                String id = el.getAttribute("id");
                ArrayList messages = new ArrayList();

                NodeList messagesNl = el.getElementsByTagName("message");

                // if node is not null and has children
                if (messagesNl != null && messagesNl.getLength() > 0) {

                    // loop through all children
                    for (int j = 0; j < messagesNl.getLength(); j++) {

                        // get state element
                        Element elmsg = (Element) messagesNl.item(j);

                        // get state id, message and keywords
                        messages.add(elmsg.getFirstChild().getNodeValue());
                    }
                }
                ArrayList keywords = getKeywords(el);

                // construct a new State object
                State state = new State(id, messages, keywords);

                // add the state to the states hashmap
                states.put(id, state);
            }
        }
    }

    // get state object by id
    public State getState(String id) {
        return states.get(id);
    }

    // get all keywords in an State tag
    public ArrayList getKeywords(Element ele) {
        // construct keywords arraylist
        ArrayList keywords = new ArrayList();

        // get all nodes by keyword tag name
        NodeList nl = ele.getElementsByTagName("keyword");

        // if the tag is not null and has children
        if (nl != null && nl.getLength() > 0) {

            // loop through all the children
            for (int i = 0; i < nl.getLength(); i++) {

                //get the keyword element
                Element el = (Element) nl.item(i);

                // find the keyword title and target state
                String wordTag = el.getFirstChild().getNodeValue();
                String[] words = wordTag.split(",");
                String target = el.getAttribute("target");
                String action = el.getAttribute("action");
                String arg = el.getAttribute("arg");
                for (String word : words) {
                    word = word.trim();
                    // construct a new keyword
                    Keyword keyword = new Keyword(word, target, action, arg);

                    // add the keyword to keywords array list
                    keywords.add(keyword);
                }
            }
        }

        // return all the keywords in the given tag
        return keywords;
    }

    // get a value of a tag inside given element by tag name
    private String getTextValue(Element ele, String tagName) {
        String textVal = null;

        // find the tag
        NodeList nl = ele.getElementsByTagName(tagName);

        // if the tag is not null and has children
        if (nl != null && nl.getLength() > 0) {
            // get first element and its node value
            Element el = (Element) nl.item(0);
            textVal = el.getFirstChild().getNodeValue();
        }

        // return the string in the tag
        return textVal;
    }

    public String getInvalidAnswer() {
        String answer = invalidMessages.get(invalidMessageIndex);
        invalidMessageIndex++;
        if (invalidMessageIndex >= invalidMessages.size()) {
            invalidMessageIndex = 0;
        }
        return answer;
    }

    private void loadConfiguration() {
        // get elements
        Element docEle = dom.getDocumentElement();

        // get all State node names
        NodeList node = docEle.getElementsByTagName("InvalidMessages");

        NodeList nl = ((Element) node.item(0)).getElementsByTagName("message");

        // if node is not null and has children
        if (nl != null && nl.getLength() > 0) {

            // loop through all children
            for (int i = 0; i < nl.getLength(); i++) {

                // get state element
                Element el = (Element) nl.item(i);

                // get state id, message and keywords
                String message = el.getFirstChild().getNodeValue();
                invalidMessages.add(message);
            }
        }
    }
}
