package Voodoo.Network.Distribution.Clients;

import Voodoo.Network.Distribution.Constants;
import static Voodoo.Network.Distribution.Constants.TCP_PORT;

import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * User: oivoodoo
 * Date: Nov 16, 2009
 * Time: 7:48:20 PM
 */
public class TcpClient extends BaseClient {
    private Socket socket = null;

    public TcpClient() throws Exception {
        super(TCP_PORT);

        socket = new Socket();
    }

    @Override
    public void close() {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch(Exception ex) {
            logger.info(ex.getMessage());
        }
    }

    @Override
    public void connect(String host) throws Exception {
        socket.connect(new InetSocketAddress(host, port));
    }

    @Override
    public void send(String line) throws Exception {
        OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
        writer.write(makePacket(line));
        writer.close();
    }

    private String makePacket(String line) {
        return String.format("%s\r\n", line);
    }
}
