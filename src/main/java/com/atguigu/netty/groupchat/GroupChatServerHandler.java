package com.atguigu.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: chenhp
 * @Date: 2020/11/03/15:50
 * @Description:
 */
public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {

	//定义一个channel组，管理所有的channel

	//GlobalEventExecutor.INSTANCE是全局的事件执行器，是一个单例
	private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	//handlerAdded 标识连接建立，一旦连接第一个被执行
	//将当前channel加入到channelGroup
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		//将该客户加入聊天的信息推送给其他在线的客户端
		/*
		 * 该方法会将channelGroup中所有的channel遍历并发送消息，不需要自己遍历
		 */
		channelGroup.writeAndFlush("[客户端] " + channel.remoteAddress() + "加入聊天\n");
		channelGroup.add(channel);
	}

	//断开连接,将xx客户离开信息推送给当前在线的客户
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + "离开了\n");
		System.out.println("channelGroup size :" + channelGroup.size());
	}

	//表示channel处于活动的状态 提示xxx上线
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println(ctx.channel().remoteAddress() + "上线了");
	}

	//表示channel处于非活动的状态 提示xxx离线
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println(ctx.channel().remoteAddress() + "离线了");
	}

	//读取数据
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		//获取当前的channel
		Channel channel = ctx.channel();
		//遍历channelGroup 根据不同的情况回复不同的消息
		channelGroup.forEach(ch -> {
			if (channel != ch) {//不是当前的channel 转发消息
				ch.writeAndFlush("[客户]" + channel.remoteAddress() + "发送消息" +
						msg + "\n");
			}else {
			    ch.writeAndFlush("[自己]发送了消息"+msg+"\n");
			}
		});

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		//关闭通道
		ctx.close();
	}
}
