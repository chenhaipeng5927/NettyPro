package com.atguigu.netty.codec2;

import com.atguigu.netty.codec.StudentPOJO;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 说明：
 * 1.我们自定义一个handler  需要继承netty规定好的某个handlerAdapter适配器
 * 2.这时我们自定义的handler,才能成为handler
 */
/*
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

	//读取数据的事件（这里可以读取客户端发送的消息）

	*/

/**
 * 1.ChannelHandlerContext ctx 上下文对象  含有管道 pipeline 通道channel ,地址
 * 2. Object msg 客户端发送的数据，默认是Object
 *
 * @author 24321
 *//*

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		//读取从客户端发送的StudentpOJO.Studnt
		StudentPOJO.Student student = (StudentPOJO.Student) msg;
		System.out.println("客户端发送的数据  id=" + student.getId() + "  名字 name=" + student.getName());
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
**/

public class NettyServerHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {
		//根据dataType显示不同的信息
		MyDataInfo.MyMessage.DataType dataType = msg.getDataType();
		if (dataType == MyDataInfo.MyMessage.DataType.StudentType) {
			MyDataInfo.Student student = msg.getStudent();
			System.out.println(student.getId() + " -----" + student.getName());
		} else if (dataType == MyDataInfo.MyMessage.DataType.WorkerType) {
			MyDataInfo.Worker worker = msg.getWorker();
			System.out.println(worker.getAge() + " -----" + worker.getName());
		} else {
			System.out.println("传输的类型不正确");
		}


	}
}