import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private String userName;
    private BufferedWriter writer;
    private BufferedReader reader;

    public Client(Socket socket, String userName) {
        try {
            this.socket = socket;
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.userName = userName;
        } catch (IOException e) {
            onLeaving(this.socket, this.reader, this.writer);
        }
    }

    public static void main(String[] args) throws IOException {
        var socket = new Socket("localhost", 1234);
        System.out.println("Enter your username:");
        var client = new Client(socket, new Scanner(System.in).nextLine());
        client.listenToMessages();
        client.sendMessage();
    }

    public void sendMessage() {
        try {
            write(userName);
            var cin = new Scanner(System.in);
            while (this.socket.isConnected())
                write(userName + ": " + cin.nextLine());
        } catch (IOException e) {
            onLeaving(this.socket, this.reader, this.writer);
        }
    }

    public void listenToMessages() {
        new Thread(() -> {
            while (socket.isConnected())
                try {
                    System.out.println(this.reader.readLine());
                } catch (IOException e) {
                    onLeaving(this.socket, this.reader, this.writer);
                    break;
                }
        }).start();
    }

    private void onLeaving(Socket socket, BufferedReader reader, BufferedWriter writer) {
        try {
            if (reader != null) reader.close();
            if (writer != null) writer.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void write(String string) throws IOException {
        writer.write(string);
        writer.newLine();
        writer.flush();
    }
}
