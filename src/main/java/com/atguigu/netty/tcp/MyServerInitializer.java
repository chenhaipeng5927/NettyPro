package com.atguigu.netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;


/**
 * Created with IntelliJ IDEA.
 *
 * @author 24321
 * @Auther: chenhp
 * @Date: 2020/11/06/15:23
 * @Description:
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast(new MyServerHandler());
	}
}
