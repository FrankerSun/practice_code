package netty.asimple;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import netty.Configure;

import java.util.Date;


/**
 * @author f.s.
 * @date 2019/2/21
 */
public class Client {

    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) {
                        ch.pipeline().addLast(new StringEncoder());
                    }
                });

        Channel channel = bootstrap.connect(Configure.SERVER_HOST, Configure.SERVER_PORT).channel();

        int whileBreak = 1000;
        int j = 0;
        while (true) {
            channel.writeAndFlush(new Date() + ": Test Send Msg!");
            Thread.sleep(1000);
            j++;
            if (j > whileBreak) {
                break;
            }
        }
    }


}
