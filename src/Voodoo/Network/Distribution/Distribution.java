package Voodoo.Network.Distribution;

import Voodoo.Network.Distribution.Clients.TcpClient;
import Voodoo.Network.Distribution.Clients.UdpClient;
import Voodoo.Network.Distribution.Commands.SlaveCommands;
import Voodoo.Network.Distribution.Factory.NetworkFactory;
import Voodoo.Network.Distribution.Parser.NetworkCommand;
import Voodoo.Network.Distribution.Parser.NetworkRunner;
import Voodoo.Network.Distribution.Servers.TcpServer;
import Voodoo.Network.Distribution.Servers.UdpServer;

import java.util.logging.Logger;

/**
 * User: oivoodoo
 * Date: Nov 3, 2009
 * Time: 9:59:28 PM
 */
public class Distribution extends Thread {
    private boolean isRoot;
    private UdpServer udpServer = null;
    private TcpServer tcpServer = null;
    private TcpClient tcpClient = null;
    private UdpClient udpClient = null;
    private NetworkRunner runner = null;

    private Logger logger = Logger.getLogger(Distribution.class.getName());

    public Distribution() {
        try {
            logger.info("Start Create Sockets");
            // If we manager of distributed network, we will use 'socket',
            // for sending commands to all hosts in the you network,
            // and then 'serverSocket' collect all 'memory' information
            // and create report for client.
            tcpServer = new TcpServer();
            tcpClient = new TcpClient();
            udpServer = new UdpServer();
            udpClient = new UdpClient();

            logger.info("Create network runner.");
            runner = NetworkFactory.createSlave(tcpClient, udpClient);
            logger.info("End Create Sockets");
        } catch(Exception ex) {
            logger.info(ex.getMessage());
        } finally {
            closeSockets();
        }
    }

    public void start() {
        super.start();

        // Try to connect to network via ring algorithm using UDP connection.
        // Broadcasts udp packet and then collect all information from another
        // machines, if you haven't any data(hosts, ports) from another hosts by timeout,
        // you will stay manager of distributed network.
        runner.execute(NetworkCommand.createPacket(SlaveCommands.ENTRY, tcpServer.getHost()));
    }

    private void closeSockets() {
        try {
            if (udpServer != null) {
                udpServer.close();
            }
            if (tcpServer != null) {
                tcpServer.close();
            }
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

    // Root - manager of network.
    public boolean isRoot() {
        return this.isRoot;
    }
}

