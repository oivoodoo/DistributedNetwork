package Voodoo.Network.Distribution.Factory;

import Voodoo.Network.Distribution.Clients.TcpClient;
import Voodoo.Network.Distribution.Clients.UdpClient;
import Voodoo.Network.Distribution.Parser.NetworkCommand;
import Voodoo.Network.Distribution.Parser.NetworkRunner;

import java.util.Hashtable;
import java.util.logging.Logger;

class NetworkType {
    public static final int UDP = 0;
    public static final int TCP = 1;
}

/**
 * User: oivoodoo
 * Date: Nov 16, 2009
 * Time: 7:51:15 PM
 */
public class BaseFactoryItem implements NetworkRunner {
    protected TcpClient tcpClient = null;
    protected UdpClient udpClient = null;
    protected Hashtable<String, Action> commands = new Hashtable<String, Action>(); 
    
    protected Logger logger = Logger.getLogger(BaseFactoryItem.class.getName());

    public BaseFactoryItem(TcpClient tcpClient, UdpClient udpClient) {
        this.tcpClient = tcpClient;
        this.udpClient = udpClient;
    }

    public void execute(int type, String line) {
        try {
            switch(type) {
                case NetworkType.UDP:
                    tcpClient.send(line);
                    break;
                case NetworkType.TCP:
                    udpClient.send(line);
                    break;
            }
        } catch(Exception ex) {
            logger.info(ex.getMessage());
        }
    }

    public void receive(String line) {
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
            
        }
    }
}
