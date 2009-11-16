package Voodoo.Network.Distribution.Parser;

/**
 * User: oivoodoo
 * Date: Nov 16, 2009
 * Time: 7:45:49 PM
 */
public interface NetworkRunner {
    public void execute(int type, String line);
    public void receive(String line);
}
