package Voodoo.Network.Distribution.Factory;

import Voodoo.Network.Distribution.Clients.TcpClient;
import Voodoo.Network.Distribution.Clients.UdpClient;
import Voodoo.Network.Distribution.Commands.MasterCommands;
import Voodoo.Network.Distribution.Constants;
import Voodoo.Network.Distribution.Parser.NetworkCommand;

import java.util.Hashtable;

/**
 * User: oivoodoo
 * Date: Nov 16, 2009
 * Time: 7:49:04 PM
 */
public class Master extends BaseFactoryItem {
    private Hashtable<String, String> hosts = new Hashtable<String, String>();

    public Master(TcpClient tcpClient, UdpClient udpClient) {
        super(tcpClient, udpClient);

        commands.put(MasterCommands.GET_MEMORY, new MemoryAction());
        commands.put(MasterCommands.GATHER, new GatherAction());
    }

    class MemoryAction implements Action {
        public void execute(NetworkCommand command) throws Exception {
            for(String key : hosts.keySet()) {
                try {
                    tcpClient.connect(hosts.get(key));
                    tcpClient.send(NetworkCommand.createPacket(MasterCommands.GET_MEMORY, key));
                    tcpClient.close();
                } catch(Exception ex) {
                    logger.info(ex.getMessage());
                }
            }
        }
    }

    class GatherAction implements Action {
        public void execute(NetworkCommand command) throws Exception {
            udpClient.connect(Constants.BROADCAST);
            udpClient.send(NetworkCommand.createPacket(MasterCommands.GATHER));
            udpClient.close();
        }
    }
}
