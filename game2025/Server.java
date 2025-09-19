import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) throws IOException {

        ServerSocket welcomeSocket = new ServerSocket(12800);
        System.out.println("Venter på klient...");

        Socket connectionSocket = welcomeSocket.accept();
        System.out.println("Forbundet til klient");

        BufferedReader fromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
        DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

        while (true) {
            String clientSentence = fromClient.readLine();
            System.out.println("Modtaget fra klient: " + clientSentence);

            // Her ekko'er vi beskeden direkte tilbage
            outToClient.writeBytes(clientSentence + "\n");
        }
    }
}
