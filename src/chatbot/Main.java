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

}
