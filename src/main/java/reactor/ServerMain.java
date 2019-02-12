package reactor;

/**
 * @author f.s.
 * @date 2018/12/6
 */
public class ServerMain {

    public static void main(String[] args) throws Exception {

        reactor.ReactThread reactThread = new reactor.ReactThread(8081);
        reactThread.run();
    }
}
