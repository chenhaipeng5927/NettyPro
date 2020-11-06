package com.atguigu.netty.inboundhandlerandoutboundhandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * Created with IntelliJ IDEA.
 *
 * @author 24321
 * @Auther: chenhp
 * @Date: 2020/11/06/10:38
 * @Description:
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();

		//入栈的handler 进行解码
		//pipeline.addLast(new MyByteToLongDecoder());
		pipeline.addLast(new MyByteToLongDecoder2());
		pipeline.addLast(new MyLongToByteEncoder());
		pipeline.addLast(new MyServerHandler());


	}
}
