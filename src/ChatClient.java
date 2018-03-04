import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient {
  private Socket socket = null;
  private DataInputStream console = null;
  private DataOutputStream streamOut = null;

  public ChatClient(String serverName, int serverPort) throws IOException {
    System.out.println("Establishing connection. Please wait...");

    try {
      socket = new Socket(serverName, serverPort);
      System.out.println("Connected " + socket);
      start();
    } catch (UnknownHostException e) {
      System.out.println("Host unknown "+ e.getMessage());
    } catch(IOException e) {
      System.out.println("Unexpected exception " + e.getMessage());
    }

    String line = "";

    while (!line.equals("/quit") && !line.equals("/q")) {
      try {
        line = console.readLine();
        streamOut.writeUTF(line);
        streamOut.flush();
      } catch (IOException e) {
        System.out.println("Sending error " + e.getMessage());
      }
    }
  }

  public void start() throws IOException {
    console = new DataInputStream(System.in);
    streamOut = new DataOutputStream(socket.getOutputStream());
  }

  public static void main(String args[]) throws IOException {
    ChatClient client = null;

    if (args.length != 2) {
      System.out.println("Usage: java -cp /Users/nadjib/workspace/java-chat-server/out/production/java-chat-server/ ChatClient [host] [port]");
    } else {
      client = new ChatClient(args[0], Integer.parseInt(args[1]));
    }
  }
}
