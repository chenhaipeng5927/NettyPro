package com.atguigu.netty.inboundhandlerandoutboundhandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * Created with IntelliJ IDEA.
 *
 * @author 24321
 * @Auther: chenhp
 * @Date: 2020/11/06/11:03
 * @Description:
 */
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();



		//再加入一个出栈的handler对数据进行编码
		pipeline.addLast(new MyLongToByteEncoder());
		pipeline.addLast(new MyByteToLongDecoder2());
		pipeline.addLast(new MyClientHandler());

	}
}
