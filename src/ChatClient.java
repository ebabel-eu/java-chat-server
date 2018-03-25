import java.io.*;
import java.net.Socket;

public class ChatClient implements Runnable {
  private Socket socket;
  private Thread thread = null;
  private BufferedReader streamIn = null;
  private ObjectOutputStream streamOut = null;
  private ChatClientThread client = null;
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
      client = new ChatClientThread(this, socket);
      thread = new Thread(this);
      thread.start();
    }
  }

  public void stop() {
    if (thread != null) {
      thread.stop();
      thread = null;
    }

    try {
      if (streamIn != null) {
        streamIn.close();
      }

      if (streamOut != null) {
        streamOut.close();
      }

      if (socket != null) {
        socket.close();
      }
    } catch (IOException ex) {
      System.out.println("[ERROR] " + ex.getMessage());
    }

    client.close();
    client.stop();
  }

  public static void main(String args[]) throws RuntimeException, IOException {
    String host = "localhost";
    int port = 4444;
    String alias = "John Smith";

    if (args.length == 3) {
      host = args[0];
      port = Integer.parseInt(args[1]);
      alias = args[2];
    }

    new ChatClient(host, port, alias);
  }
}
