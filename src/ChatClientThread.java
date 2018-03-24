import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatClientThread extends Thread {
  private Socket socket;
  private ChatClient client;
  private DataInputStream streamIn = null;

  public ChatClientThread(ChatClient _client, Socket _socket) {
    client = _client;
    socket = _socket;
    open();
    start();
  }

  public void open() {
    try {
      streamIn = new DataInputStream(socket.getInputStream());
    } catch (IOException ex) {
      System.out.println("[ERROR] " + ex.getMessage());
      client.stop();
    }
  }

  public void close() {
    try {
      if (streamIn != null) {
        streamIn.close();
      }
    } catch (IOException ex) {
      System.out.println("[ERROR] " + ex.getMessage());
    }
  }

  public void run() {
    while (true) {
      try {
        client.handle(streamIn.readUTF());
      } catch (IOException ex) {
        System.out.println("[ERROR] " + ex.getMessage());
        client.stop();
      }
    }
  }
}
