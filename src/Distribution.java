import java.net.DatagramPacket;
import java.net.ServerSocket;
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
    private ServerSocket serverSocket = null;
    private DatagramSocket datagramSocket = null;
    private Logger logger = Logger.getLogger(Distribution.class.getName());

    public Distribution() {
        try {
            logger.info("Start Create Sockets");
            socket = new Socket();
            serverSocket = new ServerSocket(DistributionConstants.TCP_PORT);
            datagramSocket = new DatagramSocket(DistributionConstants.DATAGRAM_PORT);
            logger.info("End Create Sockets");
        } catch(Exception ex) {
            logger.info(ex.getMessage());
        } finally {
            closeSockets();
        }
    }

    public void start() {
        super.start();

        entry();
    }

    private void sendDatagramPacket(String line) throws Exception {
        logger.info(String.format("Send Datagram Packet %s", line));
        DatagramPacket packet = new DatagramPacket(DistributionHelper.toBytes(line), 0, line.length());
        datagramSocket.send(packet);
    }


    public void entry() {
        try {
            logger.info("Start Entry Step");
            sendDatagramPacket(String.format(DistributionCommands.IAMTHERE,
                    DistributionHelper.getValidHost(serverSocket.getInetAddress().getHostName(), serverSocket.getLocalPort())));
            logger.info("End Entry Step");
        } catch(Exception ex) {
            logger.info(ex.getMessage());
        }
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
