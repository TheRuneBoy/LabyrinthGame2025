import java.io.*;
import java.net.Socket;

public class ClientHandler {
    private Socket socket;
    private BufferedReader inFromServer;
    private DataOutputStream outToServer;

    public ClientHandler(String host, int port) throws IOException {
        socket = new Socket(host, port);
        inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outToServer = new DataOutputStream(socket.getOutputStream());
    }

    public void sendMessage(String message) throws IOException {
        outToServer.writeBytes(message + "\n");
    }

    public String readMessage() throws IOException {
        return inFromServer.readLine();
    }

    public void close() throws IOException {
        socket.close();
    }
    public Socket getSocket() {
        return socket;
    }
}
