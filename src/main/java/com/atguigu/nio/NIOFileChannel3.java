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
public class NIOFileChannel3 {
	public static void main(String[] args) throws IOException {
		FileInputStream fileInputStream = new FileInputStream("1.txt");
		FileChannel channel01 = fileInputStream.getChannel();
		FileOutputStream fileOutputStream = new FileOutputStream("2.txt");
		FileChannel channel02 = fileOutputStream.getChannel();

		ByteBuffer byteBuffer = ByteBuffer.allocate(512);

		while (true) {//循环读取
			byteBuffer.clear();
			int read = channel01.read(byteBuffer);
			System.out.println(read);
			if (read == -1) {//表示读完
				break;
			}
			//将buffer中的数据写入到filechannel02 --2.txt
			byteBuffer.flip();
			channel02.write(byteBuffer);
		}

		fileInputStream.close();
		fileOutputStream.close();

	}

}
