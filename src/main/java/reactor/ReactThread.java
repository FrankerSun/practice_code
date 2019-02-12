package reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

/**
 * 可以选择使用SPI provider:
 * <p>SelectorProvider p = SelectorProvider.provider();  </p>
 * <p>selector = p.openSelector();                       </p>
 * <p>serverSocket = p.openServerSocketChannel();        </p>
 *
 * @author f.s.
 * @date 2018/12/5
 */
public class ReactThread implements Runnable {

    /**
     * Selector Tell which of a set of Channels have IO events
     */
    private final Selector selector;
    /**
     * Channels -- Connections to files, sockets etc that support non-blocking reads
     */
    private final ServerSocketChannel serverSocket;

    ReactThread(int port) throws IOException {

        selector = Selector.open();
        serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(port));
        serverSocket.configureBlocking(false);
        /* Maintain IO event status and bindings */
        SelectionKey sk = serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        sk.attach(new Acceptor());
    }

    @Override
    public void run() {
        try {
            /* 转发 */
            while (!Thread.interrupted()) {
                // OSX会阻塞在KQueueArrayWrapper.kevent0方法上
                selector.select();
                Set selected = selector.selectedKeys();
                for (Object selectedKey : selected) {
                    dispatch((SelectionKey) (selectedKey));
                }
                selected.clear();
            }
        } catch (IOException ignore) {
        }
    }

    private void dispatch(SelectionKey k) {
        Runnable r = (Runnable) (k.attachment());
        if (r != null) {
            r.run();
        }
    }

    class Acceptor implements Runnable {

        @Override
        public void run() {
            try {
                SocketChannel c = serverSocket.accept();
                if (c != null) {
                    new reactor.Handler(selector, c);
                }
            } catch (IOException ex) { /* ... */ }
        }
    }
}
