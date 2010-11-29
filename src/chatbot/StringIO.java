/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chatbot;

/**
 *
 * @author Ray Gumme
 * @modified by Majid
 */
import java.io.IOException;
public class StringIO
{
  public static String readString() throws IOException
  {
    StringBuffer buffer = new StringBuffer(80);
    int key = System.in.read();
    while ( (char) key != '\n')
    {
      buffer.append((char) key);
      key = System.in.read();
    }
    key = System.in.read(); // skip LF
    return new String(buffer);
  }

  public static String ask(String prompt) throws IOException
  {
    System.out.print(prompt);
    return readString();
  }

}

