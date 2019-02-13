package proxy;

import lombok.extern.slf4j.Slf4j;

/**
 * @author f.s.
 * @date 2018/12/3
 */
@Slf4j
public class BeautifulGirl implements Girl {

    @Override
    public void sayHi() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignore) {
        }
        log.info("beautiful girl say HI...");
    }
}
