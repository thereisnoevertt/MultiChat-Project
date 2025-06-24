import java.io.*;
import java.net.Socket;
import java.util.Objects;

public class ServerSomething extends Thread {
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;

    public ServerSomething(Socket socket) throws IOException {
        this.socket = socket;
        setIn(new BufferedReader(new InputStreamReader(socket.getInputStream())));
        setOut(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
        start();
    }

    private void send(String msg) {
        try {
            getOut().write(msg + "\n");
            getOut().flush();
        } catch (IOException ignored) {}
    }

    @Override
    public void run() {
        String word;
        try {
            while ((word = getIn().readLine()) != null) {
                if (word.trim().equals("exit")) {
                    break;
                }
                synchronized (Server.getServerList()) {
                    for (ServerSomething vr : Server.getServerList()) {
                        if (vr != this) {
                            vr.send(word);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Client disconnected: " + e.getMessage());
        } finally {
            synchronized (Server.getServerList()) {
                Server.getServerList().remove(this);
            }
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Error closing socket: " + e.getMessage());
            }
        }
    }

    public BufferedReader getIn() {
        return in;
    }

    public void setIn(BufferedReader in) {
        this.in = in;
    }

    public BufferedWriter getOut() {
        return out;
    }

    public void setOut(BufferedWriter out) {
        this.out = out;
    }
}