/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chatbot;

import java.util.ArrayList;

/**
 *
 * @author Majid
 */
public class State
{
    private String id = "";
    private String message = "";
    private ArrayList keywords;
    
    public State(String id, String message, ArrayList keywords)
    {
        this.id = id;
        this.message = message;
        this.keywords = keywords;        
    }

    public String getId()
    {
        return id;
    }

    public String getMessage()
    {
        return message;
    }

    public ArrayList getKeywords()
    {
        return keywords;
    }

    
}
