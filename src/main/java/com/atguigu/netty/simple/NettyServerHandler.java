package com.atguigu.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * 说明：
 * 1.我们自定义一个handler  需要继承netty规定好的某个handlerAdapter适配器
 * 2.这时我们自定义的handler,才能成为handler
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {


	//读取数据的事件（这里可以读取客户端发送的消息）

	/**
	 * 1.ChannelHandlerContext ctx 上下文对象  含有管道 pipeline 通道channel ,地址
	 * 2. Object msg 客户端发送的数据，默认是Object
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

		//比如这里有一个非常耗时长的时间业务 ->异步执行->提交该channel 对应的NioEventLoop的taskQueue中

		/*//解决方案1.用户程序自定义的普通任务
		ctx.channel().eventLoop().execute(() -> {
			try {
				Thread.sleep(10*1000);
				ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端 (>^ω^<)喵2~", CharsetUtil.UTF_8));

			} catch (InterruptedException e) {
				System.out.println("发生异常"+e.getMessage());				}

		});
		ctx.channel().eventLoop().execute(() -> {
			try {
				Thread.sleep(20*1000);
				ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端 (>^ω^<)喵3~", CharsetUtil.UTF_8));
			} catch (InterruptedException e) {
				System.out.println("发生异常"+e.getMessage());				}

		});*/

	/*	//用户自定义定时任务 -> 该任务是提交到scheduleTaskQueue
		ctx.channel().eventLoop().schedule(() -> {
			try {
				Thread.sleep(10*1000);
				ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端 (>^ω^<)喵4~", CharsetUtil.UTF_8));

			} catch (InterruptedException e) {
				System.out.println("发生异常"+e.getMessage());				}

		},10, TimeUnit.SECONDS);

		System.out.println(" go on .....");*/


		System.out.println("服务器读取线程 " + Thread.currentThread().getName()
		+"channel = " + ctx.channel());
		System.out.println("server ctx = " + ctx);
		System.out.println("看看channel和pipeline的关系");
		Channel channel = ctx.channel();
		ChannelPipeline pipeline = ctx.pipeline();//本质是一个双向链表 出栈入栈问题

		//将msg转成一个ByteBuffer
		//ByteBuf 是netty提供的，不是NIO的ByteBuffer
		ByteBuf buf = (ByteBuf) msg;
		System.out.println("客户端方发送消息:" + buf.toString(CharsetUtil.UTF_8));
		System.out.println("客户端地址：" + channel.remoteAddress());

	}


	//数据读取完毕
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		//writeAndFlush 是write+flush
		//将数据写入到缓存  并刷新
		//一般讲，对发送的数据进行编码
		ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端 (>^ω^<)喵1~", CharsetUtil.UTF_8));
	}


	//处理异常，一般需要关闭通道
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}
}