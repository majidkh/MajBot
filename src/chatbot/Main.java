/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chatbot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Majid
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    private static ArrayList<String> keylist = new ArrayList<String>();
    public static void main(String[] args) {

        DataParser dp = new DataParser();
        Bot bot = new Bot("0", dp);
        bot.run();


    }

    private static String parse(String text){

        List words = Arrays.asList(text.split(" "));
        int bestMatch = -1;
        String answer = "no answer";
        for(int i=0; i <keylist.size(); i ++){
            List keywords = Arrays.asList(keylist.get(i).split(" "));
            int matches = getMatches(words, keywords);
            if(matches > -1 && matches > bestMatch){
                answer = keylist.get(i);
                bestMatch = matches;
            }    
        }

        return answer;
    }

    private static int getMatches(List text, List keywords){

        int result = -1;
        for(int i=0; i< keywords.size(); i++){

            if(text.contains(keywords.get(i))){
                result++;
            }else{
                return -1;
            }
        }

        return result;
    }

}
