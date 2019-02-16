package proxy.dynamicjdk;

import proxy.BeautifulGirl;
import proxy.Girl;

import java.lang.reflect.Proxy;

/**
 * 动态代理是在运行后生成代理类。[JDK/CGLIB]
 * 静态代理是在编译期生成代理类。
 *
 * @author f.s.
 * @date 2018/12/3
 */
public class DynamicJdkMain {

    public static void main(String[] args) {

        Girl girl = new BeautifulGirl();
        GirlInvocationHandle handler = new GirlInvocationHandle<>(girl);

        Girl girlProxy = (Girl) Proxy.newProxyInstance(
                Girl.class.getClassLoader(), new Class<?>[]{Girl.class}, handler);
        girlProxy.sayHi();

        Girl beautifulGirlProxy = handler.getGirlProxy();
        beautifulGirlProxy.sayHi();
    }

}
