// ServerBroadcast.java

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class ServerBroadcast {
    private static final int PORT = 12800;
    // trådsikkert array af output streams
    private static final CopyOnWriteArrayList<DataOutputStream> clients = new CopyOnWriteArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server venter på klienter på port " + PORT);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Ny klient forbundet: " + clientSocket.getRemoteSocketAddress());
            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
            clients.add(out);

            // Start en reader-tråd for denne klient
            Thread t = new Thread(() -> handleClient(clientSocket, out));
            t.start();
        }
    }

    private static void handleClient(Socket socket, DataOutputStream out) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println("Modtaget: " + line);
                broadcast(line, out);
            }
        } catch (IOException e) {
            System.out.println("Klient disconnected: " + e.getMessage());
        } finally {
            // fjern klientens out når den lukker
            clients.remove(out);
            try {
                socket.close();
            } catch (IOException ignored) {
            }
        }
    }

    private static void broadcast(String message, DataOutputStream senderOut) {
        for (DataOutputStream out : clients) {
            try {
                out.writeBytes(message + "\n");
            } catch (IOException e) {
                // fejl -> fjern klient
                clients.remove(out);
            }
        }
    }
}
