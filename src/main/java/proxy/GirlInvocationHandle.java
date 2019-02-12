package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author f.s.
 * @date 2018/12/3
 */
public class GirlInvocationHandle<T> implements InvocationHandler {

    private T target;

    public GirlInvocationHandle(T target) {
        this.target = target;
    }


    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("before");
        Object result = method.invoke(target, args);
        System.out.println("after");
        return result;
    }
}
