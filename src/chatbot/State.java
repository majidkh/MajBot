package chatbot;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Seyed Majid Khosravi
 */
public class State {

    private String id = "";
    private ArrayList messages;
    private ArrayList keywords;

    // default constructor, constructs State object
    public State(String id, ArrayList messages, ArrayList keywords) {
        this.id = id;
        this.messages = messages;
        this.keywords = keywords;
    }

    // get state id
    public String getId() {
        return id;
    }

    // get random state messages
    public String getMessage() {
        Random generator = new Random();
        return messages.get(generator.nextInt(messages.size())).toString();
    }

    // get state keywords
    public ArrayList getKeywords() {
        return keywords;
    }
}
