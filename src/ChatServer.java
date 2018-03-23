import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer implements Runnable {
  private ServerSocket server = null;
  private Thread thread = null;

  private ChatServer(int port) throws IOException {
    System.out.println("Please wait, binding to port " + port);

    server = new ServerSocket(port);
    System.out.println("Server started " + server + " - waiting for a client...");

    start();
  }

  public void run() {
    while (thread != null) {
      System.out.println("Waiting for a client...");

      try {
        socket = server.accept();
        System.out.println("Client accepted " + socket);
        open();
        boolean done = false;

        while (!done) {
          String line = streamIn.readUTF();
          System.out.println(line);
          done = line.equals("/quit") || line.equals("/q");
        }
        close();
      } catch (IOException error) {
        throw new RuntimeException(error.getMessage());
      }
    }
  }

  public void start() {
    if (thread == null) {
      thread = new Thread(this);
      thread.start();
    }
  }

  private void open() throws IOException {
    streamIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
  }

  private void close() throws IOException {
    if (socket != null) {
      socket.close();
    }
    if (streamIn != null) {
      streamIn.close();
    }
  }

  public static void main(String args[]) throws IOException {
    if (args.length != 1) throw new RuntimeException("Usage: java -cp [classpath] ChatServer [port-number]");

    new ChatServer(Integer.parseInt(args[0]));
  }
}
