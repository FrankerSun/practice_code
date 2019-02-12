package javaclass;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Date;

/**
 * 测试FileLock
 *
 * @author f.s.
 * @date 2018/12/11
 */
public class FileLockT {

    public static void main(String[] args) throws Exception {

        new Thread(() -> {
            File file = new File("/Users/forcode/fileLock.txt");
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
                FileChannel fileChannel = randomAccessFile.getChannel();
                final FileLock fileLock = fileChannel.tryLock(0L, Long.MAX_VALUE, false);
                if (null == fileLock) {
                    System.out.println("未获取到FileLock!");
                } else {
                    if (fileLock.isValid()) {
                        System.out.println("成功获取到了FileLock!!");
                        new Thread(
                                () -> {
                                    try {
                                        ByteBuffer sendBuffer = ByteBuffer.wrap((new Date() + " 写入数据\n").getBytes());
                                        fileChannel.write(sendBuffer);
                                        Thread.sleep(10 * 1000);
                                        randomAccessFile.read();
                                        System.out.println("释放锁");
                                        fileLock.release();
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                }
                        ).start();
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        // doNotExit();
    }

    private static void doNotExit() {
        while (true) {
            try {
                Thread.sleep(5 * 1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}


class BlockLockT {

    public static void main(String[] args) {

        new Thread(() -> {
            try {
                File file;
                FileChannel fileChannel;
                RandomAccessFile randomAccessFile;
                String path = "/Users/forcode/fileLock.txt";
                file = new File(path);
                if (!file.exists()) {
                    file.createNewFile();
                }
                Thread.sleep(1000);
                System.out.println("run");
                randomAccessFile = new RandomAccessFile(file, "rw");
                fileChannel = randomAccessFile.getChannel();

                FileLock tempLock = fileChannel.tryLock(0L, Long.MAX_VALUE, false);
                if (null == tempLock) {
                    System.out.println("获取FileLock失败，使用lock()去获取[block]");
                    FileLock fileLock2 = fileChannel.lock();
                    if (null == fileLock2) {

                    } else {
                        if (fileLock2.isValid()) {
                            System.out.println("另一线程[在不同方法中]释放了锁，当前线程获取到了锁");
                        }
                    }
                } else {
                    System.out.println("获取到了锁！");
                }
            } catch (Exception ignore) {
                System.out.println(ignore);
            }
        }).start();
    }
}
