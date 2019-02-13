package proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

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

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        log.info("invoke before");
        Object result = method.invoke(target, args);
        log.info("invoke after");
        return result;
    }
}
