package Voodoo.Network.Distribution.Clients;

import java.util.logging.Logger;

/**
 * User: oivoodoo
 * Date: Nov 16, 2009
 * Time: 7:58:28 PM
 */
public abstract class BaseClient {
    protected int port;
    protected Logger logger = Logger.getLogger(BaseClient.class.getName());

    public abstract void send(String line) throws Exception;
    public abstract void connect(String host) throws Exception;
    public abstract void close();

    public BaseClient(int port) {
        this.port = port;
    }
}
