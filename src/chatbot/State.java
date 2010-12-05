package chatbot;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Seyed Majid Khosravi
 */
public class State {

    private String id = "";
    private ArrayList<String> messages;
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
        return messages.get(generator.nextInt(messages.size()));
    }

    // replace message dynamic field with given argument
    public String getMessage(String arg){
        Random generator = new Random();
        String message = messages.get(generator.nextInt(messages.size()));
        return message.replaceAll("\\[1\\]", arg.toString());
    }

    // get state keywords
    public ArrayList getKeywords() {
        return keywords;
    }
}
