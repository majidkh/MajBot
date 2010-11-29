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

    private int level = 0;
    private Document dom;
    HashMap<String, State> states = new HashMap<String, State>();

    public DataParser(int level) {

        this.level = level;
        //get the factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {

            //Using factory get an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            //parse using builder to get DOM representation of the XML file
            dom = db.parse("src/chatbot/data.xml");
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (SAXException se) {
            se.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        loadStates();
    }

    public void setLevel(int level) {
        this.level = level;
    }

    // Load states
    private void loadStates() {

        Element docEle = dom.getDocumentElement();

        NodeList nl = docEle.getElementsByTagName("State");
        if (nl != null && nl.getLength() > 0) {

            for (int i = 0; i < nl.getLength(); i++) {

                //get the employee element
                Element el = (Element) nl.item(i);

                String id = el.getAttribute("id");

                //get the Employee object
                State state = getState(el, id);

                states.put(id, state);


            }
        }


    }

    public void getMessage(String id) {
        State state = states.get(id);
    }

    private State getState(Element empEl, String id) {

        String message = getTextValue(empEl, "message");
        ArrayList keywords = getKeywords(empEl);
        State state = new State(id, message, keywords);
        return state;
    }

    public ArrayList getKeywords(Element ele) {
        ArrayList keywords = new ArrayList();
        NodeList nl = ele.getElementsByTagName("keyword");
        if (nl != null && nl.getLength() > 0) {

            for (int i = 0; i < nl.getLength(); i++) {

                //get the keyword element
                Element el = (Element) nl.item(i);

                Keyword keyword = new Keyword(el.getFirstChild().getNodeValue(), el.getAttribute("target"));
                if (keyword != null) {
                    keywords.add(keyword);
                }

            }
        }
        return keywords;
    }

    private String getTextValue(Element ele, String tagName) {
        String textVal = null;
        NodeList nl = ele.getElementsByTagName(tagName);
        if (nl != null && nl.getLength() > 0) {
            Element el = (Element) nl.item(0);
            textVal = el.getFirstChild().getNodeValue();
        }

        return textVal;
    }
}
