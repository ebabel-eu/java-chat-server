import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer implements Runnable {
  private ServerSocket server;
  private Thread thread = null;
  private ChatServerThread client = null;

  private ChatServer(int port) throws IOException {
    // Binding to port.
    server = new ServerSocket(port);

    // Starts server, then waiting for a client.
    start();
  }

  public void run() {
    while (thread != null) {
      try {
        addThread(server.accept());
      } catch (IOException ex) {
        System.out.println("[ERROR]" + ex.getMessage());
      }
    }
  }

  public void addThread(Socket socket) throws IOException {
    // Client accepted.
    client = new ChatServerThread(socket);
    client.open();
    client.start();
  }

  public void start() {
    if (thread == null) {
      thread = new Thread(this);
      thread.start();
    }
  }

  public static void main(String args[]) throws IOException {
    if (args.length != 1) {
      throw new RuntimeException("[ERROR] Usage: java -cp [classpath] ChatServer [port-number]");
    }

    new ChatServer(Integer.parseInt(args[0]));
  }
}
