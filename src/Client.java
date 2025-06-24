import java.io.*;
import java.net.*;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 8080;
    private static BufferedReader in;
    private static BufferedWriter out;
    private static Socket socket;

    public static void main(String[] args) {
        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            System.out.println("Connected to server at " + SERVER_ADDRESS + ":" + SERVER_PORT);
            setIn(new BufferedReader(new InputStreamReader(socket.getInputStream())));
            setOut(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
            ReadMsg readMsg = new ReadMsg();
            readMsg.start();
            WriteMsg writeMsg = new WriteMsg();
            writeMsg.start();
            writeMsg.join();

        } catch (IOException e) {
            System.err.println("Error connecting to server: " + e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            shutdown();
        }
    }

    public static void shutdown() {
        try {
            if (in != null) {
                in.close();
            }
        } catch (IOException e) {
            System.err.println("Error closing input stream: " + e.getMessage());
        }
        try {
            if (out != null) {
                out.close();
            }
        } catch (IOException e) {
            System.err.println("Error closing output stream: " + e.getMessage());
        }
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
                System.out.println("Connection closed");
            }
        } catch (IOException e) {
            System.err.println("Error closing socket: " + e.getMessage());
        }
    }

    public static BufferedReader getIn() {
        return in;
    }

    public static void setIn(BufferedReader in) {
        Client.in = in;
    }

    public static BufferedWriter getOut() {
        return out;
    }

    public static void setOut(BufferedWriter out) {
        Client.out = out;
    }
}