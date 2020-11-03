package com.atguigu.nio;

import java.nio.ByteBuffer;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: chenhp
 * @Date: 2020/10/23/17:19
 * @Description:
 */
public class ReadOnlyBuffer {

	public static void main(String[] args) {

		ByteBuffer buffer = ByteBuffer.allocate(64);

		for (int i = 0; i <64;i++){
			buffer.put((byte)i);
		}

		System.out.println(buffer.getClass());
		//读取
		buffer.flip();
		//得到一个只读的buffer

		ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();


		System.out.println(readOnlyBuffer.getClass());
		while (readOnlyBuffer.hasRemaining()) {
			System.out.println(readOnlyBuffer.get());
		}

		readOnlyBuffer.put((byte)100);//readonlybuffereception
	}
}
