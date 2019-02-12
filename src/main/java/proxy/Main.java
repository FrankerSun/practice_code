package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author f.s.
 * @date 2018/12/3
 */
public class Main {

    public static void main(String[] args) {

        Girl girl = new BeautifulGirl();

        InvocationHandler handler = new GirlInvocationHandle<Girl>(girl);

        Girl girlProxy = (Girl) Proxy.newProxyInstance(
                Girl.class.getClassLoader(), new Class<?>[]{Girl.class}, handler);

        girlProxy.sayHi();
    }

}
