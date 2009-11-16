package Voodoo.Network.Distribution.Factory;

import Voodoo.Network.Distribution.Parser.NetworkCommand;

/**
 * User: oivoodoo
 * Date: Nov 16, 2009
 * Time: 10:14:16 PM
 */
public interface Action {
    public void execute(NetworkCommand command) throws Exception;
}
