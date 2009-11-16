package Voodoo.Network.Distribution;

import Voodoo.Network.Distribution.Commands.CommonCommands;
import Voodoo.Network.Distribution.Parser.NetworkHelper;
import Voodoo.Network.Distribution.Servers.TcpServer;
import Voodoo.Network.Distribution.Servers.UdpServer;

import java.net.DatagramPacket;
import java.net.Socket;
import java.net.DatagramSocket;
import java.util.logging.Logger;

/**
 * User: oivoodoo
 * Date: Nov 3, 2009
 * Time: 9:59:28 PM
 */
public class Distribution extends Thread {
    private long id = -1;
    private boolean isRoot;
    private Socket socket = null;
    private UdpServer udpServer = null;
    private TcpServer tcpServer = null;
    private DatagramSocket datagramSocket = null;

    private Logger logger = Logger.getLogger(Distribution.class.getName());

    public Distribution() {
        try {
            logger.info("Start Create Sockets");
            // If we manager of distributed network, we will use 'socket',
            // for sending commands to all hosts in the you network,
            // and then 'serverSocket' collect all 'memory' information
            // and create report for client.
            socket = new Socket();
            datagramSocket = new DatagramSocket();

            udpServer = new UdpServer();
            tcpServer = new TcpServer();

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
        try
        {
            // Send broadcast invitation for all nodes in the network. 
            entry();
        } catch(Exception ex) {
            logger.info(ex.getMessage());
        }
    }

    private void sendDatagramPacket(String line) throws Exception {
        // Common method for sending datagram packets(broadcast in the current version).
        logger.info(String.format("Send Datagram Packet %s", line));
        DatagramPacket packet = new DatagramPacket(NetworkHelper.toBytes(line), 0, line.length());
        try
        {
            datagramSocket.send(packet);
        } catch(Exception ex) {
            logger.info(ex.getMessage());
        }
    }

    public void entry() throws Exception {
        logger.info("Start Entry Step");
        sendDatagramPacket(String.format(CommonCommands.IAMTHERE, "127.0.0.1"));
//                Voodoo.Network.Distribution.Parser.NetworkHelper.getValidHost(serverSocket.getInetAddress().getHostName(),
//                        serverSocket.getLocalPort())));
        logger.info("End Entry Step");
    }

    private void closeSockets() {
        try {
            if (socket != null) {
                socket.close();
            }
            if (datagramSocket != null) {
                datagramSocket.close();
            }
        } catch(Exception ex) {
            logger.info(ex.getMessage());
        }
    }

    // Get id of network node.
    public long getId() {
        return id;
    }

    // Set id of network node.
    public void setId(long id) {
        this.id = id;
    }

    // Root - manager of network.
    public boolean isRoot() {
        return this.isRoot;
    }
}

