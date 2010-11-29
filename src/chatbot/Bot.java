/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chatbot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Majid
 */
public class Bot {

    String level = "0";
    DataParser parser;

    public Bot(String level, DataParser parser){

        this.level = level;
        this.parser = parser;
    }

    public void run(){
        State state = parser.getState(level);
        try
        {
            String answer = StringIO.ask(state.getMessage());

            Keyword match = parse(answer, state.getKeywords());

            if (match == null)
            {
                System.out.println("Invalid answer");
                run();
            }
            else
            {
                this.level = match.target;
                run();
            }

        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }


    private static Keyword parse(String text, ArrayList<Keyword> keylist){

        List givenWords = Arrays.asList(text.split(" "));
        int bestMatch = -1;
        Keyword match = null;
        for(int i=0; i <keylist.size(); i ++){
            List keywords = Arrays.asList(keylist.get(i).keyword.split(" "));
            int matches = getMatches(givenWords, keywords);

            if(matches > -1 && matches > bestMatch){
                match = keylist.get(i);
                bestMatch = matches;
            }
        }

        return match;
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
