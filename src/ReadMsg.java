import java.io.*;

public class ReadMsg extends Thread {
    private static final String EXIT = "exit";

    @Override
    public void run() {
        try {
            String str;
            while ((str = Client.getIn().readLine()) != null) {
                System.out.println(str);
                if (str.trim().equals(EXIT)) {
                    break;
                }
            }
        } catch (IOException e) {
            if (!e.getMessage().contains("Socket closed") && !e.getMessage().contains("Stream closed")) {
                System.err.println("Error reading message: " + e.getMessage());
            }
        } finally {
            Client.shutdown(); // Закрываем ресурсы при завершении
        }
    }
}