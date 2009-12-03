package Voodoo.Network.Distribution.Parser;

import java.util.ArrayList;

/**
 * User: oivoodoo
 * Date: Nov 16, 2009
 * Time: 7:47:14 PM
 */
public class NetworkCommand {
    protected static final String COMMAND_SPLITTER = ":";
    protected String rawLine;
    protected String command;
    protected ArrayList<String> params = new ArrayList<String>();

    // line: <command>:param1,param2,...<params>\r\n
    public NetworkCommand(String line) {
        this.rawLine = line;

        parseCommand();
        parseParams();
    }

    protected void parseCommand() {
        command = this.rawLine.split(":")[0]; // TODO: Check command if line without params.
    }

    protected void parseParams() {
        String[] rawParams = this.rawLine.replaceAll(getRawCommand(command),"").replaceAll("\r\n","").split(",");
        for(String param : rawParams) {
            params.add(param);
        }
    }

    public static String createPacket(String command, String ...params) {
        String packet = getRawCommand(command);
        for(String value : params) {
            packet += value + ",";
        }
        if (packet.indexOf(',') == packet.length() - 1) {
            packet = packet.substring(0, packet.length() - 2); // TODO: Make sense to check correct packet.
        }
        return String.format("%s\r\n", packet);
    }

    public String getPacket()
    {
        return rawLine;
    }

    protected static String getRawCommand(String rawCommand) {
        return String.format("%s%s", rawCommand, COMMAND_SPLITTER);
    }

    public String getCommand() {
        return command;
    }

    public ArrayList<String> getParams() {
        return params;
    }
}
