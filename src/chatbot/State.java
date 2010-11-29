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
public class State {
    public String id = "";
    public String message = "";
    public ArrayList keywords;
    public State(String id, String message, ArrayList keywords){
        this.id = id;
        this.message = message;
        this.keywords = keywords;
        System.out.println(message);
        
    }
}
