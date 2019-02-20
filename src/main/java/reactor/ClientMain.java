package reactor;

import lombok.extern.slf4j.Slf4j;

import javax.net.SocketFactory;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author f.s.
 * @date 2018/12/6
 */
@Slf4j
public class ClientMain {

    private Socket socket;
    private OutputStream out;
    private InputStream in;

    public ClientMain() throws IOException {
        socket = SocketFactory.getDefault().createSocket();
        socket.setTcpNoDelay(true);
        socket.setKeepAlive(true);
        InetSocketAddress server = new InetSocketAddress("localhost", 8081);
        socket.connect(server, 10000);
        out = socket.getOutputStream();
        in = socket.getInputStream();
    }


    private void send(String message) throws IOException {
        byte[] data = message.getBytes();
        DataOutputStream dos = new DataOutputStream(out);
        // 头4个字节作为本次内容的长度
        dos.writeInt(data.length);
        // 下面为具体的发送内容
        dos.write(data);
        out.flush();
    }

    /**
     * 启动线程向服务器，客户端采用阻塞的socket
     */
    public static void main(String[] args) throws IOException {

        int n = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(n);
        for (int i = 0; i < n; i++) {
            executorService.execute(() -> {
                try {
                    ClientMain client = new ClientMain();
                    log.info(client.socket.toString());
                    client.send("xmm");
                    DataInputStream inputStream = new DataInputStream(client.in);
                    int dataLength = inputStream.readInt();
                    byte[] data = new byte[dataLength];
                    inputStream.readFully(data);
                    client.socket.close();
                    log.info("receive from server: dataLength={}, currentTimeStamp={}", data.length, System.currentTimeMillis());
                } catch (IOException e) {
                    log.error("IOException", e);
                } catch (Exception e) {
                    log.error("Exception", e);
                }
            });
        }
    }

}
