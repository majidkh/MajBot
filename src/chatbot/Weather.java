/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chatbot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 *
 * @author Majid
 */
public class Weather {

    public String today = "";
    public String tomorrow = "";
    public String dayAfterTomorrow = "";
    private static String url = "http://weather.yahooapis.com/forecastrss?u=c&w=44418";

    public Weather() {

        HttpClient client = new HttpClient();

        // Create a method instance.
        GetMethod method = new GetMethod(url);

        try {
            // Execute the method.
            int statusCode = client.executeMethod(method);

            if (statusCode != HttpStatus.SC_OK) {
                System.err.println("Method failed: " + method.getStatusLine());
            }

            // Read the response body.
            //byte[] responseBody = method.getResponseBody();
            BufferedReader br = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream()));

            String xmlResponse = "";
            String readLine;
            while (((readLine = br.readLine()) != null)) {
                xmlResponse += readLine;
            }
            // Deal with the response.
            // Use caution: ensure correct character encoding and is not binary data
            parse(xmlResponse);

        } catch (HttpException e) {
            System.err.println("Fatal protocol violation: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Fatal transport error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Release the connection.
            method.releaseConnection();
        }

    }

    private void parse(String xmlString) {

        DocumentBuilderFactory dbf =
                DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xmlString));

            Document doc = db.parse(is);
            NodeList nodes = doc.getElementsByTagName("yweather:condition");
            NodeList forcast = doc.getElementsByTagName("yweather:forecast");

            today = ((Element) nodes.item(0)).getAttribute("text");
            tomorrow = ((Element) forcast.item(0)).getAttribute("text");
            dayAfterTomorrow = ((Element) forcast.item(1)).getAttribute("text");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getWeather(String day){
        String weather = "";
        if (day.equals("today")){
            weather = "I think today is " + today;
        }

        if (day.equals("tomorrow")){
            weather = "I guess tomorrow will be " + tomorrow;
        }
        if (day.equals("dayaftertomorrow")){
            weather = "Day after tomorrow should be " + dayAfterTomorrow;
        }
        return weather;
    }
}
