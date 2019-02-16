package proxy.dunamiccglib;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Random;

/**
 * 动态代理类实现了一个方法拦截器接口
 *
 * @author f.s.
 * @date 2019/2/16
 */
@Slf4j
public class DynamicProxy<T> implements MethodInterceptor {

    private T targetObject;

    /**
     * 代理对象
     */
    public Object getProxyObject(T object) {
        this.targetObject = object;
        // 增强器，动态代码生成器
        Enhancer enhancer = new Enhancer();
        // 回调方法
        enhancer.setCallback(this);
        // 设置生成类的父类类型
        enhancer.setSuperclass(targetObject.getClass());
        // 动态生成字节码并返回代理对象
        return enhancer.create();
    }


    @Override
    public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {

        long start = System.currentTimeMillis();
        this.idle();
        Object result = methodProxy.invoke(targetObject, args);

        log.info("spend time --> " + (System.currentTimeMillis() - start));
        return result;
    }

    private void idle() {
        try {
            int n = new Random().nextInt(555);
            Thread.sleep(n);
        } catch (InterruptedException e) {
            log.error("InterruptedException", e);
        }
    }

}