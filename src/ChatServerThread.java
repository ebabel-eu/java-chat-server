import java.io.*;
import java.net.Socket;

public class ChatServerThread extends Thread {
  private ChatServer server;
  private Socket socket;
  private int ID;
  private ObjectInputStream streamIn = null;
  private DataOutputStream streamOut = null;
  String alias;

  public ChatServerThread(ChatServer _server, Socket _socket) {
    super();
    server = _server;
    socket = _socket;
    ID = socket.getPort();
  }

  public void send(String msg) {
    try {
      streamOut.writeUTF(msg);
      streamOut.flush();
    } catch(IOException ex) {
      server.remove(ID);
      stop();
    }
  }

  public int getID() {
    return ID;
  }

  public void run() {
    server.messageEachClient(alias + " has joined this chat.");
    boolean errorFound = false;
    while (!errorFound) {
      try {
        server.handle(ID, streamIn.readUTF());
      } catch (IOException e) {
        server.remove(ID);
        errorFound = true;
        stop();
      }
    }
  }

  public void open() throws IOException {
    streamIn = new ObjectInputStream(socket.getInputStream());

    try {
      alias = (String) streamIn.readObject();
    } catch (ClassNotFoundException ex) {
      System.out.println("[ERROR] " + ex.getMessage());
    }

    streamOut = new DataOutputStream(
        new BufferedOutputStream(socket.getOutputStream())
    );
  }

  public void close() {
    try {
      if (socket != null) {
        socket.close();
      }

      if (streamIn != null) {
        streamIn.close();
      }

      if (streamOut != null) {
        streamOut.close();
      }
    } catch (IOException ex) {
      System.out.println("[ERROR] " + ex.getMessage());
    }
  }
}
