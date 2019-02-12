package reactor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Random;

/**
 * @author f.s.
 * @date 2018/12/5
 */
final class Handler implements Runnable {

    private final SocketChannel socket;
    private final SelectionKey sk;
    private ByteBuffer input = ByteBuffer.allocate(1024);
    private ByteBuffer output = ByteBuffer.allocate(1024);
    private static final int READING = 0;
    private static final int SENDING = 1;
    private int state = READING;

    Handler(Selector sel, SocketChannel c) throws IOException {
        socket = c;
        c.configureBlocking(false);
        // Optionally try first read now
        sk = socket.register(sel, 0);
        sk.attach(this);
        sk.interestOps(SelectionKey.OP_READ);
        sel.wakeup();
    }

    private boolean inputIsComplete() {
        System.out.println("inputIsComplete");
        Random random = new Random();
        return random.nextBoolean();
    }

    private boolean outputIsComplete() {
        System.out.println("outputIsComplete");
        Random random = new Random();
        return random.nextBoolean();
    }

    void process() {
        System.out.println("process");
    }

    @Override
    public void run() {
        try {
            if (state == READING) {
                read();
            } else if (state == SENDING) {
                send();
            }
        } catch (IOException ignore) {
        }
    }

    private void read() throws IOException {
        socket.read(input);
        if (inputIsComplete()) {
            process();
            state = SENDING;
            // Normally also do first write now
            sk.interestOps(SelectionKey.OP_WRITE);
        }
    }

    private void send() throws IOException {
        socket.write(output);
        if (outputIsComplete()) {
            sk.cancel();
        }
    }
}
