package Voodoo.Network.Distribution.Factory;

import Voodoo.Network.Distribution.Clients.TcpClient;
import Voodoo.Network.Distribution.Clients.UdpClient;
import Voodoo.Network.Distribution.Commands.SlaveCommands;
import Voodoo.Network.Distribution.Constants;
import Voodoo.Network.Distribution.Parser.NetworkCommand;
import Voodoo.Network.Distribution.Session;

/**
 * User: oivoodoo
 * Date: Nov 16, 2009
 * Time: 7:50:12 PM
 */
public class Slave extends BaseFactoryItem {
    private Session leftNode = null;
    private Session rightNode = null;
    private Session manager = null;

    public Slave(TcpClient tcpClient, UdpClient udpClient) {
        super(tcpClient, udpClient);

        commands.put(SlaveCommands.TRY_MANAGER, new TryManagerAction());
        commands.put(SlaveCommands.DEAD_NODE, new DeadNodeAction());
        commands.put(SlaveCommands.MEMORY, new MemoryAction());
    }

    class TryManagerAction implements Action {
        public void execute(NetworkCommand command) throws Exception {
            udpClient.connect(Constants.BROADCAST);
            udpClient.send(NetworkCommand.createPacket(SlaveCommands.TRY_MANAGER));
            udpClient.close();
        }
    }

    class DeadNodeAction implements Action {
        public void execute(NetworkCommand command) throws Exception {
            udpClient.connect(Constants.BROADCAST);
            udpClient.send(NetworkCommand.createPacket(SlaveCommands.DEAD_NODE, command.getParams().get(0)));
            udpClient.close();
        }
    }

    class MemoryAction implements Action {
        public void execute(NetworkCommand command) throws Exception {
            if (manager != null) {
                tcpClient.connect(manager.getHost());
                tcpClient.send(NetworkCommand.createPacket(SlaveCommands.MEMORY, Long.toString(Runtime.getRuntime().totalMemory())));
                tcpClient.close();
            } else {
                logger.info("Manager isn't defined.");
            }
        }
    }
}
