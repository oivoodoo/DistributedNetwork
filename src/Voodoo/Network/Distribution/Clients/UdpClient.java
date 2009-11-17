package Voodoo.Network.Distribution.Clients;

import Voodoo.Network.Distribution.Constants;
import static Voodoo.Network.Distribution.Constants.DATAGRAM_PORT;

import java.net.*;

/**
 * User: oivoodoo
 * Date: Nov 16, 2009
 * Time: 7:48:11 PM
 */
public class UdpClient extends BaseClient {
    private DatagramSocket socket = null;

    public UdpClient() throws Exception {
        super(DATAGRAM_PORT);

        socket = new DatagramSocket();
    }

    private DatagramPacket makePacket(String line) {
        byte[] bytes = line.getBytes();
        return new DatagramPacket(bytes, 0, bytes.length);
    }

    @Override
    public void send(String line) throws Exception {
        socket.send(makePacket(line));
    }

    @Override
    public void connect(String host) throws Exception {
        socket = new DatagramSocket(new InetSocketAddress(host, port));
    }

    @Override
    public void close() {
        try {
            if(socket != null) {
                socket.close();
            }
        } catch(Exception ex) {
            logger.info(ex.getMessage());
        }
    }
}
