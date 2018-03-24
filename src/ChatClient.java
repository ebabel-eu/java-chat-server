import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ChatClient implements Runnable {
  private Socket socket;
  private Thread thread = null;
  private BufferedReader streamIn = null;
  private DataOutputStream streamOut = null;
  private ChatClientThread client = null;

  private ChatClient(String serverName, int serverPort) throws IOException {
    System.out.println("Loading, please wait...");

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
    streamOut = new DataOutputStream(socket.getOutputStream());

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

    if (args.length == 2) {
      host = args[0];
      port = Integer.parseInt(args[1]);
    }

    new ChatClient(host, port);
  }
}
