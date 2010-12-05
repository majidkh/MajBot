package chatbot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Seyed Majid Khosravi
 */
public class Bot {

    // Default state to start the bot
    String level = "0";
    DataParser parser;

    // default constructor
    public Bot(String level, DataParser parser) {

        this.level = level;
        this.parser = parser;
    }

    // get current state message
    public String getMessage() {
        State state = parser.getState(level);
        return state.getMessage();
    }

    // send user message to the bot and get the response
    public String send(String message) {

        String response = "";
        State state = parser.getState(level);


        // end of the tree
        if (state.getKeywords().isEmpty()) {
            this.level = "1";
        }

        // match the keyword with given message
        Keyword match = parse(message, state.getKeywords());

        // if no keyword is matched, display one of the invalid answers
        if (match == null) {
            response = parser.getInvalidAnswer();
        } else {

            // if match classname is provided, check to get the dynamic response
            if (match.className.length() > 0) {

                // check for Weather dynamic response
                if (match.className.equals("Weather")) {
                    Weather weather = new Weather();
                    response = weather.getResponse(match.arg);
                    this.level = "1";
                }
            } else {

                // get the new state and return the new message
                if (response.length() == 0) {

                    this.level = match.target;
                    state = parser.getState(level);
                    state.setRegex(match.regexMatch);

                    // if it is end of the tree
                    if (state.getKeywords().isEmpty()) {
                        response = this.getMessage();
                        this.level = "1";

                    }
                }
            }
        }
        return response;
    }

    // parse the given text to find best match in the keywords
    private static Keyword parse(String text, ArrayList<Keyword> keylist) {

        // set the default match to none
        int bestMatch = -1;
        Keyword match = null;

        // loop through keywords
        for (int i = 0; i < keylist.size(); i++) {

            // extract all words in each keyword text
            //List<String> keywords = Arrays.asList(keylist.get(i).keyword.split(" "));

            // get number of matches of the keyword with given text
            int matches = getMatches(text, keylist.get(i));

            // if match is better than best match, replace it
            if (matches > -1 && matches > bestMatch) {
                match = keylist.get(i);
                bestMatch = matches;
            }
        }
        return match;
    }

    // get number of matches of the given keywords in the given list
    private static int getMatches(String text, Keyword keyword) {

        // no match by default
        int result = -1;

        // return 0 match when keyword is *
        if(keyword.keyword.equals("*")){
            return 0;
        }

        // if regex is expected
        if(keyword.regex.length() > 0){
            String match = Regex.match(keyword.keyword, text);
            if(match.length() > 0){
                keyword.regexMatch = match;
                return 0;
            }
        }

        String[] words = keyword.keyword.split(" ");


        // loop through list of the keywords
        for (String word : words) {

            // if current keyword is in the text, add one score
            if (text.toLowerCase().indexOf(word.toLowerCase()) >= 0) {
                result++;
            } else {
                // return null if one of the keywords does not exists
                return -1;
            }
        }
        return result;
    }
}
