package proxy.dynamicjdk;

import lombok.extern.slf4j.Slf4j;
import proxy.Girl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author f.s.
 * @date 2018/12/3
 */
@Slf4j
public class GirlInvocationHandle<T> implements InvocationHandler {

    private T target;

    public GirlInvocationHandle(T target) {
        this.target = target;
    }

    public Girl getGirlProxy(){
        return (Girl) Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        log.info("invoke before");
        Object result = method.invoke(target, args);
        log.info("invoke after");
        return result;
    }
}
