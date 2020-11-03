package com.atguigu.netty.heartbeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: chenhp
 * @Date: 2020/11/03/17:05
 * @Description:
 */
public class MyServer {
	public static void main(String[] args) throws InterruptedException {
		NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
		NioEventLoopGroup workerGroup = new NioEventLoopGroup(16);

		try {
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(bossGroup,workerGroup)
					.channel(NioServerSocketChannel.class)
					.handler(new LoggingHandler(LogLevel.INFO))//在 bossGroup增加日志处理器
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ChannelPipeline pipeline = ch.pipeline();
							//加入一个netty提供的IdleStateHandler
							//说明
							//1.IdleStateHandler 是netty提供的处理空闲状态的处理器
							//2.long readerIdleTime,//表示多长时间没有读就会发送心跳检测包
							// long writerIdleTime, //表示多长时间没有写就会发送心跳检测包
							// long allIdleTime,//表示多长时间没有写和写就会发送心跳检测包
							//当IdleStateHandler触发后就会传递到管道的下一个handler，通过调用下一个handler的userEventTrigger(读，写，读写)
							pipeline.addLast(new IdleStateHandler(3,5,7, TimeUnit.SECONDS));
							//加入一个对空闲检测进一步处理的handler
							pipeline.addLast(new MyServerHandler());
						}
					});

			ChannelFuture channelFuture = serverBootstrap.bind(7000).sync();
			channelFuture.channel().closeFuture().sync();

		}finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}


	}

}
