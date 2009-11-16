package Voodoo.Network.Distribution;

import java.util.UUID;

/**
 * User: oivoodoo
 * Date: Nov 10, 2009
 * Time: 10:45:42 PM
 */
public class Session {
    private String id;
    private String host;

    private Session(String id, String host) {
        this.id = id;
        this.host = host;
    }

    public String getId() {
        return id;
    }

    public String getHost() {
        return host;
    }

    //  Create new session generating unique id for determining what
    // are certain process do you use.
    public static Session newSession(String host) {
        return new Session(UUID.randomUUID().toString(), host);
    }

    public static Session newSession(String id, String host) {
        return new Session(id, host);
    }
}
