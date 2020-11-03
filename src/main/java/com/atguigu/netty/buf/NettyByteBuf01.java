package com.atguigu.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: chenhp
 * @Date: 2020/11/03/15:02
 * @Description:
 */
public class NettyByteBuf01 {
	public static void main(String[] args) {
		//创建ByteBuf
		//说明
		//1.创建对象，该对象包含一个数组array,byte[10]
		//2.在netty的buffer中，不需要使用flip进行反转
		//因为底层维护了readIndex和writerIndex
		//3.readIndex  capacity writerIndex，将buffer分成三个区域
		// 0----readIndex已经读取的
		//readIndex--writerIndex,可读的区域
		//writerIndex -- capacity 可写的区域

		ByteBuf buffer = Unpooled.buffer(10);
		for (int i = 0; i < 10; i++) {
			buffer.writeByte(i);
		}
		System.out.println("capacity="+buffer.capacity());
		//输出
		for (int i = 0; i < buffer.capacity(); i++){
			System.out.println(buffer.getByte(i));
		}

		for (int i = 0; i < buffer.capacity(); i++){
			System.out.println(buffer.readByte());
		}

	}
}
