// Ryan Tietjen
// CS3700
// 8/15/23

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.io.*;
import java.net.*;
import com.fasterxml.jackson.core.JsonParser;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class client {

  public client(boolean secure)
      throws UnknownHostException, IOException, InterruptedException, NoSuchAlgorithmException {
    String id;
    String network = "proj1.3700.network";
    int port;
    if (secure)
      port = 27994;
    else
      port = 27993;
    // connect to server
    Socket socket;
    if (secure) {
      // TLS encrypted socket connection
      SSLSocketFactory ssf = (SSLSocketFactory) SSLSocketFactory.getDefault();
      socket = ssf.createSocket(network, port);
    }
    else {
      // TCP connection
      socket = new Socket(network, port);
    }

    // dealing with receiving server input
    BufferedReader input;
    try {
      input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }
    catch (IOException e) {
      e.printStackTrace();
      System.out.println("Error regarding server input.");
      socket.close();
      return;
    }

    // dealing with outputting info to server
    PrintStream output;
    try {
      output = new PrintStream(socket.getOutputStream());
    }
    catch (IOException e) {
      e.printStackTrace();
      System.out.println("Error regarding client output.");
      socket.close();
      return;
    }

    // send out initial "hello" message
    String helloStr = "{\"type\": \"hello\", \"northeastern_username\": \"tietjen.r\"}";
    String inputStr = "";
    output.println(helloStr);
    inputStr = input.readLine();

    // establish id
    String[] split = inputStr.split("\"");
    id = split[3];

    // ---------------------------------------------------
    // GUESSING STRATEGY: finds what characters are in each position
    // by using ~130 guesses. Is inefficient but guarantees a correct answer
    // with 51 guesses. More info in the README
    // ---------------------------------------------------
    output.println("{\"type\": \"guess\", \"id\":\"" + id + "\", \"word\": \"arara\"}");
    inputStr = input.readLine();
    output.println("{\"type\": \"guess\", \"id\":\"" + id + "\", \"word\": \"bacao\"}");
    inputStr = input.readLine();
    output.println("{\"type\": \"guess\", \"id\":\"" + id + "\", \"word\": \"babby\"}");
    inputStr = input.readLine();
    output.println("{\"type\": \"guess\", \"id\":\"" + id + "\", \"word\": \"ceded\"}");
    inputStr = input.readLine();
    output.println("{\"type\": \"guess\", \"id\":\"" + id + "\", \"word\": \"dhoon\"}");
    inputStr = input.readLine();
    output.println("{\"type\": \"guess\", \"id\":\"" + id + "\", \"word\": \"egest\"}");
    inputStr = input.readLine();
    output.println("{\"type\": \"guess\", \"id\":\"" + id + "\", \"word\": \"indii\"}");
    inputStr = input.readLine();
    output.println("{\"type\": \"guess\", \"id\":\"" + id + "\", \"word\": \"piing\"}");
    inputStr = input.readLine();
    output.println("{\"type\": \"guess\", \"id\":\"" + id + "\", \"word\": \"hokku\"}");
    inputStr = input.readLine();
    output.println("{\"type\": \"guess\", \"id\":\"" + id + "\", \"word\": \"kimmo\"}");
    inputStr = input.readLine();
    output.println("{\"type\": \"guess\", \"id\":\"" + id + "\", \"word\": \"quaff\"}");
    inputStr = input.readLine();
    output.println("{\"type\": \"guess\", \"id\":\"" + id + "\", \"word\": \"hajji\"}");
    inputStr = input.readLine();
    output.println("{\"type\": \"guess\", \"id\":\"" + id + "\", \"word\": \"lolls\"}");
    inputStr = input.readLine();
    output.println("{\"type\": \"guess\", \"id\":\"" + id + "\", \"word\": \"pippy\"}");
    inputStr = input.readLine();
    output.println("{\"type\": \"guess\", \"id\":\"" + id + "\", \"word\": \"equal\"}");
    inputStr = input.readLine();
    output.println("{\"type\": \"guess\", \"id\":\"" + id + "\", \"word\": \"antiq\"}");
    inputStr = input.readLine();
    output.println("{\"type\": \"guess\", \"id\":\"" + id + "\", \"word\": \"aequi\"}");
    inputStr = input.readLine();
    output.println("{\"type\": \"guess\", \"id\":\"" + id + "\", \"word\": \"iraqi\"}");
    inputStr = input.readLine();
    output.println("{\"type\": \"guess\", \"id\":\"" + id + "\", \"word\": \"uvver\"}");
    inputStr = input.readLine();
    output.println("{\"type\": \"guess\", \"id\":\"" + id + "\", \"word\": \"exxon\"}");
    inputStr = input.readLine();
    output.println("{\"type\": \"guess\", \"id\":\"" + id + "\", \"word\": \"wuzzy\"}");
    inputStr = input.readLine();
    output.println("{\"type\": \"guess\", \"id\":\"" + id + "\", \"word\": \"snowk\"}");
    inputStr = input.readLine();
    output.println("{\"type\": \"guess\", \"id\":\"" + id + "\", \"word\": \"xerox\"}");
    inputStr = input.readLine();
    output.println("{\"type\": \"guess\", \"id\":\"" + id + "\", \"word\": \"adoxa\"}");
    inputStr = input.readLine();
    output.println("{\"type\": \"guess\", \"id\":\"" + id + "\", \"word\": \"yacht\"}");
    inputStr = input.readLine();
    output.println("{\"type\": \"guess\", \"id\":\"" + id + "\", \"word\": \"xylyl\"}");
    inputStr = input.readLine();
    output.println("{\"type\": \"guess\", \"id\":\"" + id + "\", \"word\": \"zigan\"}");
    inputStr = input.readLine();
    output.println("{\"type\": \"guess\", \"id\":\"" + id + "\", \"word\": \"veuve\"}");
    inputStr = input.readLine();
    output.println("{\"type\": \"guess\", \"id\":\"" + id + "\", \"word\": \"essed\"}");
    inputStr = input.readLine();
    output.println("{\"type\": \"guess\", \"id\":\"" + id + "\", \"word\": \"offal\"}");
    inputStr = input.readLine();
    output.println("{\"type\": \"guess\", \"id\":\"" + id + "\", \"word\": \"stott\"}");
    inputStr = input.readLine();
    output.println("{\"type\": \"guess\", \"id\":\"" + id + "\", \"word\": \"mawks\"}");
    inputStr = input.readLine();
    output.println("{\"type\": \"guess\", \"id\":\"" + id + "\", \"word\": \"stacc\"}");
    inputStr = input.readLine();
    output.println("{\"type\": \"guess\", \"id\":\"" + id + "\", \"word\": \"jalap\"}");
    inputStr = input.readLine();
    output.println("{\"type\": \"guess\", \"id\":\"" + id + "\", \"word\": \"nunch\"}");
    inputStr = input.readLine();
    output.println("{\"type\": \"guess\", \"id\":\"" + id + "\", \"word\": \"fohat\"}");
    inputStr = input.readLine();
    output.println("{\"type\": \"guess\", \"id\":\"" + id + "\", \"word\": \"gloom\"}");
    inputStr = input.readLine();
    output.println("{\"type\": \"guess\", \"id\":\"" + id + "\", \"word\": \"objet\"}");
    inputStr = input.readLine();
    output.println("{\"type\": \"guess\", \"id\":\"" + id + "\", \"word\": \"tchai\"}");
    inputStr = input.readLine();
    output.println("{\"type\": \"guess\", \"id\":\"" + id + "\", \"word\": \"raver\"}");
    inputStr = input.readLine();
    output.println("{\"type\": \"guess\", \"id\":\"" + id + "\", \"word\": \"climb\"}");
    inputStr = input.readLine();
    output.println("{\"type\": \"guess\", \"id\":\"" + id + "\", \"word\": \"gazoz\"}");
    inputStr = input.readLine();
    output.println("{\"type\": \"guess\", \"id\":\"" + id + "\", \"word\": \"groow\"}");
    inputStr = input.readLine();
    output.println("{\"type\": \"guess\", \"id\":\"" + id + "\", \"word\": \"ollav\"}");
    inputStr = input.readLine();
    output.println("{\"type\": \"guess\", \"id\":\"" + id + "\", \"word\": \"kilij\"}");
    inputStr = input.readLine();
    output.println("{\"type\": \"guess\", \"id\":\"" + id + "\", \"word\": \"njave\"}");
    inputStr = input.readLine();
    output.println("{\"type\": \"guess\", \"id\":\"" + id + "\", \"word\": \"skulk\"}");
    inputStr = input.readLine();
    output.println("{\"type\": \"guess\", \"id\":\"" + id + "\", \"word\": \"dwyka\"}");
    inputStr = input.readLine();
    output.println("{\"type\": \"guess\", \"id\":\"" + id + "\", \"word\": \"azide\"}");
    inputStr = input.readLine();
    output.println("{\"type\": \"guess\", \"id\":\"" + id + "\", \"word\": \"amaga\"}");
    inputStr = input.readLine();
    output.println("{\"type\": \"guess\", \"id\":\"" + id + "\", \"word\": \"apple\"}");
    inputStr = input.readLine();

    // obtains the result from the inputs
    String[] answer = new String[5];
    String[] splitAnswer = inputStr.split("\"");
    for (int i = 8; i < splitAnswer.length - 4; i += 6) {
      if (splitAnswer[i].substring(2, 3).equals("2")) {
        answer[0] = splitAnswer[i - 3].substring(0, 1);
      }
      if (splitAnswer[i].substring(4, 5).equals("2")) {
        answer[1] = splitAnswer[i - 3].substring(1, 2);
      }
      if (splitAnswer[i].substring(6, 7).equals("2")) {
        answer[2] = splitAnswer[i - 3].substring(2, 3);
      }
      if (splitAnswer[i].substring(8, 9).equals("2")) {
        answer[3] = splitAnswer[i - 3].substring(3, 4);
      }
      if (splitAnswer[i].substring(10, 11).equals("2")) {
        answer[4] = splitAnswer[i - 3].substring(4, 5);
      }
    }

    // submits the final answer
    String answerStr = answer[0] + answer[1] + answer[2] + answer[3] + answer[4];
    output.println("{\"type\": \"guess\", \"id\":\"" + id + "\", \"word\": \"" + answerStr + "\"}");
    inputStr = input.readLine();

    // isolates the flag
    String[] splitFlag = inputStr.split("\"");
    String flag = splitFlag[3];
    System.out.println(flag);

    socket.close();
  }

  public static void main(String[] args)
      throws IOException, InterruptedException, NoSuchAlgorithmException {
    // sets up the word list
    ArrayList<String> wordlist = new ArrayList<String>();
    wordlist = (ArrayList<String>) Files.readAllLines(Paths.get("wordlist.txt"));

    // determines if connection should be secure
    boolean secure = false;
    for (int i = 0; i < args.length; i++) {
      if (args[i].equals("-s") || (args[i] + "").equals("27994")) {
        secure = true;
      }
    }
    client client = new client(secure);

  }

}
