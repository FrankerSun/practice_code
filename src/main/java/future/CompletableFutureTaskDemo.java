package future;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

/**
 * @author f.s.
 * @date 2019/2/22
 */
@Slf4j
public class CompletableFutureTaskDemo {

    public static void main(String[] args) throws InterruptedException {

        long startTime = System.currentTimeMillis();
        CountDownLatch countDownLatch = new CountDownLatch(1);

        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            log.info("supplyAsync...");
            sleep();
            return 212;
        });

        // Promise
        completableFuture = completableFuture.thenCompose(i -> CompletableFuture.supplyAsync(() -> {
            log.info("Promise completableFuture.thenCompose...");
            sleep();
            countDownLatch.countDown();
            return i + 88;
        }));

        // 回调
        completableFuture.whenComplete((result, e) -> log.info("result = " + result));

        // 无论相关方法是否已经获取值，都强制设置future.get()所获取的值为value
        if (new Random().nextBoolean()){
            completableFuture.obtrudeValue(10);
            countDownLatch.countDown();
        }

        log.info("Main线程耗时:" + (System.currentTimeMillis() - startTime) + " ms");

        // 阻塞Main线程
        countDownLatch.await();
    }

    private static void sleep() {

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}