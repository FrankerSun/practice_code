package javaclass;

import lombok.extern.slf4j.Slf4j;

/**
 * @author f.s.
 * @date 2018/12/4
 */
@Slf4j
public class ThreeClassLoader {

    public static void main(String[] args) {

        ClassLoader appLoader = ThreeClassLoader.class.getClassLoader();

        ClassLoader extClassLoader = appLoader.getParent();

        ClassLoader parent = extClassLoader.getParent();

        log.info("threadClassLoader");

    }

    /*
     * 类加载器：三个层次
     *  BootStrapClassLoader    始祖
     *  ExtClassLoader          祖
     *  AppClassLoader          父
     *
     *  CustomClassLoader       子
     *
     *  机制--全局负责与双亲委托[Tomcat违反了双亲委托]
     */

    @Override
    public String toString() {
        return super.toString();
    }
}
