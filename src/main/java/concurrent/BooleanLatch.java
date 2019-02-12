package concurrent;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * Here is a latch class that is like a CountDownLatch except that it only requires a single signal to fire.
 * Because a latch is non-exclusive, it uses the shared acquire and release methods.
 * @author f.s.
 * @date 2018/12/12
 */
public class BooleanLatch {

    private static class Sync extends AbstractQueuedSynchronizer {
        boolean isSignalled() {
            return getState() != 0;
        }

        @Override
        protected int tryAcquireShared(int ignore) {
            return isSignalled() ? 1 : -1;
        }

        @Override
        protected boolean tryReleaseShared(int ignore) {
            setState(1);
            return true;
        }
    }

    private final Sync sync = new Sync();

    public boolean isSignalled() {
        return sync.isSignalled();
    }

    public void signal() {
        sync.releaseShared(1);
    }

    public void await() throws InterruptedException {
        sync.acquireSharedInterruptibly(1);
    }
}