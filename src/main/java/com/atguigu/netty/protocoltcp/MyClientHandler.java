package com.atguigu.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * Created with IntelliJ IDEA.
 *
 * @author 24321
 * @Auther: chenhp
 * @Date: 2020/11/06/15:19
 * @Description:
 */
public class MyClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {

	private int count;

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		//客户端发送十条数据 "今天嗓子疼，吃药"+编号
		for (int i = 0; i < 5; i++) {
			String mes = "今天嗓子疼，吃药";
			byte[] content = mes.getBytes(CharsetUtil.UTF_8);
			int length = mes.getBytes(CharsetUtil.UTF_8).length;

			//创建协议包对象
			MessageProtocol messageProtocol = new MessageProtocol();
			messageProtocol.setLen(length);
			messageProtocol.setContent(content);
			ctx.writeAndFlush(messageProtocol);
		}
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}
}
