package com.logic.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * 服务端
 *
 * @author logic
 * @date 2019-08-01 11:07
 * @since 1.0
 */
public class NioServer {
    public static void main(String[] args) {
        new Thread(new MultiplexerTimeServer(1234), "NioServer").start();
    }

    private static class MultiplexerTimeServer implements Runnable {
        private Selector selector;
        private ServerSocketChannel serverSocketChannel;
        private volatile boolean stop;

        public MultiplexerTimeServer(int port) {
            try {
                //创建Selector
                selector = Selector.open();
                //创建ServerSocketChannel
                serverSocketChannel = ServerSocketChannel.open();
                serverSocketChannel.configureBlocking(false);
                serverSocketChannel.socket().bind(new InetSocketAddress(port), 1024);

                //注册accept事件
                serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
                System.out.println("服务起来了");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {

            while (!stop) {
                try {
                    selector.select(1000);
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();

                    //获取已经就绪的事件
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();

                    SelectionKey key = null;
                    while (iterator.hasNext()) {
                        key = iterator.next();

                        try {
                            handleInput(key);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            if (key != null) {
                                key.cancel();
                                if (key.channel() != null) {
                                    key.channel().close();
                                }
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (selector != null) {
                try {
                    selector.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void handleInput(SelectionKey key) throws IOException {
            System.out.println("服务端检测到有效的Key");
            if (key.isValid()) {

                //如果是连接的事件，则处理连接事件
                if (key.isAcceptable()) {
                    System.out.println("处理接收事件");
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                    SocketChannel acceptChannel = serverSocketChannel.accept();
                    acceptChannel.configureBlocking(false);
                    acceptChannel.register(selector, SelectionKey.OP_READ);
                    // TODO: 2019-08-01 感觉这里应该把 ServerSocketChannel关闭
                }

                //如果是可读事件，则读取数据
                if (key.isReadable()) {
                    System.out.println("处理读数据事件");
                    key.channel();
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    int readBytes = channel.read(byteBuffer);

                    if (readBytes > 0) {
                        byteBuffer.flip();
                        byte[] bytes = new byte[byteBuffer.remaining()];

                        //将缓冲区中的东西写入到buffer中
                        byteBuffer.get(bytes);

                        String body = new String(bytes, "UTF-8");
                        System.out.println("receive from client : " + body);

                        String currentTime = new Date(System.currentTimeMillis()).toString();

                        doWrite(channel, currentTime);
                    } else if (readBytes < 0) {
                        key.cancel();
                        serverSocketChannel.close();
                        stop= true;
                    } else {
                        System.out.println("读取数据是吧");
                        stop = true;
                    }
                }

            }
        }

        private void doWrite(SocketChannel channel, String response) throws IOException {
            System.out.println("往客户端写数据");
            if (response != null && response.trim().length() > 0) {
                byte[] bytes = response.getBytes();

                ByteBuffer byteBuffer = ByteBuffer.allocate(response.length());
                byteBuffer.put(bytes);

                byteBuffer.flip();
                channel.write(byteBuffer);
            }
        }
    }
}
