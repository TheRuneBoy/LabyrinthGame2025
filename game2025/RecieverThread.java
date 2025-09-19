import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class RecieverThread extends Thread{
    Socket connectionSocket;

    public RecieverThread(Socket connectionSocket) {
        this.connectionSocket = connectionSocket;
    }

    @Override
    public void run() {
        try (BufferedReader inFromOther = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));) {
            String fromServer;
            while ((fromServer = inFromOther.readLine()) != null) {
                System.out.println("From other client: " + fromServer);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
