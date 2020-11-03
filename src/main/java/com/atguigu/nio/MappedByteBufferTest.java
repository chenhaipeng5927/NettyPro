package com.atguigu.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: chenhp
 * @Date: 2020/10/23/17:25
 * @Description: 说明：1.MappedByteBuffer可让文件直接在内存（堆外内存）修改，操作系统不需要再拷贝一次
 */
public class MappedByteBufferTest {
	public static void main(String[] args) throws IOException {

		RandomAccessFile randomAccessFile = new RandomAccessFile("1.txt", "rw");
		//获取对应的通道
		FileChannel channel = randomAccessFile.getChannel();

		/**
		 * 参数1：FileChannel.MapMode.READ_WRITE 使用读写模式
		 * 参数2： 0：可以直接修改的起始位置
		 *  参数3： 5 ：是映射到内存的大小,即将1.txt的多少个字节映射到内存
		 *    可以直接修改的范围就是0-5（是映射内存的大小不是索引位置）
		 */
		MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

		mappedByteBuffer.put(0, (byte) 'H');
		mappedByteBuffer.put(3, (byte) '9');
		//mappedByteBuffer.put(5, (byte) '9');

		channel.close();
		randomAccessFile.close();
		System.out.println("修改成功");

	}

}
