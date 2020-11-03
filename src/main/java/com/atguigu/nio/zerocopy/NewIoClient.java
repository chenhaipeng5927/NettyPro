package com.atguigu.nio.zerocopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: chenhp
 * @Date: 2020/10/28/14:14
 * @Description:
 */
public class NewIoClient {
	public static void main(String[] args) throws IOException {

		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.connect(new InetSocketAddress("localhost", 7001));
		String fileName = "protoc-3.6.1-win32.zip";

		//得到一个文件channel
		FileChannel channel = new FileInputStream(fileName).getChannel();

		//准备发送

		long startTime = System.currentTimeMillis();

		int l = (int) (channel.size() / (8 * 1024 * 1024));
		if (channel.size() % (8 * 1024 * 1024) > 0) {
			l = l + 1;
		}
		int count = 0;
		long transferCount = 0L;
		while (count < l) {
			transferCount += channel.transferTo(0, 8 * 1024 * 1024, socketChannel);
			count += 1;
		}

		//在linux下一个transferTo方法就可以完成传输
		//在windows下transferTo只能发送8m 就需要分段传输文件 而且要注意传输时的位置

		System.out.println("发送的总字节数 = " + transferCount + " 耗时：" + (System.currentTimeMillis() - startTime));
		channel.close();


	}
}
