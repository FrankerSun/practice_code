package javaclass;

import com.google.common.collect.Lists;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.ArrayList;

/**
 * @author f.s.
 * @date 2018/12/4
 */
public class AnnotationT {

    public void first() {
        ArrayList<Long> longs = Lists.newArrayList(12L, 13L);
        System.out.println(longs);
    }

    public void classForName() {
        try {
            Class t = Class.forName("java.lang.Thread");
            Object o = t.newInstance();
            Package aPackage = t.getPackage();
            Annotation[] annotations = t.getAnnotations();
            t.getClassLoader();
            Constructor[] constructors = t.getConstructors();


            Class<?> aClass = Class.forName("java.lang.Thread", true, Thread.currentThread().getContextClassLoader());
            System.out.println( t == aClass);
            System.out.println();

        } catch (ClassNotFoundException ignore) {
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

}
