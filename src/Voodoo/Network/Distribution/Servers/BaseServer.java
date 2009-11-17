package Voodoo.Network.Distribution.Servers;

import java.io.BufferedReader;
import java.util.logging.Logger;

/**
 * User: oivoodoo
 * Date: Nov 15, 2009
 * Time: 2:47:55 PM
 */
public abstract class BaseServer extends Thread {
    private Thread serverThread = new Thread(this);
    protected char[] buffer;
    protected StringBuilder packetBuilder = null;
    protected BufferedReader reader = null;
    protected int port = 0;

    protected Logger logger = Logger.getLogger(BaseServer.class.getName());

    public BaseServer(int port) throws Exception {
        logger.info(String.format("Create Voodoo.Network.Distribution.Servers.BaseServer %d", port));
        this.port = port;
        serverThread.start();
    }

    protected abstract void execute();

    protected String getPacket() {
        if (packetBuilder != null) {
            return packetBuilder.toString();
        }
        return "";
    }

    public void close() {
        try {
            if (reader != null) {
                reader.close();
            }
        } catch(Exception ex) {
            logger.info(ex.getMessage());
        }
    }
}

