import java.net.InetAddress;

public class DistributionHelper {
    public static byte[] toBytes(String line) {
        byte bytes[] = new byte[line.length()];
        for(int i = 0; i < line.length(); i++) {
            bytes[i] = (byte) line.charAt(i);
        }
        return bytes;
    }

    public static String getValidHost(String hostAddress, int port)
    {
        return String.format("%s#%d", hostAddress.replace('.','_'), port);
    }

    public static String getHost(String line) {
        String[] params = line.split("#");
        if (params.length > 1) {
            return params[0].replace('_','.');
        }
        return "127.0.0.1";
    }

    public static int getPort(String line) {
        String[] params = line.split("#");
        if (params.length > 1) {
            return Integer.parseInt(params[1]);
        }
        // TODO: it may cause unhandle errors, please review this code again.
        return DistributionConstants.TCP_PORT;
    }
}
