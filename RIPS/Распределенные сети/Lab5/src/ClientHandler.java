import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler implements Runnable {
    private static final List<ClientHandler> clientHandlers = new ArrayList<>();

    private Socket socket;
    private String userName;
    private BufferedWriter writer;
    private BufferedReader reader;

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.userName = this.reader.readLine();
            clientHandlers.add(this);
            broadCastMessage("Server: " + this.userName + "landed on server.");
        } catch (IOException e) {
            serverLog(this.socket, this.reader, this.writer);
        }
    }

    @Override
    public void run() {
        while (socket.isConnected())
            try {
                broadCastMessage(this.reader.readLine());
            } catch (IOException e) {
                serverLog(this.socket, this.reader, this.writer);
                break;
            }
    }

    public void write(String string) throws IOException {
        writer.write(string);
        writer.newLine();
        writer.flush();
    }

    private void broadCastMessage(String message) {
        clientHandlers.stream()
                .filter(x -> x != this)
                .forEach(x -> {
                    try {
                        x.write(message);
                    } catch (IOException e) {
                        serverLog(this.socket, this.reader, this.writer);
                    }
                });
    }

    private void leaveUser() {
        clientHandlers.remove(this);
        broadCastMessage("Server:" + this.userName + " left the chat.");
    }

    private void serverLog(Socket socket, BufferedReader reader, BufferedWriter writer) {
        this.leaveUser();
        try {
            if (reader != null) reader.close();
            if (writer != null) writer.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
