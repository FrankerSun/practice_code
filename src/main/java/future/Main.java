package future;

import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author f.s.
 * @date 2019/2/22
 */
@Slf4j
public class Main {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        long startTime = System.currentTimeMillis();

        // 比较耗时的操作，其他任务不前置依赖次任务
        Callable<Ink> buyInk = () -> {
            log.info("女朋友去文具店买一次性墨水");
            Thread.sleep(3000);
            log.info("女朋友买墨水回来");
            return new Ink();
        };
        FutureTask<Ink> task = new FutureTask<>(buyInk);
        ExecutorService executorService = new ThreadPoolExecutor(1, 2, 60L,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), new DefaultThreadFactory("buyInk"),
                new ThreadPoolExecutor.DiscardOldestPolicy());
        executorService.submit(task);

        doSth();
        // 歇一会吧
        Thread.sleep(1000);

        if (!task.isDone()) {
            log.info("女朋友还没回来");
        }
        // 主线程会阻塞在这个地方
        Ink ink = task.get();

        log.info("写起来");
        Pan pan = new Pan();
        start(ink, pan);

        log.info("耗时" + (System.currentTimeMillis() - startTime));
        // 关闭线程池
        executorService.shutdownNow();
    }

    private static void doSth() {
        log.info("打扫下卫生");
    }

    private static void start(Ink ink, Pan pan) throws InterruptedException {
        log.info("write...");
        Thread.sleep(1000);
        log.info("写完睡觉");
    }

    @Data
    private static class Ink {

        private String color;
    }

    @Data
    private static class Pan {

        private String price;
    }
}
