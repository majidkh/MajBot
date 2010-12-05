package chatbot;

/**
 *
 * @author Seyed Majid Khosravi
 */
public class Keyword {

    public String keyword = "";
    public String target = "";
    public String className = "";
    public String arg = "";
    public String variable = "";

    // default constructor, constructs a keyword object
    public Keyword(String keyword, String target, String className, String arg, String variable) {
        this.keyword = keyword;
        this.target = target;
        this.className = className;
        this.arg = arg;
        this.variable = variable;
    }
}
