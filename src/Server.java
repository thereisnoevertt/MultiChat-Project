import java.io.*;
import java.net.*;
import java.util.LinkedList;

public class Server {
    private static final int PORT = 8080;
    private static LinkedList<ServerSomething> serverList = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(PORT);

        try (server) {
            System.out.println("Сервер запущен! Порт: + " + server.getLocalPort() + ", " + server.getInetAddress());
            while (true) {
                Socket socket = server.accept();
                try {
                    getServerList().add(new ServerSomething(socket));
                } catch (IOException e) {
                    socket.close();
                }
            }
        } finally {
            System.out.println("Сервер закрыт!");
        }
    }

    public static LinkedList<ServerSomething> getServerList() {
        return serverList;
    }

    public static void setServerList(LinkedList<ServerSomething> serverList) {
        Server.serverList = serverList;
    }
}
