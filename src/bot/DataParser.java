package bot;

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
 * MajBot 1.0
 * 
 * @author Seyed Majid Khosravi
 */
public class DataParser {

    private Document dom;
    private HashMap<String, State> states = new HashMap<String, State>();
    private ArrayList<String> invalidMessages = new ArrayList();
    private int invalidMessageIndex = 0;
    public  int stateCounter = 1000;

    // default constructor
    public DataParser() {

        // Load the XML file and parse it
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {

            //Using factory get an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder();

            //parse using builder to get DOM representation of the XML file
            dom = db.parse("data.xml");

            // Load configuration and states from the XML file
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

        // get document element object
        Element docEle = dom.getDocumentElement();

        // get all State node names
        NodeList nl = docEle.getElementsByTagName("State");

        // if node is not null and has children
        if (nl != null && nl.getLength() > 0) {

            // loop through all children
            for (int i = 0; i < nl.getLength(); i++) {

                // get state element
                Element el = (Element) nl.item(i);

                // get state id
                String id = el.getAttribute("id");

                // get all state messages
                ArrayList messages = new ArrayList();
                NodeList messagesNodeList = el.getElementsByTagName("message");

                // if messages node is not null and has children
                if (messagesNodeList != null && messagesNodeList.getLength() > 0) {

                    // loop through all children
                    for (int j = 0; j < messagesNodeList.getLength(); j++) {

                        // get current message element
                        Element elmsg = (Element) messagesNodeList.item(j);

                        // append message node value to the messages list
                        messages.add(elmsg.getFirstChild().getNodeValue());
                    }
                }

                // get keywords in the current state
                ArrayList keywords = getKeywords(el);

                // construct a new State object
                State state = new State(id, messages, keywords);

                stateCounter ++;

                // add the state to the states hashmap
                states.put(id, state);
            }
        }
    }

    // get state object by id
    public State getState(String id) {
        return states.get(id);
    }

    // create a new state
    public void addState(State state){
        states.put(state.getId(), state);
        stateCounter++;
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

                // find the keyword target, classname and argument attributes
                String wordTag = el.getFirstChild().getNodeValue();
                String target = el.getAttribute("target");
                String className = el.getAttribute("className");
                String arg = el.getAttribute("arg");
                String variable = el.getAttribute("variable");
                int points = 0;
                try{
                     points = Integer.valueOf(el.getAttribute("points"));
                }catch (Exception e){
                    
                }

                String learn = el.getAttribute("learn");
                // split keyword by comma
                String[] words = wordTag.split(",");

                // loop through all words
                for (String word : words) {

                    // trim the word to remove spaces
                    word = word.trim();
                    
                    // construct a new keyword
                    Keyword keyword = new Keyword(word, target, className, arg, variable, points, learn );

                    // add the keyword to keywords array list
                    keywords.add(keyword);
                }
            }
        }

        // return all the keywords in the given node
        return keywords;
    }


    // returns one of the invalid messages and move the index to the next message
    public String getInvalidAnswer() {

        // get current answer
        String answer = invalidMessages.get(invalidMessageIndex);

        // increase the index, if it is end of messages, reset the index to 0
        invalidMessageIndex++;
        if (invalidMessageIndex >= invalidMessages.size()) {
            invalidMessageIndex = 0;
        }
        return answer;
    }

    // load cofig tags from data xml file
    private void loadConfiguration() {

        // get document element
        Element docEle = dom.getDocumentElement();

        // get all node names for invalid messages
        NodeList node = docEle.getElementsByTagName("InvalidMessages");

        // get all message nodes inside invalid messages node
        NodeList nl = ((Element) node.item(0)).getElementsByTagName("message");

        // if node is not null and has children
        if (nl != null && nl.getLength() > 0) {

            // loop through all children
            for (int i = 0; i < nl.getLength(); i++) {

                // get message node
                Element el = (Element) nl.item(i);

                // get message and add it to invalid messages array
                String message = el.getFirstChild().getNodeValue();
                invalidMessages.add(message);
            }
        }
    }
}
