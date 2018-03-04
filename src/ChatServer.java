import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
  private Socket socket = null;
  private ServerSocket server = null;
  private DataInputStream streamIn = null;

  private ChatServer(int port) throws IOException {
    System.out.println("Please wait, binding to port " + port);

    server = new ServerSocket(port);
    System.out.println("Server started " + server + " - waiting for a client...");

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
    if (args.length != 1) {
      throw new RuntimeException("Usage: java -cp [classpath] ChatServer [port-number]");
    }

    int port = Integer.parseInt(args[0]);
    ChatServer server = new ChatServer(port);
  }
}
