import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
  private Socket socket = null;
  private ServerSocket server = null;
  private DataInputStream streamIn = null;

  public ChatServer(int port) throws IOException {
    System.out.println("Please wait, binding to port " + port);

    server = new ServerSocket(port);
    System.out.println("Server started " + server + " - waiting for a client...");

    socket = server.accept();
    System.out.println("Client accepted " + socket);
  }

  public static void main(String args[]) throws IOException {
    if (args.length != 1) {
      throw new RuntimeException("Usage: java -cp [classpath] ChatServer [port-number]");
    }

    int port = Integer.parseInt(args[0]);
    ChatServer server = new ChatServer(port);
  }
}
