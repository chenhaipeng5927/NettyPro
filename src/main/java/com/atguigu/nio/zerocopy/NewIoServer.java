package com.atguigu.nio.zerocopy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: chenhp
 * @Date: 2020/10/28/14:05
 * @Description:
 */
//服务器端
public class NewIoServer {
	public static void main(String[] args) throws IOException {

		InetSocketAddress address = new InetSocketAddress(7001);

		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

		ServerSocket socket = serverSocketChannel.socket();

		socket.bind(address);

		ByteBuffer byteBuffer = ByteBuffer.allocate(4096);

		while (true) {
			SocketChannel socketChannel = serverSocketChannel.accept();

			int readCount = 0;
			while (-1 != readCount) {
				try {
					readCount = socketChannel.read(byteBuffer);
				} catch (IOException e) {
				    break;
				}
				//
				byteBuffer.rewind();//倒带  position=0 mark标志作废
			}
		}
	}

}
