package com.atguigu.nio;

import java.nio.ByteBuffer;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: chenhp
 * @Date: 2020/10/23/17:13
 * @Description:
 */
public class NIOByteBufferPutGet {
	public static void main(String[] args) {

		ByteBuffer buffer = ByteBuffer.allocate(64);

		buffer.putInt(100);
		buffer.putLong(9);
		buffer.putChar('陈');
		buffer.putShort((short)4);


		//取出

		buffer.flip();


		System.out.println(buffer.getShort());
		System.out.println(buffer.getLong());
		System.out.println(buffer.getChar());
		System.out.println(buffer.getLong());


	}
}
