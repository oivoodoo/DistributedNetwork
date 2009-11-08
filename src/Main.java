/**
 * Created by IntelliJ IDEA.
 * User: oivoodoo
 * Date: Nov 3, 2009
 * Time: 8:39:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    public static void main(String[] args)
    {
        System.out.println("Get memory information for all hosts");
        Distribution distribution = new Distribution();
        distribution.start();
    }
}
