/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chatbot;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Majid
 */
public class State
{
    private String id = "";
    private ArrayList messages ;
    private ArrayList keywords;
    
    public State(String id, ArrayList messages, ArrayList keywords)
    {
        this.id = id;
        this.messages = messages;
        this.keywords = keywords;        
    }

    public String getId()
    {
        return id;
    }

    public String getMessage()
    {
        Random generator = new Random();
        return messages.get(generator.nextInt(messages.size())).toString();
    }

    public ArrayList getKeywords()
    {
        return keywords;
    }

    
}
