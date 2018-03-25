import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatClientThread extends Thread {
  private Socket socket;
  private ChatClient client;
  private DataInputStream streamIn = null;
  private boolean shouldStop = false;

  ChatClientThread(ChatClient _client, Socket _socket) {
    client = _client;
    socket = _socket;
    open();
    start();
  }

  private void open() {
    try {
      streamIn = new DataInputStream(socket.getInputStream());
    } catch (IOException ex) {
      System.out.println("[ERROR] " + ex.getMessage());
      client.stop();
    }
  }

  void close() {
    shouldStop = true;

    try {
      if (streamIn != null) {
        streamIn.close();
      }
    } catch (IOException ex) {
      System.out.println("[ERROR] " + ex.getMessage());
    }
  }

  public void run() {
    boolean errorFound = false;
    while (!errorFound && !shouldStop) {
      try {
        client.handle(streamIn.readUTF());
      } catch (IOException ex) {
        System.out.println("[ERROR] " + ex.getMessage());
        errorFound = true;
        client.stop();
      }
    }
  }
}
