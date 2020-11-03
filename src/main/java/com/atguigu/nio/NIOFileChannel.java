package com.atguigu.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: chenhp
 * @Date: 2020/10/22/15:49
 * @Description:
 */
public class NIOFileChannel {
	public static void main(String[] args) throws IOException {
		String str = "哈哈哈哈哈";

		//创建一个输出流  -》channel
		FileOutputStream fileOutputStream = new FileOutputStream("d:\\file01.txt");

		//通过这个输出流获取对应的FileChannel
		//这个filechannel的真是类型是FileChannelImpl
		FileChannel fileChannel = fileOutputStream.getChannel();

		//创建一个缓冲区
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

		//将str放入到bytebuffer
		byteBuffer.put(str.getBytes());

		//对bytebuffer进行flip
		byteBuffer.flip();

		//将bytebuffer数据写入到filechannel
		fileChannel.write(byteBuffer);

		fileOutputStream.close();





	}

}
