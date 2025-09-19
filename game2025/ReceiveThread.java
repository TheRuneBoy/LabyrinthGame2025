import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Arrays;

public class ReceiveThread extends Thread{
    private final BufferedReader inFromServer;
    private final GUI gui;

    public ReceiveThread(Socket clientSocket, GUI gui) {
        try {
            this.gui = gui;
            inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        while(true) {
            try {
                String[] messageFromServer = inFromServer.readLine().split(" ");
                int xpos = Integer.parseInt(messageFromServer[0]), ypos = Integer.parseInt(messageFromServer[1]);
                String direction = messageFromServer[2];

                Platform.runLater(() -> gui.playerMoved(xpos, ypos, direction));
                System.out.println("Message from server: " + Arrays.toString(messageFromServer));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
