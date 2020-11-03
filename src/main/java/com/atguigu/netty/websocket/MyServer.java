package com.atguigu.netty.websocket;

import com.atguigu.netty.heartbeat.MyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: chenhp
 * @Date: 2020/11/03/18:38
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
							//因为基于http协议  使用http的编码解码器
							pipeline.addLast(new HttpServerCodec());
							//是以块方式写，添加chunkedwriter处理器
							pipeline.addLast(new ChunkedWriteHandler());
							/**
							 * 说明：
							 * 1.因为http数据在传输过程中是分段的，HttpObjectAggregator就是可以将
							 *  多个段聚合起来
							 *  2.这就是为什么当浏览器发送大量数据时就会发送多次http请求
							 */
							pipeline.addLast(new HttpObjectAggregator(8192));
							/**
							 * 说明
							 * 1.对于webocket,他的数据是以帧（frame）的形式传递
							 * 2.可以看到WebSocketFrame下边有6个子类
							 * 3.浏览器请求时 ws://localhost:7000/hello表示请求的uri
							 * 4.WebSocketServerProtocolHandler 核心功能是将http协议升级为ws协议保持长连接
							 */
							pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));
							//自定义handler,处理业务逻辑
							pipeline.addLast(new MyTextWebSocketFrameHandler());

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
