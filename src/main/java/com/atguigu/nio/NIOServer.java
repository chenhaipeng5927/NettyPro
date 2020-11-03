package com.atguigu.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: chenhp
 * @Date: 2020/10/26/16:38
 * @Description:
 */
public class NIOServer {
	public static void main(String[] args) throws IOException {
		//创建serverSocketChannel ->serverSocket
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

		//得到一个selector对象
		try (Selector selector = Selector.open()) {
			//绑定一个端口6666，在服务器端监听
			serverSocketChannel.socket().bind(new InetSocketAddress(6666));
			//设置为非阻塞
			serverSocketChannel.configureBlocking(false);

			//把serverSocketChannel注册到 selector 关心的事件为op_accept
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

			System.out.println("注册后的key 数量=" + selector.keys().size());//1.

			//循环等到客户端连接
			while (true) {
				//这里等待1秒，如果没有事件发生，返回
				if (selector.select(1000) == 0) {//没有事件发生
					System.out.println("服务器等待了1秒，无连接");
					continue;
				}
				//如果返回的>0,就获取到相关的selectionKey集合
				//1.如果返回>0  标识已经获取到关注的事件
				//2.selector.selectedKeys(); 返回关注事件的集合
				//通过selectionKeys反向获取通道
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				System.out.println("selectionKeys 数量 = " + selectionKeys.size()+"   keys数量"+selector.keys().size());
				//遍历Set<SelectionKey>,使用迭代器
				Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
				while (keyIterator.hasNext()) {
					//获取到selectionKey
					SelectionKey key = keyIterator.next();
					//根据key对应的通道发生的事件做相应的处理
					if (key.isAcceptable()) {//如果是 OP_ACCEPT，有新的客户端连接
						//给该客户端生成一个socketChannel
						SocketChannel socketChannel = serverSocketChannel.accept();
						//将当前的socketChannel注册到selector,关注事件为OP_READ，socketChannel关联一个buffer
						System.out.println("客户端连接成功，生成了一个socketChannel" + socketChannel.hashCode());
						//将socketChannel设置为非阻塞
						socketChannel.configureBlocking(false);
						socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
						System.out.println("客户端连接后，注册后的key 数量=" + selector.keys().size());//2,3,4
					} else if (key.isReadable()) {//发生OP_READ
						//通过key反向获取到对应的channel
						SocketChannel channel = (SocketChannel) key.channel();
						//获取到该channel关联的buffer
						ByteBuffer buffer = (ByteBuffer) key.attachment();
						int read = channel.read(buffer);
						if (read != -1) {
							System.out.println("from客户端 " + new String(buffer.array()));
						} else {
							channel.close();
						}

					}
					//手动从集合中移动当前的selecionKey，防止重复操作
					keyIterator.remove();


				}

			}


		}


	}
}
