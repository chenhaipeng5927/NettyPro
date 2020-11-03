package com.atguigu.nio;

import java.nio.IntBuffer;
import java.nio.channels.Channel;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: chenhp
 * @Date: 2020/10/22/13:29
 * @Description:
 */
public class BasicBuffer {

	public static void main(String[] args) {

		//举例说明buffer的使用(简单说明)
		//创建一个buffer，大小为5，可以存放5个int
		IntBuffer intBuffer = IntBuffer.allocate(5);

		//想buffer存放数据
		for (int i = 0; i < intBuffer.capacity(); i++) {
			intBuffer.put(i * 2);
		}

		//如何从buffer读取数据
		//将buffer转换，读写切换
		intBuffer.flip();
		intBuffer.position(1);
		while (intBuffer.hasRemaining()) {
			System.out.println(intBuffer.get());
		}
	}

}
