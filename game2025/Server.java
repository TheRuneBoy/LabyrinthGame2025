import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main() {
        try {
            ServerSocket welcomingSocket = new ServerSocket(6000);
            while (true) {
                Socket connectionSocket = welcomingSocket.accept();
                new ServerThread(connectionSocket).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
