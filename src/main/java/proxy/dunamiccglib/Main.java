package proxy.dunamiccglib;

import proxy.BeautifulGirl;
import proxy.FantasticMan;
import proxy.Girl;

/**
 * @author f.s.
 * @date 2019/2/16
 */
public class Main {

    public static void main(String[] args) {

        Girl girl = (Girl) new DynamicProxy<Girl>().getProxyObject(new BeautifulGirl());
        girl.sayHi();

        // 注意FantasticMan是一个类，并没有实现接口。[cglib支持对类代理，但是不支持final修饰的类]
        FantasticMan fantasticMan = (FantasticMan) new DynamicProxy<FantasticMan>().getProxyObject(new FantasticMan());
        fantasticMan.sayHello();
    }
}
