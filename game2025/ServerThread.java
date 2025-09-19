import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerThread extends Thread {
    private static final List<DataOutputStream> DATA_OUTPUT_STREAMS = new ArrayList<>();
    private final BufferedReader inFromClient;
    private final DataOutputStream outToClient;

    public ServerThread(Socket connectionSocket) {
        System.out.println("Connection established to: " + connectionSocket.getInetAddress());
        try {
            this.inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            this.outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            DATA_OUTPUT_STREAMS.add(outToClient);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        receiveAndSend();
    }

    private void broadcast(String messageToClient) throws IOException {
        for (DataOutputStream outToClient : DATA_OUTPUT_STREAMS) {
            outToClient.writeBytes(messageToClient + "\n");
        }
    }

    private void receiveAndSend() {
        while(true) {
            try {
                String messageToClient = inFromClient.readLine();
                System.out.println("Message from player: " + messageToClient);
                broadcast(messageToClient);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
