package com.logic.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * nio 客户端
 *
 * @author logic
 * @date 2019-08-01 11:07
 * @since 1.0
 */
public class NioClient {
    static volatile boolean stop = false;
    static Selector selector;

    public static void main(String[] args) throws IOException {
        //开启一个Selector
        selector = Selector.open();

        //开启一个channel
        SocketChannel socketChannel = SocketChannel.open();
        //设置非阻塞
        socketChannel.configureBlocking(false);

        //监听端口
        boolean connect = socketChannel.connect(new InetSocketAddress("127.0.0.1", 1234));
        //判断是否连接成功
        if (connect) {
            //如果连接成功的话，注册的监听可读事件
            socketChannel.register(selector, SelectionKey.OP_READ);
            //向channel中写写入数据
            doWrite(socketChannel);
        } else {
            //如果没有连接成功的话，也不代表连接失败，说明服务器可能还没有收到TCP握手应答消息，这个时候注册 SelectionKey.OP_CONNECT
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
        }


        //轮询Selector
        while (!stop) {
            //每隔1000毫秒扫描一次
            selector.select(1000);

            //获取到达某种状态的key，由于channel注册到Selector上，可以说通过key可以获取channel
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            SelectionKey key = null;

            while (iterator.hasNext()) {
                //获取一个可操作的key
                key = iterator.next();
                iterator.remove();

                try {
                    handleInput(key);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
//                    if (key != null) {
//                        key.cancel();
//                        if (key.channel() != null) {
//                            key.channel().close();
//                        }
//                    }
                }
            }
        }
        if (selector != null) {
            selector.close();
        }

        System.out.println("服务结束");
    }

    /**
     * 处理Selector上的各种已经到达的事件
     *
     * @param key Selector上的key
     * @throws IOException
     */
    private static void handleInput(SelectionKey key) throws IOException {
        System.out.println("检测到有效的key");
        //判断key是否有效，有效的意思是 它所在的Selector和channel都没有被关闭
        if (key.isValid()) {
            SocketChannel channel = (SocketChannel) key.channel();

            /**
             * 判断当前channel是否处于连接状态，如果是连接状态，说明服务端已经返回ACK应答消息
             */
            if (key.isConnectable()) {
                System.out.println("处理连接成功的channel");
                //判断是否处于已经连接完成状态
                if (channel.finishConnect()) {
                    //注册监听读事件
                    channel.register(selector, SelectionKey.OP_READ);
                    //想channel中写入数据
                    doWrite(channel);
                } else {
                    //连接失败，直接退出
                    System.exit(1);
                }
            }

            /**
             * 判断当前的事件是不是可读事件，如果是可读事件的话，则读取数据
             */
            if (key.isReadable()) {
                System.out.println("处理读数据的channel");

                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                //将数据读入到buffer中
                int readBytes = channel.read(readBuffer);

                if (readBytes > 0) {
                    readBuffer.flip();
                    byte[] bytes = new byte[readBuffer.remaining()];

                    //将数据从readBuffer转移到bytes中
                    readBuffer.get(bytes);

                    String body = new String(bytes, "UTF-8");
                    System.out.println("服务端返回结果 = " + body);
                    stop = true;
                } else if (readBytes < 0) {
                    //如果服务端没有返回数据的话，则直接将此key在Selector上取消，并关闭channel
                    key.cancel();
                    channel.close();
                } else {
                    System.out.println("什么都没有输出");
                }
            }
        }
    }

    private static void doWrite(SocketChannel socketChannel) throws IOException {
        System.out.println("往服务端请求数据");
        //向channel中写数据
        byte[] req = "query time order".getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
        writeBuffer.put(req);
        writeBuffer.flip();
        //向socketchannel中写数据
        socketChannel.write(writeBuffer);
        if (!writeBuffer.hasRemaining()) {
            System.out.println("已经将数据写完到chanel中了");
        }
    }
}
