package cs3700.ftp.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ftp {
  BufferedReader controlInput;
  BufferedReader dataInput;
  PrintStream controlOutput;
  PrintStream dataOutput;
  String controlInputStr = "";
  String dataInputStr = "";
  Socket data;

  public static void main(String[] args) throws UnknownHostException, IOException {
    // argument parsing
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
  }

  /**
   * Represents a FTP client that can send, receive, and modify files/directories.
   * 
   * @param operation is the provided operation (ls, mkdir, rmdir, rm, cp, mv)
   * @param param1 is the first parameter (url/file)
   * @param param2 is the second parameter (url/file)
   * @throws UnknownHostException
   * @throws IOException
   */
  public ftp(String operation, String param1, String param2)
      throws UnknownHostException, IOException {
    
    //deals with incoming data
    if (param1.length() == 0) {
      param1 = "aaaaaaa";
    }
    if (param2.length() == 0) {
      param2 = "aaaaaaa";
    }
    String str = "";
    if (param1.substring(0, 3).equals("ftp") && param2.substring(0, 3).equals("ftp")) {
      throw new IllegalArgumentException("Illegal argument(s)");
    }
    if (param1.substring(0, 3).equals("ftp")) {
      str = param1;
    }
    else if (param2.substring(0, 3).equals("ftp")) {
      str = param2;
    }
    else {
      throw new IllegalArgumentException("Illegal argument(s)");
    }
    
    //establishes username, password, network, and socket (if applicable)
    String[] split = str.split(":");
    String[] passAndNetwork = split[2].split("@");

    String userName = split[1].substring(2);
    String password = passAndNetwork[0];
    String network = passAndNetwork[1];
    if (network.contains("/")) {
      network = network.substring(0, network.indexOf("/"));
    }
    int port = 21;
    try {
      port = Integer.parseInt(split[3].substring(0, split[3].length() - 1));
    }
    catch (Exception ignored) {

    }

    // connect to network
    Socket control = new Socket(network, port);

    // dealing with receiving control server input
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

    //obtains data IP/port
    String[] splitAddress = controlInputStr.split(",");
    String dataAddress = splitAddress[0].substring(splitAddress[0].length() - 2) + "."
        + splitAddress[1] + "." + splitAddress[2] + "." + splitAddress[3];
    int dataPort = (Integer.parseInt(splitAddress[4]) * 256)
        + Integer.parseInt(splitAddress[5].substring(0, splitAddress[5].length() - 2));

    data = new Socket(dataAddress, dataPort);

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

    // case "ls" operation is sent.
    if (operation.equalsIgnoreCase("ls")) {
      this.list(this.getDirectory(param1));
    }
    // case "mkdir" operation is sent.
    else if (operation.equalsIgnoreCase("mkdir")) {
      this.makeDirectory(this.getDirectory(param1));

    }
    // case "rm" operation is sent.
    else if (operation.equalsIgnoreCase("rm")) {
      String temp = this.getDirectory(param1);
      temp = temp.substring(0, temp.length() - 1);
      this.removeFile(temp);
    }
    // case "rmdir" operation is sent.
    else if (operation.equalsIgnoreCase("rmdir")) {
      this.removeDirectory(this.getDirectory(param1));
    }
    //case "cp" operation is sent.
    else if (operation.equalsIgnoreCase("cp")) {
      String file = "";
      String url = "";
      //case file is being downloaded
      if (param1.substring(0, 3).equals("ftp")) {
        url = param1;
        file = param2;
        String tempStr = this.getDirectory(url).substring(0, this.getDirectory(url).length() - 1);
        controlOutput.println("RETR " + tempStr + "\r\n");
        this.receiveFile(file);
        controlInputStr = controlInput.readLine();
        System.out.println(controlInputStr);
      }
      //case file is being uploaded
      else {
        file = param1;
        url = param2;
        String tempStr = this.getDirectory(url).substring(0, this.getDirectory(url).length() - 1);
        controlOutput.println("STOR " + tempStr + "\r\n");
        this.sendFile(file);
        controlInputStr = controlInput.readLine();
        System.out.println(controlInputStr);
      }
    }
    //case bad operation is sent, throws exception
    else {
      control.close();
      data.close();
      throw new IllegalArgumentException("Illegal argument(s)");
    }

    //sockets must be closed
    control.close();
    data.close();
  }

  /**
   * Parses a string to extract the directory from it.
   * 
   * @param param is the string to be parsed.
   * @return the directory
   */
  private String getDirectory(String param) {
    String[] split = param.split("/");
    String str = "/";
    for (int i = 3; i < split.length; i++) {
      str += split[i] + "/";
    }
    return str;
  }

  /**
   * Makes a directory.
   * 
   * @param directory is the directory
   * @throws IOException
   */
  private void makeDirectory(String directory) throws IOException {
    controlOutput.println("MKD " + directory + "\r\n");
    controlInputStr = controlInput.readLine();
    System.out.println(controlInputStr);
  }

  /**
   * Lists all the files and directories in a given directory.
   * 
   * @param directory is the directory
   * @throws IOException
   */
  private void list(String directory) throws IOException {
    controlOutput.println("LIST " + directory + "\r\n");
    ;
    while (dataInputStr != null) {
      dataInputStr = dataInput.readLine();
      if (dataInputStr != null) {
        System.out.println(dataInputStr);
      }
    }
  }

  /**
   * Removes a directory.
   * 
   * @param directory is the directory
   * @throws IOException
   */
  private void removeDirectory(String directory) throws IOException {
    controlOutput.println("RMD " + directory + "\r\n");
    controlInputStr = controlInput.readLine();
    System.out.println(controlInputStr);
  }

  /**
   * Removes a file.
   * 
   * @param file is the path of the file.
   * @throws IOException
   */
  private void removeFile(String file) throws IOException {
    controlOutput.println("DELE " + file + "\r\n");
    controlInputStr = controlInput.readLine();
    System.out.println(controlInputStr);
  }

  /**
   * Sends a file.
   * 
   * @param path is the path of the file.
   * @throws IOException
   */
  private void sendFile(String path) throws IOException {
    // Create a File object to represent the file to be sent
    File file = new File(path);

    // Create a FileInputStream to read the file
    FileInputStream fileInputStream = new FileInputStream(file);

    // Buffer sends/reads data in "chunks"
    byte[] buffer = new byte[1024 * 4];
    int bytesRead;

    // send data until there is none left
    while ((bytesRead = fileInputStream.read(buffer)) != -1) {
      dataOutput.write(buffer, 0, bytesRead);
    }

    // streams must be closed after they are done being used
    // note that sockets will be closed later
    fileInputStream.close();
    
    // Flushes remaining data
    dataOutput.flush();

    // displays a success/error message
    controlInputStr = controlInput.readLine();
    System.out.println(controlInputStr);
  }

  /**
   * Receives a file.
   * 
   * @param path is the path of the file.
   * @throws IOException
   */
  private void receiveFile(String path) throws IOException {
    FileOutputStream fileOutputStream = new FileOutputStream(path);

    // Buffer sends/reads data in "chunks"
    byte[] buffer = new byte[4 * 1024];
    int bytesRead;

    InputStream dataInputStream = data.getInputStream();

    // Read data until there is none left
    while ((bytesRead = dataInputStream.read(buffer)) != -1) {
      fileOutputStream.write(buffer, 0, bytesRead);
    }

    // streams must be closed after they are done being used
    // note that sockets will be closed later
    fileOutputStream.close();
    dataInputStream.close();

    // displays a success/error message
    controlInputStr = controlInput.readLine();
    System.out.println(controlInputStr);
  }

}
