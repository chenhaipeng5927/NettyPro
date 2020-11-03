package com.atguigu.netty.groupchat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: chenhp
 * @Date: 2020/11/03/16:18
 * @Description:
 */
public class GroupChatClient {

	private final String host;

	private final int port;

	public GroupChatClient(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void run() throws InterruptedException {
		NioEventLoopGroup group = new NioEventLoopGroup();

		try {

			Bootstrap bootstrap = new Bootstrap()
					.group(group)
					.channel(NioSocketChannel.class)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ChannelPipeline pipeline = ch.pipeline();

							pipeline.addLast("decoder", new StringDecoder());
							pipeline.addLast("encoder", new StringEncoder());
							//加入自定义处理器
							pipeline.addLast(new GroupChatClientHandler());
						}
					});
			ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
			//得到channel
			Channel channel = channelFuture.channel();
			System.out.println("......" + channel.localAddress() + ".........");
			//客户端要输入信息  创建扫描器
			Scanner scanner = new Scanner(System.in);
			while (scanner.hasNextLine()) {
				String msg = scanner.nextLine();
				//通过channel发送到服务器端
				channel.writeAndFlush(msg + "\r\n");
			}
		} finally {
			group.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		new GroupChatClient("127.0.0.1",7000).run();
	}

}
