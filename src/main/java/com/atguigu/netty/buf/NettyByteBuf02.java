package com.atguigu.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: chenhp
 * @Date: 2020/11/03/15:20
 * @Description:
 */
public class NettyByteBuf02 {
	public static void main(String[] args) {
		//创建buffer
		ByteBuf byteBuffer = Unpooled.copiedBuffer("hello,world!", Charset.forName("utf-8"));

		//使用相关的方法
		if (byteBuffer.hasArray()){//true
			byte[] content = byteBuffer.array();

			//将content转成字符串
			System.out.println(new String(content, StandardCharsets.UTF_8));

			System.out.println("byteBuffer = "+byteBuffer);
			System.out.println(byteBuffer.arrayOffset());//0
			System.out.println(byteBuffer.readerIndex());//0
			System.out.println(byteBuffer.writerIndex());//12
			System.out.println(byteBuffer.capacity());//36
			//System.out.println(byteBuffer.readByte());//输出的是asc码
			int len = byteBuffer.readableBytes();//可读的字节数 12
			System.out.println("len :"+len);

			//使用for循环取出各个字符
			for (int i = 0; i < len; i++) {
				System.out.print((char) byteBuffer.getByte(i));//将asc码转成字符
			}
			System.out.println();
			System.out.println(byteBuffer.getCharSequence(0,4,Charset.forName("utf-8")));
			System.out.println(byteBuffer.getCharSequence(4,6,Charset.forName("utf-8")));

		}


	}
}
