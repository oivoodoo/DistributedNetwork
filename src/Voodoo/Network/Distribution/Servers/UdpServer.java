package Voodoo.Network.Distribution.Servers;

import static Voodoo.Network.Distribution.Constants.DATAGRAM_PORT;

/**
 * User: oivoodoo
 * Date: Nov 15, 2009
 * Time: 3:38:01 PM
 */
public class UdpServer extends BaseServer {

    public UdpServer() throws Exception {
        super(DATAGRAM_PORT);
    }

    @Override
    protected void execute() {
        logger.info(String.format("Run execution for '%s'", getPacket()));
    }
}
