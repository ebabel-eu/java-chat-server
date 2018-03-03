import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
  private Socket socket = null;
  private ServerSocket server = null;
  private DataInputStream streamIn = null;

  public ChatServer(int port) {
    System.out.println("Please wait, binding to port " + port);

    try {
      server = new ServerSocket(port);
      System.out.println("Server started " + server);
      System.out.println("Waiting for a client...");

      socket = server.accept();
      System.out.println("Client accepted " + socket);
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }


  public static void main(String args[]) {
    new ChatServer(4444);
  }
}
