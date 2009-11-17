package Voodoo.Network.Distribution.Servers;

import static Voodoo.Network.Distribution.Constants.DATAGRAM_PORT;

import java.net.DatagramSocket;

/**
 * User: oivoodoo
 * Date: Nov 15, 2009
 * Time: 3:38:01 PM
 */
public class UdpServer extends BaseServer {
    DatagramSocket server = null;

    public UdpServer() throws Exception {
        super(DATAGRAM_PORT);
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
