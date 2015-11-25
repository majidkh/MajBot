<h2>Introduction</h2>
MajBot is a Java-Based chat bot which responses based on the messages that it receives. It uses an external data file (XML file) to make it easier for users to extend its features and its domain. MajBot uses dynamic features to retrieve weather information from Yahoo Weather API. It allows multiple messages per state to make it possible to display random message for similar questions to make the bot more interactive.
Each state can expect multiple keywords or no keywords which returns user to the default state. It also supports regular expressions for better user input processing. MajBot can learn new keywords by asking questions from user and provide their answer for them in the future questions.

<img class="alignnone size-full wp-image-254" title="chat-bot" alt="Chat Bot" src="http://www.majidkhosravi.com/wp-content/uploads/2012/08/chat-bot.png" width="591" height="341" />

License: GPL

<h2>At a glance</h2>
It is easy to customize the MajBot by configuring data.xml file, this file contains different states and keywords inside each state, once a keyword is matched, user will be transferred to the target state, MajBot stats from State “0” by default, here is a simple configuration file:
<pre>&lt;?xml<strong> </strong>version="1.0"<strong> </strong>encoding="UTF-8"?&gt;
&lt;<strong>Config</strong>&gt;
<strong>    </strong>&lt;<strong>State </strong>id="0"&gt;
<strong>        </strong>&lt;<strong>message</strong>&gt;Hello, How are you?&lt;/<strong>message</strong>&gt;
<strong>        </strong>&lt;<strong>keywords</strong>&gt;<strong>        </strong><strong>            </strong>&lt;<strong>keyword target</strong>="1"&gt;happy&lt;/<strong>keyword</strong>&gt;
<strong>        </strong>&lt;/<strong>keywords</strong>&gt;
<strong>    </strong>&lt;/<strong>State</strong>&gt;

<strong>    </strong>&lt;<strong>State </strong>id="1"&gt;
<strong>      </strong>&lt;<strong>message</strong>&gt;wow, you are happy!&lt;/<strong>message</strong>&gt;
      &lt;<strong>message</strong>&gt;<strong>glad</strong> to hear that!&lt;/<strong>message</strong>&gt;
<strong>   </strong>&lt;/<strong>State</strong>&gt;
   &lt;<strong>InvalidMessages</strong>&gt;
        &lt;<strong>message</strong>&gt;Huh, I didn't understand?&lt;/<strong>message</strong>&gt;
        &lt;<strong>message</strong>&gt;What do you mean?&lt;/<strong>message</strong>&gt;
    &lt;/<strong>InvalidMessages</strong>&gt;
&lt;/<strong>Config</strong>&gt;</pre>
In the above example, If user enters happy keyword, bot moves to State "1" and display one of  state "1" messages randomly, If user enters any other strings, one of the invalid messages will appear.

Keyword tag contains multiple attributes to make it possible to customize the bot according to the requirements.
<h2>Navigation</h2>
To navigate user between different states, keyword tag is being used with target attribute, the matching keyword will transfer user to its target, it is possible to use multiple keywords and multiple messages for each state, in this case everytime a random message appears. It is easy to use multiple keywords by separating the keywords by comma, if any of these keywords matches, user will be transferred to the target state:
<pre>&lt;<strong>State</strong> id="16"&gt;
&lt;<strong>message</strong>&gt;Hello, how are you today?&lt;/<strong>message</strong>&gt;
&lt;<strong>message</strong>&gt;How are you feeling?&lt;/<strong>message</strong>&gt;
&lt;<strong>message</strong>&gt;How are you doing?&lt;/<strong>message</strong>&gt;
&lt;<strong>keyword</strong> <strong>target</strong>="11"&gt;happy, very happy&lt;/<strong>keyword</strong>&gt;
&lt;<strong>keyword</strong> <strong>target</strong>="13"&gt;not happy,not good,not fine,sad,upset&lt;/<strong>keyword</strong>&gt;
&lt;<strong>keyword</strong> <strong>target</strong>="23"&gt;fine, good, thanks&lt;/<strong>keyword</strong>&gt;
&lt;<strong>keyword</strong> <strong>target</strong>="14"&gt;yourself&lt;/<strong>keyword</strong>&gt;
&lt;/<strong>State</strong>&gt;</pre>
&nbsp;

<strong>Using the weather feature</strong>

