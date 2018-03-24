import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.System.arraycopy;

public class ChatServer implements Runnable {
  private ChatServerThread clients[] = new ChatServerThread[50];
  private ServerSocket server;
  private Thread thread = null;
  private int clientCount = 0;

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
        stop();
      }
    }
  }

  public void start() {
    if (thread == null) {
      thread = new Thread(this);
      thread.start();
    }
  }

  public void stop() {
    if (thread != null) {
      thread.stop();
      thread = null;
    }
  }

  private int findClient(int ID) {
    for (int i = 0; i < clientCount; i++) {
      if (clients[i].getId() == ID) {
        return i;
      }
    }

    return -1;
  }

  public synchronized void handle(int ID, String input) {
    if (input.equals("/quit") || input.equals("/q")) {
      clients[findClient(ID)].send("/q");
      remove(ID);
    } else {
      // Send message to each client.
      for (int i = 0; i < clientCount; i++) {
        clients[i].send(ID + ": " + input);
      }

      // Log message on server console.
      System.out.println(ID + ": " + input);
    }
  }

  // Removing thread.
  public synchronized void remove(int ID) {
    int pos = findClient(ID);

    if (pos >= 0) {
      ChatServerThread toTerminate = clients[pos];

      if (pos < clientCount - 1) {
        arraycopy(clients, pos + 1, clients, pos + 1 - 1, clientCount - (pos + 1));
      }

      clientCount--;
      toTerminate.close();
    }
  }

  private void addThread(Socket socket) throws IOException {
    if (clientCount < clients.length) {
      clients[clientCount] = new ChatServerThread(this, socket);

      try {
        clients[clientCount].open();
        clients[clientCount].start();
        clientCount++;
      } catch(IOException ex) {
        System.out.println("[ERROR] Could not open thread. " + ex.getMessage());
      }
    } else {
      System.out.println("[ERROR] Client refused, maximum " + clients.length + " reached.");
    }
  }

  public static void main(String args[]) throws IOException {
    if (args.length != 1) {
      throw new RuntimeException("[ERROR] Usage: java -cp [classpath] ChatServer [port-number]");
    }

    new ChatServer(Integer.parseInt(args[0]));
  }
}
