package Voodoo.Network.Distribution.Factory;

import Voodoo.Network.Distribution.Clients.TcpClient;
import Voodoo.Network.Distribution.Clients.UdpClient;
import Voodoo.Network.Distribution.Parser.NetworkRunner;

/**
 * User: oivoodoo
 * Date: Nov 16, 2009
 * Time: 7:45:13 PM
 */
public class NetworkFactory {

    public NetworkRunner createMaster(TcpClient tcpClient, UdpClient udpClient) {
        return new Master(tcpClient, udpClient);
    }

    public NetworkRunner createSlave(TcpClient tcpClient, UdpClient udpClient) {
        return new Slave(tcpClient, udpClient);
    }

}
