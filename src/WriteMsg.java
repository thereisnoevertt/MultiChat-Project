import java.io.*;
import java.util.Scanner;

public class WriteMsg extends Thread {
    private static final String EXIT = "exit\n";

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        try {
            while (true) {
                String userWord = scanner.nextLine();
                Client.getOut().write(userWord + "\n");
                Client.getOut().flush();
                if (userWord.trim().equals("exit")) {
                    System.out.println("Диалог закончен");
                    break; // Завершаем цикл
                }
            }
        } catch (IOException e) {
            System.err.println("Error sending message: " + e.getMessage());
        } finally {
            scanner.close();
            Client.shutdown(); // Вызываем shutdown после завершения
        }
    }
}