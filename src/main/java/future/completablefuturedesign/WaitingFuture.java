package future.completablefuturedesign;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author f.s.
 * @date 2019/2/25
 */
public interface WaitingFuture<V> {

    /*
     * Asynchronously means that we can continue execution flow after start of waitingFuture and go back to results of execution later.
     * This means that we need some another thread to execute user function. The best pattern is to use some execution thread pool.
     * For simplicity let’s start new thread for each user function.
     */

    /**
     * 阻塞直到执行结束，执行失败时抛出异常
     *
     * @return V 返回结果
     * @throws ExecutionException 执行异常
     */
    V get() throws ExecutionException;

    /**
     * 添加超时时间
     *
     * @param timeout 超时时间
     * @param unit    时间单位
     * @return V 返回结果
     * @throws ExecutionException 执行异常
     */
    V get(long timeout, TimeUnit unit) throws TimeoutException, ExecutionException;
}
