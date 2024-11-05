import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Socket Server class.
 *
 * @author Рома
 */
public class SocketServer {
    private static final int BUFFER_SIZE = 1024;
    private final static Logger logger = LogManager.getLogger();

    private final InetSocketAddress address;
    private final Set<SocketChannel> session = new HashSet<>();

    private Selector selector;

    /**
     * Create new Instance of server socket class.
     *
     * @param host host of server run.
     * @param port worked port.
     */
    public SocketServer(String host, int port) {
        this.address = new InetSocketAddress(host, port);
    }

    /**
     * Start new Server Chat witch listen of new clients.
     *
     * @throws IOException if an I/O errors occurs.
     */
    public void start() throws IOException {
        this.selector = Selector.open();
        ServerSocketChannel.open()
                .bind(address)
                .configureBlocking(false)
                .register(this.selector, SelectionKey.OP_ACCEPT);

        logger.info("Server started...");

        while (true) {
            this.selector.select();
            var keys = this.selector.selectedKeys().iterator();

            while (keys.hasNext()) {
                var key = (SelectionKey) keys.next();
                keys.remove();

                if (!key.isValid())
                    continue;

                if (key.isAcceptable())
                    accept(key);
                else if (key.isReadable())
                    read(key);
            }
        }
    }

    /**
     * Read messages from concrete user.
     *
     * @param key key of concrete user channel.
     * @throws IOException if an I/O errors occurs.
     */
    private void read(SelectionKey key) throws IOException {
        var channel = (SocketChannel) key.channel();
        var address = channel.socket().getRemoteSocketAddress();

        var byteBuffer = ByteBuffer.allocate(BUFFER_SIZE);

        int readBytes = channel.read(byteBuffer);

        var message = "";
        if (readBytes == -1) {
            this.session.remove(channel);
            message = "Server: user left " + address + "\r\n";
            channel.close();
            key.cancel();
        } else {
            var gotData = new String(Arrays.copyOfRange(byteBuffer.array(), 0, readBytes - 1));

            if (gotData.equals("ERROR\r")) {
                try {
                    throw new UserError("" + address);
                } catch (UserError userError) {
                    logger.error(userError.getMessage());
                }
            }

            logger.info("Got from: " + channel.socket().getPort() + ": " + gotData);
            message = address + ": " + gotData + "\r\n";
        }

        broadcast(channel, message.getBytes());
    }

    /**
     * Register new users channel.
     *
     * @param key key of concrete user channel.
     * @throws IOException if an I/O errors occurs.
     */
    private void accept(SelectionKey key) throws IOException {
        SocketChannel channel = ((ServerSocketChannel) key.channel()).accept();

        channel.configureBlocking(false).register(this.selector, SelectionKey.OP_READ);

        this.session.add(channel);
        var message = "Server: new user " + channel.socket().getRemoteSocketAddress() + "\r\n";
        broadcast(channel, message.getBytes());
    }

    /**
     * Sends a data to all users except the directing user.
     *
     * @param currentChannel the directing user who sen data.
     * @param data           some byte data.
     */
    private void broadcast(SocketChannel currentChannel, byte[] data) {
        var byteBuffer = ByteBuffer.allocate(BUFFER_SIZE);
        byteBuffer.put(data);
        byteBuffer.flip();

        this.session.stream()
                .filter(channel -> channel != currentChannel)
                .forEach(channel -> {
                    try {
                        channel.write(byteBuffer);
                        byteBuffer.flip();
                    } catch (IOException e) {
                        logger.error(e.getMessage());
                        e.printStackTrace();
                    }
                });
    }

    /**
     * Launch the program.
     *
     * @param args console arguments.
     * @throws IOException if an I/O errors occurs.
     */
    public static void main(String[] args) throws IOException {
        new SocketServer("localhost", 8080).start();
    }
}
