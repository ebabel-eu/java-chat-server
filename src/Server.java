import java.io.IOException;
import java.net.ServerSocket;

public class Server {


  public static void main(String[] args) {
    int portNumber = 4444;
    ServerSocket serverSocket = null;

    try {
      serverSocket = new ServerSocket(portNumber);
      acceptClients();
    } catch (IOException e) {
      System.err.println("Could not listen on port " + portNumber);
      System.exit(1);
    }
  }

  private static void acceptClients() {
    
  }

}
