package Voodoo.Network.Distribution.Servers;

import static Voodoo.Network.Distribution.Constants.DATAGRAM_PORT;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

/**
 * User: oivoodoo
 * Date: Nov 15, 2009
 * Time: 3:38:01 PM
 */
public class UdpServer extends BaseServer {
    DatagramSocket server = null;

    public UdpServer() throws Exception {
        super(DATAGRAM_PORT);

        server = new DatagramSocket();
        //server.bind(new InetSocketAddress(port));
    }

    @Override
    public void run() {
        while(true) {
            //try {
                /* if (server.getReceiveBufferSize() != -1) {
                    int size = server.getReceiveBufferSize();
                    server.receive(new DatagramPacket(new byte[size], 0, size));
                }  */
            //} catch (IOException e) {
            //    logger.info(e.getMessage());
            //}
        }
    }

    @Override
    protected void execute() {
        logger.info(String.format("Run execution for '%s'", getPacket()));
    }

    @Override
    public void close() {
        super.close();
        try {
            if (server != null) {
                server.close();
            }
        } catch (Exception ex) {
            logger.info(ex.getMessage());
        }
    }
}
