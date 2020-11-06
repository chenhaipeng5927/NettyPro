package com.atguigu.netty.tcp;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
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
public class MyClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

	private int count;

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		//客户端发送十条数据 hello server+编号
		for (int i = 0; i < 10; i++) {
			ByteBuf buf = Unpooled.copiedBuffer("hello,server " + i, CharsetUtil.UTF_8);
			ctx.writeAndFlush(buf);
		}
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
		byte[] buf = new byte[msg.readableBytes()];
		msg.readBytes(buf);
		String s = new String(buf, CharsetUtil.UTF_8);
		System.out.println("客户端接收到消息=" + s);
		System.out.println("客户端接收到消息数量=" + (++this.count));
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
