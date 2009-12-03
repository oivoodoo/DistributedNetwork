package Voodoo.Network.Distribution.Servers;

import static Voodoo.Network.Distribution.Constants.PACKET_CHUNK;
import static Voodoo.Network.Distribution.Constants.TCP_PORT;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * User: oivoodoo
 * Date: Nov 15, 2009
 * Time: 3:39:49 PM
 */
public class TcpServer extends BaseServer {
    private ServerSocket server = null;

    public TcpServer() throws Exception {
        super(TCP_PORT);
        server = new ServerSocket();
        server.bind(new InetSocketAddress(port));
        host = server.getInetAddress().getHostName();
    }

    @Override
    public void run() {
        try {
            while(true) // TODO: Make timeout mechanizm for removing obv. cycles.
            {
                Socket socket = server.accept();
                if (socket.getReceiveBufferSize() != -1) {
                    packetBuilder = new StringBuilder();
                    buffer = new char[PACKET_CHUNK];
                    reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    int bytes = 0;
                    while((bytes = reader.read(buffer, 0, PACKET_CHUNK)) != -1) {
                        packetBuilder.append(new String(buffer));
                    }
                    logger.info(String.format("Get buffer: %s", getPacket()));
                    logger.info("Execute command for current found packet.");
                    execute();
                    logger.info("End of execution.");
                }
            }
        } catch(Exception ex) {
            logger.info(ex.getMessage());
        } finally {
            close();
        }
    }

    @Override
    protected void execute() {
        logger.info(String.format("Run execution for tcp server with ''", getPacket()));
    }

    @Override
    public void close() {
        super.close();
        try {
            if (server != null) {
                server.close();
            }
        } catch(Exception ex) {
            logger.info(ex.getMessage());
        }
    }
}
