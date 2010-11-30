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

    public Bot(String level, DataParser parser) {

        this.level = level;
        this.parser = parser;
    }

    public String getMessage() {
        State state = parser.getState(level);
        return state.getMessage();
    }

    public String send(String message) {

        String response = "";
        State state = parser.getState(level);


        // end of the tree
        if (state.getKeywords().isEmpty()) {
            this.level = "1";
        }

        // match the keyword with given message
        Keyword match = parse(message, state.getKeywords());

        if (match == null) {
            response = parser.getInvalidAnswer();
        } else {
            if (match.action.equals("getWeather")) {
                Weather weather = new Weather();
                response = weather.getWeather(match.arg);
                this.level = "1";
            } else {
                this.level = match.target;
                state = parser.getState(level);
                if (state.getKeywords().isEmpty()) {
                    response = this.getMessage();
                    this.level = "1";
                    
                }
            }
        }

        return response;
    }

    public void run() {
        State state = parser.getState(level);
        try {
            System.out.println(state.getMessage());
            if (state.getKeywords().isEmpty()) {
                this.level = "1";
                run();
                return;
            }



            String answer = StringIO.readString();

            Keyword match = parse(answer, state.getKeywords());

            if (match == null) {
                System.out.println(parser.getInvalidAnswer());
                run();
                return;
            } else {
                if (match.action.equals("getWeather")) {
                    Weather weather = new Weather();
                    System.out.println(weather.getWeather(match.arg));
                    this.level = "1";
                    run();
                    return;
                } else {
                    this.level = match.target;
                    run();
                    return;
                }
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static Keyword parse(String text, ArrayList<Keyword> keylist) {

        //List givenWords = Arrays.asList(text.split(" "));
        int bestMatch = -1;
        Keyword match = null;
        for (int i = 0; i < keylist.size(); i++) {
            List<String> keywords = Arrays.asList(keylist.get(i).keyword.split(" "));
            int matches = getMatches(text, keywords);

            if (matches > -1 && matches > bestMatch) {
                match = keylist.get(i);
                bestMatch = matches;
            }
        }

        return match;
    }

    private static int getMatches(String text, List<String> keywords) {

        int result = -1;
        for (int i = 0; i < keywords.size(); i++) {

            if (text.toLowerCase().indexOf(keywords.get(i).toLowerCase()) >= 0) {
                result++;
            } else {
                return -1;
            }
        }

        return result;
    }
}
