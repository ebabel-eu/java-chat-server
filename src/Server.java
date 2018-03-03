import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
  private static ServerSocket serverSocket = null;
  private static int portNumber = 4444;

  public static void main(String[] args) {
    try {
      serverSocket = new ServerSocket(portNumber);
      acceptClients();
    } catch (IOException e) {
      System.err.println("Could not listen on port " + portNumber);
      System.exit(1);
    }
  }

  private static void acceptClients() {
    while (true) {
      try {
        System.out.println("hello world 4");

        Socket socket = serverSocket.accept();
        System.out.println("Listening on port" + portNumber);
      } catch (IOException e) {
        System.out.println("Accept failed on " + portNumber);
      }
    }
  }

}