To use weather feature, keyword tag should contain two attributes, className and arg, className should set to Weather and arg should set to "today", "tomorrow" or "dayaftertomorrow" keywords. Here is an example to get today's weather:
<pre>&lt;<strong>keyword</strong> <strong>className</strong>="Weather" <strong>arg</strong>="today"&gt;weather&lt;/<strong>keyword</strong>&gt;</pre>
Result:
<pre>You: how is the weather
Bot: I think today is Fog</pre>
<h2>Catch all</h2>
To transfer user to a custom state when user enters an unexpected keyword, "*" symbol can be used:
<pre>&lt;<strong>State</strong> id="11"&gt;
&lt;<strong>message</strong>&gt;That's great, why you are happy?&lt;/<strong>message</strong>&gt;
&lt;<strong>keywords</strong>&gt;
&lt;<strong>keyword</strong> <strong>target</strong>="21"&gt;lottery&lt;/<strong>keyword</strong>&gt;
&lt;<strong>keyword</strong> <strong>target</strong>="22"&gt;*&lt;/<strong>keyword</strong>&gt;
&lt;/<strong>keywords</strong>&gt;
&lt;/<strong>State</strong>&gt;</pre>
<h2>Regular Expressions</h2>
Using Regular Expression makes it possible to process user's input and extract data from their inputs; MajBot stores these data into a dictionary and it is possible to use these dynamic data in different states, following example demonstrates how to ask a user's name and response with a greeting message with user's name in the greeting:
<pre>&lt;?xml version="1.0" encoding="UTF-8"?&gt;
&lt;<strong>Config</strong>&gt;
&lt;<strong>State</strong> id="0"&gt;
&lt;<strong>message</strong>&gt;Hello, My name is MajBot, what is your name?&lt;/<strong>message</strong>&gt;
&lt;<strong>keywords</strong>&gt;
&lt;<strong>keyword</strong> <strong>target</strong>="2" <strong>variable</strong>="name"&gt;([a-zA-Z ]+)&lt;/<strong>keyword</strong>&gt;
&lt;/<strong>keywords</strong>&gt;
&lt;/<strong>State</strong>&gt;
&lt;<strong>State</strong> id="16"&gt;
&lt;<strong>message</strong>&gt;Hello [name], nice to see you!&lt;/<strong>message</strong>&gt;
&lt;/<strong>State</strong>&gt;
&lt;/<strong>Config</strong>&gt;</pre>
Result:
<pre>Bot: Hello, My name is MajBot, what is your name?
You: Majid 
Bot: Hello majid, nice to see you!</pre>
In the above example "([a-zA-Z )+)" regex accepts any characters betwen a to z  (case insensitive) including spaces and store the matching word to name variable, then by using [name] in the message, user's name will be replaced with this tag. (For more information about Regex syntax refer to Java Regex Documentation).
<h2>Adding priorities to keywords</h2>
By default the longest matching keyword will be the best match, however sometimes we need to give extra points to some of keywords so in case both of the keywords matches, the keywords with higher points selected for the best match, in this case we can use points attribute:
<pre>&lt;<strong>Config</strong>&gt;
&lt;<strong>State</strong> id="0"&gt;
&lt;<strong>message</strong>&gt;Hello, My name is MajBot, what is your name?&lt;/<strong>message</strong>&gt;
&lt;<strong>keywords</strong>&gt;
&lt;<strong>keyword</strong> <strong>target</strong>="16" <strong>variable</strong>="name" <strong>points</strong>="2"&gt;.*my name is ([a-zA-z]+).*&lt;/<strong>keyword</strong>&gt;
&lt;<strong>keyword</strong> <strong>target</strong>="16" <strong>variable</strong>="name"&gt;([a-zA-Z ]+)&lt;/<strong>keyword</strong>&gt;
&lt;/<strong>keywords</strong>&gt;
&lt;/<strong>State</strong>&gt;
&lt;<strong>State</strong> id="16"&gt;
&lt;<strong>message</strong>&gt;Hello [name], nice to see you!&lt;/<strong>message</strong>&gt;
&lt;/<strong>State</strong>&gt;
&lt;/<strong>Config</strong>&gt;</pre>
Result:
<pre>Bot: Hello, My name is MajBot, what is your name?
You: my name is Majid
Bot: Hello majid, nice to see you!</pre>
In the above example we are adding 2 points for the first keyword so if user enters "my name is Majid", it only gets the name instead of returning "My name is Majid" as name.
<h2>Learning new words</h2>
It is possible to ask the user about unknown keywords and add dynamic keywords and states for the new keyword, if in the future user enters same keyword, his answer will be replied back to the user. For this purpose we can use variable tag to get user's keyword first and then asking a question about that subject and storing the subject result by using learn attribute:
<pre> &lt;<strong>State</strong> id="1"&gt;
&lt;<strong>message</strong>&gt;What do you want to talk about?&lt;/<strong>message</strong>&gt;
&lt;<strong>keyword</strong> <strong>variable</strong>="subject" <strong>target</strong>="27"&gt;([a-zA-z]+)&lt;/<strong>keyword</strong>&gt;
&lt;/<strong>State</strong>&gt;
&lt;<strong>State</strong> id="27"&gt;
&lt;<strong>message</strong>&gt;What is [subject]?&lt;/<strong>message</strong>&gt;
&lt;<strong>keyword</strong> <strong>target</strong>="43" <strong>variable</strong>="result" <strong>learn</strong>="subject"&gt;(.*)&lt;/<strong>keyword</strong>&gt;
&lt;/<strong>State</strong>&gt;</pre>
Result:
<pre>Bot: What do you want to talk about?
You: football 
Bot: What is football?
You: it is a good game 
Bot: I see...
Bot: What do you want to talk about?
You: football 
Bot: it is a good game</pre>
As it is shown in the above example, bot learns about football keyword and then send the response back to the user when user sends same keyword again.
<h2>How to run MajBot</h2>
MajBot includes 7 classes and one data.xml file. It also uses Apache HttpdClient libraries to connect to Yahoo Weather API. All of the classes are compiled into MajBot.jar file and you can run the jar file using provided run.sh file which contains:
<pre>java -cp MajBot.jar bot.Main</pre>
To run MajBot you can simply type:
<pre>sh run.sh</pre>
If you are using windows, you can run run.bat file instead.

After starting the bot, user can enter a message and start chating with MajBot.

Enjoy!

<a title="MajBot" href="http://www.majidkhosravi.com/chat-bot/">http://www.majidkhosravi.com/chat-bot/</a>
