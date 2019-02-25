package future.completablefuturedesign;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;

/**
 * @author f.s.
 * @date 2019/2/25
 */
@Slf4j
public class FutureTest {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        WaitingFuture<Integer> completableFuture = SpinLoopWaitingFuture.supplyAsync(() -> {
            log.info("supplyAsync...");
            sleep();
            return 212;
        });


        // 阻塞Main线程
        Integer integer = completableFuture.get();
        log.info("result = {} ", integer);


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
