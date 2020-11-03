package com.atguigu.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: chenhp
 * @Date: 2020/11/02/11:27
 * @Description:
 */
public class NettyServer {
	public static void main(String[] args) throws InterruptedException {
		//创建bossGroup 和  workerGroup
		//说明
		//1.创建两个线程组创建bossGroup 和  workerGroup
		//2.bossGroup只是处理连接请求,真正的和客户端业务处理，会交给workGroup完成
		//3.两个都是无限循环
		NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
		NioEventLoopGroup workerGroup = new NioEventLoopGroup();

		//创建服务器端的启动对象，配置参数
		try {
			ServerBootstrap serverBootstrap = new ServerBootstrap();

			//使用链式编程进行设置
			serverBootstrap.group(bossGroup, workerGroup)//设置两个线程组
					.channel(NioServerSocketChannel.class)//使用NioServerSocketChannel作为服务端的通道实现
					.option(ChannelOption.SO_BACKLOG, 128)//设置线程队列得到的连接个数
					.childOption(ChannelOption.SO_KEEPALIVE, true)//设置保持活动连接状态
					.handler(null)//该handler对应的是bossGroup,childHandler对应workGroup
					.childHandler(new ChannelInitializer<SocketChannel>() {//创建一个通道初始化对象（匿名对象）
						//给pipeline设置处理器
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							System.out.println("客户socketChannel hasCodes :" + ch.hashCode());//可以使用一个集合管理socketChannel,在推送消息时，可以
							//将业务加入到各个channel对应的NioEventLoop的taskQueue或者ScheduleTaskQueue

							ch.pipeline().addLast(new NettyServerHandler());
						}
					}); //给我们的workgroup的eventloop对应的处理器


			System.out.println("......服务器  is  ready .......");

			//绑定一个端口并且同步，生成一个ChannelFuture
			//启动服务器（并绑定端口）
			ChannelFuture cf = serverBootstrap.bind(6668).sync();

			//给cf注册监听器 监控我们关心的事件
			cf.addListener((ChannelFutureListener) future -> {
				if (cf.isSuccess()) {
					System.out.println("监听端口 6668 成功");
				} else {
					System.out.println("监听端口 6666 失败");
				}
			});

			//对关闭通道进行监听
			cf.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
