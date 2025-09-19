import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class RecieverThread extends Thread {
    private final Socket socket;
    private final GUI gui;
    private volatile boolean running = true;

    public RecieverThread(Socket socket, GUI gui) {
        this.socket = socket;
        this.gui = gui;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String line;
            while (running && (line = in.readLine()) != null) {
                System.out.println("From server: " + line);
                if (gui != null) {
                    gui.handleIncomingMessage(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Receiver thread fejl: " + e.getMessage());
        } finally {
            // Sørg for at lukke socket hvis thread stopper
            try {
                socket.close();
            } catch (IOException ignored) {
            }
        }
    }

    public void shutdown() {
        running = false;
        try {
            socket.close();
        } catch (IOException ignored) {
        }
    }
}
