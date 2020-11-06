package com.atguigu.netty.protocoltcp;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 *
 * @author 24321
 * @Auther: chenhp
 * @Date: 2020/11/06/15:28
 * @Description:
 */
public class MyServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {
	private int count;

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
		//接收到数据 并处理
		int len = msg.getLen();
		byte[] content = msg.getContent();
		System.out.println("服务端接收到信息如下");
		System.out.println("长度=" + len);
		System.out.println("内容=" + new String(content, CharsetUtil.UTF_8));

		System.out.println("服务器接收到消息包数量=" + (++this.count));

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
