import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatClient {
  private Socket socket = null;
  private DataInputStream streamIn = null;
  private DataOutputStream streamOut = null;

  private ChatClient(String serverName, int serverPort) throws IOException {
    System.out.println("Establishing connection. Please wait...");

    socket = new Socket(serverName, serverPort);
    System.out.println("Connected " + socket);
    start();

    String line = "";

    while (!line.equals("/quit") && !line.equals("/q")) {
      line = streamIn.readLine();
      streamOut.writeUTF(line);
      streamOut.flush();
    }
  }

  private void start() throws IOException {
    streamIn = new DataInputStream(System.in);
    streamOut = new DataOutputStream(socket.getOutputStream());
  }

  public static void main(String args[]) throws RuntimeException, IOException {
    if (args.length != 2) {
      throw new RuntimeException("Usage: java -cp /Users/nadjib/workspace/java-chat-server/out/production/java-chat-server/ ChatClient [host] [port]");
    }

    String host = args[0];
    int port = Integer.parseInt(args[1]);
    ChatClient client = new ChatClient(host, port);
  }
}
