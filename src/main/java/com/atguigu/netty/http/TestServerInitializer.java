package com.atguigu.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: chenhp
 * @Date: 2020/11/02/18:20
 * @Description:
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {


	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		//向管道加入处理器

		//得到管道
		ChannelPipeline pipeline = ch.pipeline();

		//加入一个netty提供的httpServerCodec codec=>[coder - decoder]
		//HttpServerCodec 说明
		//1.HttpServerCodec 是netty ，提供的处理http的编码解码
		pipeline.addLast("MyHttpServerCodec",new HttpServerCodec());
		//2.增加一个自定义的handler
		pipeline.addLast("MyTestHttpServerHandler",new TestHttpServerHandler());
		System.out.println("---");

	}
}
