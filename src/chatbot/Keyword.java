/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chatbot;

/**
 *
 * @author Majid
 */
public class Keyword
{

    public String keyword = "";
    public String target = "";
    public String action = "";
    public String arg = "";

    public Keyword(String keyword, String target, String action, String arg)
    {
        this.keyword = keyword;
        this.target = target;
        this.action = action;
        this.arg = arg;
    }
}
