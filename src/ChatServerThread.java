import java.io.*;
import java.net.Socket;

public class ChatServerThread extends Thread {
  private ChatServer server;
  private Socket socket;
  private int ID;
  private DataInputStream streamIn = null;
  private DataOutputStream streamOut = null;

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
    server.messageEachClient("User " + ID + " has joined this chat.");
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
    streamIn = new DataInputStream(
        new BufferedInputStream(socket.getInputStream())
    );

    streamOut = new DataOutputStream(
        new BufferedOutputStream(socket.getOutputStream())
    );
  }

  public void close() {
    if (socket != null) {
      try {
        System.out.println("[INFO] Server thread " + ID + " is closing.");
        socket.close();
      } catch (IOException ex) {
        System.out.println("[ERROR] Closing socket. " + ex.getMessage());
      }
    }

    if (streamIn != null) {
      try {
        streamIn.close();
      } catch (IOException ex) {
        System.out.println("[ERROR] Closing streamIn. " + ex.getMessage());
      }
    }

    if (streamOut != null) {
      try {
        streamOut.close();
      } catch (IOException ex) {
        System.out.println("[ERROR] Closing streamOut. " + ex.getMessage());
      }
    }
  }
}
