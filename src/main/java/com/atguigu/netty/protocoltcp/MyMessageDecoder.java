package com.atguigu.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author 24321
 * @Auther: chenhp
 * @Date: 2020/11/06/16:08
 * @Description:
 */
public class MyMessageDecoder extends ReplayingDecoder<Void> {
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		System.out.println("MyMessageDecoder decode 方法被调用");
		//需要将得到的二进制字节码转成MessageProtocol数据包（对象）
		int length = in.readInt();
		byte[] content = new byte[length];
		in.readBytes(content);
		//分装成
		MessageProtocol messageProtocol = new MessageProtocol();
		messageProtocol.setLen(length);
		messageProtocol.setContent(content);

		out.add(messageProtocol);
	}
}
