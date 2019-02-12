package proxy;

/**
 * @author f.s.
 * @date 2018/12/3
 */
public class BeautifulGirl implements Girl {

    @Override
    public void sayHi() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignore) {
        }
        System.out.println("beautiful girl say HI...");
    }
}
