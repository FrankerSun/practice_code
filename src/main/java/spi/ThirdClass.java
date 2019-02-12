package spi;

import com.alibaba.dubbo.common.URL;

/**
 * @author f.s.
 * @date 2019/1/23
 */
public class ThirdClass implements SendMsg {

    @Override
    public void sendMsg(String msg, URL url) {

        System.out.println("ThirdClass " + msg);

    }
}
