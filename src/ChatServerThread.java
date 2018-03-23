import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatServerThread extends Thread {
  private Socket socket;
  private int ID;
  private DataInputStream streamIn = null;

  public ChatServerThread(Socket _socket) {
    socket = _socket;
    ID = socket.getPort();
  }

  public void run() {
    System.out.println("[INFO] Server thread " + ID + " running.");
    boolean errorFound = false;
    while (!errorFound) {
      try {
        System.out.println(streamIn.readUTF());
      } catch (IOException e) {
        errorFound = true;
        close();
      }
    }
  }

  public void open() throws IOException {
    streamIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
  }

  public void close() {
    if (socket != null) {
      try {
        socket.close();
      } catch (IOException e) {
        System.out.println("[ERROR]" + e.getMessage());
      }
    }

    if (streamIn != null) {
      try {
        streamIn.close();
      } catch (IOException e) {
        System.out.println("[ERROR]" + e.getMessage());
      }
    }
  }
}
