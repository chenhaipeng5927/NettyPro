package com.atguigu.netty.codec;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: chenhp
 * @Date: 2020/11/02/13:42
 * @Description:
 */
public class NettyClient {

	public static void main(String[] args) throws InterruptedException {
		//客户端需要一个事件循环组就可以了
		NioEventLoopGroup group = new NioEventLoopGroup();

		try {

			//创建客户端启动对象
			//注意客户端使用的不是serverBootStrap而是BootStrap
			Bootstrap bootstrap = new Bootstrap();
			//设置相关参数
			bootstrap.group(group)//设置线程组
					.channel(NioSocketChannel.class)//设置客户端通道的实现类
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {

							ChannelPipeline pipeline = ch.pipeline();
							//在pipeline中加入protobufferEncoder
							pipeline.addLast("encoder",new ProtobufEncoder());
							pipeline.addLast(new NettyClientHandler());//加入自己的处理器

						}
					});
			System.out.println("客户端 ok ...");

			//启动客户端去连接服务器端
			//关于channelFuture要分析 涉及到netty的异步模式
			ChannelFuture channelFuture = bootstrap.connect("localhost", 6668).sync();

			//给关闭通道进行监听
			channelFuture.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully();
		}

	}
}
