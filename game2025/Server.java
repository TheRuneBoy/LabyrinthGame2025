import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private BufferedReader inFromClient;
    private DataOutputStream outToClient;
    public static void main(String[] args) {
        Server server = new Server();
        server.establishConnection();
        server.receiveAndSend();
    }

    private void receiveAndSend() {
        int count = 0;
        while(true) {
            try {
                String message = inFromClient.readLine();
                System.out.println("Message from player: " + message);
                outToClient.writeBytes(message + "\n");
                count++;
                if(count % 2 == 0) {
                    System.out.println();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void establishConnection() {
        try {
            ServerSocket welcomingSocket = new ServerSocket(65000);
            Socket socket = welcomingSocket.accept();

            System.out.println("Connection established at: " + socket.getInetAddress());
            inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outToClient = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
