package com.atguigu.nio;

import java.io.File;
import java.io.FileInputStream;
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
public class NIOFileChannel2 {
	public static void main(String[] args) throws IOException {
		//创建文件的输入流
		File file = new File("d:\\file01.txt");
		FileInputStream fileInputStream = new FileInputStream(file);

		//通过fileInputStream获取对应的filechannel -->实际类型 filechannelImpl
		FileChannel channel = fileInputStream.getChannel();

		//创建缓冲区
		ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());

		//将通道的数据读入到buffer中
		channel.read(byteBuffer);
		byteBuffer.flip();
		//将bytebuffer的字节数据转成字符串
		System.out.println(new String(byteBuffer.array()));

		fileInputStream.close();




	}

}
