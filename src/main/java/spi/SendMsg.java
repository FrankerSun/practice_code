package spi;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.Adaptive;
import com.alibaba.dubbo.common.extension.SPI;


/**
 *
 * @author f.s.
 */
//@SPI
@SPI("a")
public interface SendMsg {

    @Adaptive
    void sendMsg(String msg, URL url);
}
