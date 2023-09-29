package cs3700.ftp.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

public class ftp {

  public static void main(String[] args) throws UnknownHostException, IOException {
    if (args.length < 2) {
      throw new IllegalArgumentException("Illegal argument(s)");
    }
    String operation = args[0];
    String param1 = args[1];
    String param2 = "";
    if (args.length >= 3) {
      param2 = args[2];
    }
    ftp ftp = new ftp(operation, param1, param2);
//    ftp ftp = new ftp("ls", "ftp://tietjenr:D0aJdZThtW7n5yB4FqR8@ftp.3700.network:21/", "");
  } 

  public ftp(String operation, String param1, String param2)
      throws UnknownHostException, IOException {
    if (param1.length() == 0) {
      param1 = "aaaaaaa";
    }
    if (param2.length() == 0) {
      param2 = "aaaaaaa";
    }
    String str = "";
    if (param1.substring(0,3).equals("ftp") && param2.substring(0,3).equals("ftp")) {
      throw new IllegalArgumentException("Illegal argument(s)");
    }
    if (param1.substring(0,3).equals("ftp")) {
      str = param1;
    }
    else if (param2.substring(0,3).equals("ftp")) {
      str = param2;
    }
    else {
      throw new IllegalArgumentException("Illegal argument(s)");
    }
    String[] split = str.split(":");
    String[] passAndNetwork = split[2].split("@");
   
    String userName = split[1].substring(2);
    String password = passAndNetwork[0];
    String network = passAndNetwork[1];
    int port = 21;
    try {
    port = Integer.parseInt(split[3].substring(0, split[3].length()-1));
    }
    catch (Exception ignored) {
      
    }
    String controlInputStr = "";
    String dataInputStr = "";
    
    
    // connect to network
    Socket control = new Socket(network, port);
    


    // dealing with receiving control server input
    BufferedReader controlInput;
    BufferedReader dataInput;
    try {
      controlInput = new BufferedReader(new InputStreamReader(control.getInputStream()));
    }
    catch (IOException e) {
      e.printStackTrace();
      System.out.println("Error regarding server input.");
      control.close();
      return;
    }


    // dealing with outputting control info to server
    PrintStream controlOutput;
    PrintStream dataOutput;
    try {
      controlOutput = new PrintStream(control.getOutputStream());
    }
    catch (IOException e) {
      e.printStackTrace();
      System.out.println("Error regarding client output.");
      control.close();
      return;
    }


    // initialize control socket
    System.out.println("CONTROL SOCKET:");
    controlInputStr = controlInput.readLine();
    System.out.println(controlInputStr);

    controlOutput.println("USER " + userName + "\r\n");
    controlInputStr = controlInput.readLine();
    System.out.println(controlInputStr);

    controlOutput.println("PASS " + password + "\r\n");
    controlInputStr = controlInput.readLine();
    System.out.println(controlInputStr);

    controlOutput.println("TYPE I\r\n");
    controlInputStr = controlInput.readLine();
    System.out.println(controlInputStr);

    controlOutput.println("MODE S\r\n");
    controlInputStr = controlInput.readLine();
    System.out.println(controlInputStr);

    controlOutput.println("STRU F\r\n");
    controlInputStr = controlInput.readLine();
    System.out.println(controlInputStr);
    
    controlOutput.println("PASV");
    controlInputStr = controlInput.readLine();
    System.out.println(controlInputStr);
    
    String[] splitAddress = controlInputStr.split(",");
    int dataPort = (Integer.parseInt(splitAddress[4]) * 256) +
        Integer.parseInt(splitAddress[5].substring(0, splitAddress[5].length()-2));
    
    Socket data = new Socket("54.235.102.179", dataPort);
   
    
    // data socket input reader
    try {
      dataInput = new BufferedReader(new InputStreamReader(data.getInputStream()));
    }
    catch (IOException e) {
      e.printStackTrace();
      System.out.println("Error regarding server input.");
      data.close();
      control.close();
      return;
    }
    
    // data socket output sender
    try {
      dataOutput = new PrintStream(data.getOutputStream());
    }
    catch (IOException e) {
      e.printStackTrace();
      System.out.println("Error regarding client output.");
      control.close();
      data.close();
      return;
    }
   
    
    System.out.println("____________________");


    if (operation.equalsIgnoreCase("ls")) {
      controlOutput.println("LIST " + this.getDirectory(param1) + "\r\n");
      dataInputStr = dataInput.readLine();
      System.out.println(dataInputStr);
    }
    else if (operation.equalsIgnoreCase("mkdir")) {
      controlOutput.println("MKD " + this.getDirectory(param1) + "\r\n");
      controlInputStr = controlInput.readLine();
      System.out.println(controlInputStr);

    }
    else if (operation.equalsIgnoreCase("rm")) {
      controlOutput.println("DELE " + this.getDirectory(param1) + "\r\n");
      controlInputStr = controlInput.readLine();
      System.out.println(controlInputStr);

    }
    else if (operation.equalsIgnoreCase("rmdir")) {
      controlOutput.println("RMD " + this.getDirectory(param1) + "\r\n");
      controlInputStr = controlInput.readLine();
      System.out.println(controlInputStr);

    }
    else if (operation.equalsIgnoreCase("cp")) {
      String file = "";
      String url = "";
      if (param1.substring(0,3).equals("ftp")) {
        url = param1;
        file = param2;
        controlOutput.println("RETR " + this.getDirectory(param2) + "\r\n");
        controlInputStr = controlInput.readLine();
        System.out.println(controlInputStr);
      }
      else {
        file = param1;
        url = param2;
        controlOutput.println("STOR " + this.getDirectory(param1) + "\r\n");
        controlInputStr = controlInput.readLine();
        System.out.println(controlInputStr);
      }
    }
    else if (operation.equalsIgnoreCase("mv")) {
      String file = "";
      String url = "";
      if (param1.substring(0,3).equals("ftp")) {
        url = param1;
        file = param2;
        controlOutput.println("RETR " + this.getDirectory(param2) + "\r\n");
        controlInputStr = controlInput.readLine();
        System.out.println(controlInputStr);
      }
      else {
        file = param1;
        url = param2;
        controlOutput.println("STOR " + this.getDirectory(param1) + "\r\n");
        controlInputStr = controlInput.readLine();
        System.out.println(controlInputStr);
      }
    }
    else {
      control.close();
      data.close();
      throw new IllegalArgumentException("Illegal argument(s)");
    }

    control.close();
    data.close();

  }
  
  public String getDirectory(String param) {
    String[] split = param.split("/");
    String str = "/";
    for (int i = 3; i < split.length; i++) {
      str += split[i] + "/";
    }
    return str;
  }

}
