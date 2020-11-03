package com.atguigu.netty.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: chenhp
 * @Date: 2020/11/03/18:18
 * @Description:
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter {

	/**
	 * @Description:
	 * @Author: chenhp
	 * @Date: 2020/11/3 18:19
	 * @Param:
	 * @Return: void
	 * ctx上下文
	 * evt 事件
	 */
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof IdleStateEvent) {
			//将evt向下转型
			IdleStateEvent event = (IdleStateEvent) evt;
			String eventType = null;
			switch (event.state()) {
				case READER_IDLE:
					eventType = "读空闲";
					break;
				case WRITER_IDLE:
					eventType = "写空闲";
					break;
				case ALL_IDLE:
					eventType = "读写空闲";
					break;

			}
			System.out.println(ctx.channel().remoteAddress() + "--超时事件--" +
					eventType);
			System.out.println("服务器做相应处理");
			//如果发生空闲 我们关闭通道

		}


	}
}
