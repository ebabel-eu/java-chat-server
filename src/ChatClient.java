import java.io.*;
import java.net.Socket;

public class ChatClient implements Runnable {
  private Socket socket;
  private Thread thread = null;
  private BufferedReader streamIn = null;
  private ObjectOutputStream streamOut = null;
  private String alias;

  private ChatClient(String serverName, int serverPort, String _alias) throws IOException {
    System.out.println("Loading, please wait...");

    alias = _alias;
    socket = new Socket(serverName, serverPort);
    start();
  }

  public void run() {
    while (thread != null) {
      try {
        streamOut.writeUTF(streamIn.readLine());
        streamOut.flush();
      } catch (IOException ex) {
        stop();
      }
    }
  }

  public void handle(String message) {
    if (message.equals("/quit") || message.equals("/q")) {
      stop();
    } else {
      System.out.println(message);
    }
  }

  private void start() throws IOException {
    streamIn = new BufferedReader(new InputStreamReader(System.in));
    streamOut = new ObjectOutputStream(socket.getOutputStream());
    streamOut.writeObject(alias);

    if (thread == null) {
      new ChatClientThread(this, socket);
      thread = new Thread(this);
      thread.start();
    }
  }

  public void stop() {
    thread = null;

    try {
      streamIn.close();
      streamOut.close();
      socket.close();
    } catch (IOException ex) {
      System.out.println("[ERROR] " + ex.getMessage());
    }
  }

  public static void main(String args[]) throws RuntimeException, IOException {
    String alias = "John Smith";
    String host = "localhost";
    int port = 4444;

    if (args.length >= 1) {
      alias = args[0];
    }

    if (args.length >= 2) {
      host = args[1];
    }

    if (args.length == 3) {
      port = Integer.parseInt(args[2]);
    }

    new ChatClient(host, port, alias);
  }
}
