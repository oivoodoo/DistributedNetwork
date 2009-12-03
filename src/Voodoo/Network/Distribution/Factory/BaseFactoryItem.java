package Voodoo.Network.Distribution.Factory;

import Voodoo.Network.Distribution.Clients.TcpClient;
import Voodoo.Network.Distribution.Clients.UdpClient;
import Voodoo.Network.Distribution.Parser.NetworkCommand;
import Voodoo.Network.Distribution.Parser.NetworkRunner;

import java.util.Hashtable;
import java.util.logging.Logger;

/**
 * User: oivoodoo
 * Date: Nov 16, 2009
 * Time: 7:51:15 PM
 */
public class BaseFactoryItem implements NetworkRunner {
    protected TcpClient tcpClient = null;
    protected UdpClient udpClient = null;
    protected Hashtable<String, Action> receiveCommands = new Hashtable<String, Action>();
    protected Hashtable<String, Action> sendCommands = new Hashtable<String, Action>();
    
    protected Logger logger = Logger.getLogger(BaseFactoryItem.class.getName());

    public BaseFactoryItem(TcpClient tcpClient, UdpClient udpClient) {
        this.tcpClient = tcpClient;
        this.udpClient = udpClient;
    }

    public void execute(String line) {
        logger.info(String.format("execute with %s", line));
        runCommand(sendCommands, line);
    }

    public void receive(String line) {
        logger.info(String.format("receive with %s", line));
        runCommand(receiveCommands, line);
    }

    protected void runCommand(Hashtable<String, Action> commands, String line) {
        NetworkCommand command = new NetworkCommand(line);
        if (commands.containsKey(command.getCommand())) {
            Action action = commands.get(command.getCommand());
            try {
                action.execute(command);
            } catch(Exception ex) {
                logger.info(ex.getMessage());
            }
        } else {
            logger.info(String.format("Warning: Command not implemented, '%s'.", line));
        }
    }

    public void close() {
        try {
            if (tcpClient != null) {
                tcpClient.close();
            }
            if (udpClient != null) {
                udpClient.close();
            }
        } catch(Exception ex) {
            logger.info(ex.getMessage());
        }
    }
}
