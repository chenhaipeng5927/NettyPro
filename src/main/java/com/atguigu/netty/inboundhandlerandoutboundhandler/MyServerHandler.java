package com.atguigu.netty.inboundhandlerandoutboundhandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created with IntelliJ IDEA.
 *
 * @author 24321
 * @Auther: chenhp
 * @Date: 2020/11/06/10:52
 * @Description:
 */
public class MyServerHandler extends SimpleChannelInboundHandler<Long> {
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
		System.out.println("从客户端 " + ctx.channel().remoteAddress()
				+ " 读取到long " + msg);
		//给客户端发送一个long
		ctx.writeAndFlush(98765L);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
