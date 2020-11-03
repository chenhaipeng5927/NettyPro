package com.atguigu.netty.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDateTime;

/**
 * Created with IntelliJ IDEA.
 * TextWebSocketFrame表示一个文本帧（frame）
 */
public class MyTextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
		System.out.println("服务器收到消息" + msg.text());
		//回复消息
		ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器时间" + LocalDateTime.now() + " " + msg.text()));
	}

	//当web客户端连接后  触发方法
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		//id表示唯一的值 ，LongText是唯一的 ShortText有可能重复
		System.out.println("handleradded被调用 " + ctx.channel().id().asLongText());
		System.out.println("handleradded被调用 " + ctx.channel().id().asShortText());

	}


	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		System.out.println("handlerRemoved 被调用 " + ctx.channel().id().asLongText());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println("异常发生" + cause.getMessage());
		ctx.close();
	}
}
