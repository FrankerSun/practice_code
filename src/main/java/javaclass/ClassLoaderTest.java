package main.java.javaclass;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @author f.s.
 * @date 2018/12/4
 */
public class ClassLoaderTest {

    public static void main(String[] args) {

        try {
            String rootUrl = "http://localhost:8080/dynamic_proxy/javaClass";
            NetworkClassLoader networkClassLoader = new NetworkClassLoader(rootUrl);
            String classname = "ThreeClassLoader";
            Class clazz = networkClassLoader.loadClass(classname);
            System.out.println(clazz.getClassLoader());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


class NetworkClassLoader extends ClassLoader {

    private String rootUrl;

    NetworkClassLoader(String rootUrl) {
        this.rootUrl = rootUrl;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        Class clazz;
        // 根据类的二进制名称,获得该class文件的字节码数组
        byte[] classData = getClassData(name);
        if (classData == null) {
            throw new ClassNotFoundException();
        }

        // 将class的字节码数组转换成Class类的实例
        clazz = defineClass(name, classData, 0, classData.length);
        return clazz;
    }


    private byte[] getClassData(String name) {

        InputStream is = null;
        try {
            String path = classNameToPath(name);
            URL url = new URL(path);
            byte[] buff = new byte[1024 * 4];
            int len = -1;
            is = url.openStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((len = is.read(buff)) != -1) {
                baos.write(buff, 0, len);
            }
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    private String classNameToPath(String name) {
        return rootUrl + "/" + name.replace(".", "/") + ".class";
    }
}