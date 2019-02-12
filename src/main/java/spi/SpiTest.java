package spi;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;

/**
 * 获取某个SPI接口的adaptive实现类的规则是：
 * 1）实现类的类上面有Adaptive注解的，那么这个类就是adaptive类
 * 2）实现类的类上面没有Adaptive注解，但是在方法上有Adaptive注解，则会动态生成adaptive类
 * 3) 方法上增加@Adaptive注解，注解中的value与URL中的参数的键一致，URL中的键对应的value就是spi中的name
 *
 * @author f.s.
 * @date 2019/1/23
 */
public class SpiTest {

    public static void main(String[] args) {
        ExtensionLoader<SendMsg> loader = ExtensionLoader.getExtensionLoader(SendMsg.class);
        SendMsg adaptiveExtension = loader.getAdaptiveExtension();

        // @SPI("a") + @Adaptive 方法上有注解
        // URL noParam = URL.valueOf("hello://localhost/world");
        // adaptiveExtension.sendMsg("msg", noParam);

        URL url = URL.valueOf("hello://localhost/world?send.msg=b");
        adaptiveExtension.sendMsg("msg", url);

        // @SPI("a") + @Adaptive 方法上有注解
        // URL url2 = URL.valueOf("hello://localhost/world?send.msg=c");
        // adaptiveExtension.sendMsg("msg", url2);
        URL url2 = URL.valueOf("hello://localhost/world?send.msg=c");
        adaptiveExtension.sendMsg("msg", url2);

        // 方法上增加@Adaptive注解，注解中的value与URL中的参数的键一致，URL中的键对应的value就是spi中的name
        URL adaptiveInMethod = URL.valueOf("hello://localhost/world?w=b");
        adaptiveExtension.sendMsg("msg", adaptiveInMethod);
    }
}
