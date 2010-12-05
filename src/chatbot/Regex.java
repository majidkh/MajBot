/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chatbot;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * open source Java ChatBot
 *
 * @author Seyed Majid Khosravi
 * 
 */
public class Regex {


    public static String match(String pattern, String keyword){
        Pattern p = Pattern.compile(pattern.toLowerCase());
        Matcher m = p.matcher(keyword.toLowerCase());
        if (m.matches()) {
            return m.group(1);
        }
        return "";
    }

    public static String replace(String text){
        return text;
    }


}
