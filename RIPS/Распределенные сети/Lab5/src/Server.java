import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    private final ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public static void main(String[] args) throws IOException {
        new Server(new ServerSocket(1234)).startServer();
    }

    public void startServer() {
        try {
            while (!serverSocket.isClosed()) {
                var server = serverSocket.accept();
                System.out.println("New User Arrived!!");
                new Thread(new ClientHandler(server)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            disconnect();
        }
    }

    public void disconnect() {
        try {
            if (serverSocket != null) serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
