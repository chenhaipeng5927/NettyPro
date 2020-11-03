package com.atguigu.nio;

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
public class NIOFileChannel4 {
	public static void main(String[] args) throws IOException {
		//创建相关流
		FileInputStream fileInputStream = new FileInputStream("d:\\a.jpg");
		FileOutputStream fileOutputStream = new FileOutputStream("d:\\a2.jpg");
		//获取各个流对应的filechannel
		FileChannel sourceCh = fileInputStream.getChannel();
		FileChannel destCh = fileOutputStream.getChannel();
		//使用transferForm完成
		destCh.transferFrom(sourceCh, 0, sourceCh.size());
		//关闭相关通道和流
		sourceCh.close();
		destCh.close();
		fileInputStream.close();
		fileOutputStream.close();

	}

}
