package outofmemory;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author f.s.
 * @date 2019/2/20
 */
@Slf4j
public class ThreadLocalOutOfMemory {

    private static final Boolean FIX_OOM = false;

    private static ThreadLocal<List<User>> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {

        AtomicInteger threadNum = new AtomicInteger(0);

        ExecutorService executorService = new ThreadPoolExecutor(1000, 1000, 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(),
                r -> {
                    Thread thread = new Thread(r);
                    thread.setName("ThreadLocalOom-" + threadNum.getAndIncrement());
                    return thread;
                }, new ThreadPoolExecutor.CallerRunsPolicy());

        for (int i = 0; i < Integer.MAX_VALUE; i++) {

            executorService.execute(() -> {
                threadLocal.set(new ThreadLocalOutOfMemory().getData());
                Thread t = Thread.currentThread();
                log.info(t.getName());
                if (FIX_OOM) {
                    // getMap(Thread.currentThread()).remove(this) 从Map里移除当前threadLocal对应的对象
                    threadLocal.remove();
                }
            });
            try {
                Thread.sleep(500L);
            } catch (InterruptedException ignore) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private List<User> getData() {

        Random random = new Random();
        return IntStream.range(0, 100000)
                .mapToObj(i -> new User("name", "password" + i, random.nextBoolean() ? "Man" : "Girl", 18))
                .collect(Collectors.toCollection(() -> new ArrayList<>(100000)));
    }

    @Data
    class User {

        private String userName;

        private String password;

        private String sex;

        private int age;

        User(String userName, String password, String sex, int age) {
            this.userName = userName;
            this.password = password;
            this.sex = sex;
            this.age = age;
        }
    }

}
