package com.atguigu.netty.inboundhandlerandoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author 24321
 * @Auther: chenhp
 * @Date: 2020/11/06/10:42
 * @Description:
 */
public class MyByteToLongDecoder extends ByteToMessageDecoder {
	private static final int  NUM  = 8;
	/**
	 * @Description:
	 * @Author: chenhp
	 * @Date: 2020/11/6 10:43
	 * @Param: ctx(上下文对象)  in(入站的ByteBuf)  out list集合 将解码后的数据传给下一个handler处理
	 * @Return: void
	 */
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		//因为long是8个字节 需要判断有8个字节  才能读取一个long
		System.out.println("decoder被调用");
		if (in.readableBytes() >= NUM) {
			out.add(in.readLong());

		}

	}
}
