package future.completablefuturedesign;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.LockSupport;
import java.util.function.Supplier;

/**
 * @author f.s.
 * @date 2019/2/25
 */
public class SpinLoopWaitingFuture<V> implements WaitingFuture<V> {

    /* 怎么接受用户传进来的函数的执行结果和异常呢？  ---  运行用户传进来的函数，绑定个数据容器就可以了 */

    /**
     * 返回结果 -- 声明周期和本类实例对象一样长
     */
    private volatile V result;

    /**
     * 异常
     */
    private volatile Throwable throwable;
    /**
     * marker that indicates that user function has finished
     * 标记是否完成
     */
    private volatile boolean finished;

    private volatile Thread thread;


    @Override
    public V get() throws ExecutionException {
        this.thread = Thread.currentThread();
        LockSupport.park(this);

        if (throwable != null) {
            throw new ExecutionException(throwable);
        }

        return result;
    }

    @Override
    public V get(long timeout, TimeUnit unit) throws ExecutionException, TimeoutException {

        if (unit == null) {
            throw new NullPointerException();
        }

        this.thread = Thread.currentThread();
        long allowedExecutionNanos = unit.toNanos(timeout);
        LockSupport.parkNanos(allowedExecutionNanos);

        if (!finished) {
            throw new TimeoutException();
        }

        if (throwable != null) {
            throw new ExecutionException(throwable);
        }

        return result;
    }

    /**
     * 异步执行用户逻辑
     */
    public static <V> WaitingFuture<V> supplyAsync(Supplier<V> userFunction) {

        SpinLoopWaitingFuture<V> waitingFuture = new SpinLoopWaitingFuture<>();
        RunnableWaitingFuture<V> future = new RunnableWaitingFuture<>(userFunction, waitingFuture);
        // 简单创建一个线程来执行任务
        new Thread(future).start();
        return waitingFuture;
    }


    /**
     * 运行用户传进来的逻辑的内部类
     */
    private static class RunnableWaitingFuture<V> implements Runnable {
        private final Supplier<V> userFunction;
        private final SpinLoopWaitingFuture<V> waitingFuture;

        RunnableWaitingFuture(Supplier<V> userFunction, SpinLoopWaitingFuture<V> waitingFuture) {
            this.userFunction = userFunction;
            this.waitingFuture = waitingFuture;
        }

        @Override
        public void run() {
            try {
                waitingFuture.result = userFunction.get();
            } catch (Throwable throwable) {
                waitingFuture.throwable = throwable;
            } finally {
                waitingFuture.finished = true;
                LockSupport.unpark(waitingFuture.thread);
            }
        }
    }


}