public class DistributionHelper {
    public static byte[] toBytes(String line) {
        byte bytes[] = new byte[line.length()];
        for(int i = 0; i < line.length(); i++) {
            bytes[i] = (byte) line.charAt(i);
        }
        return bytes;
    }
}
