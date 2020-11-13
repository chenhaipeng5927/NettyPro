package com.atguigu.netty.doublerpc.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import sun.dc.pr.PRError;

import java.util.concurrent.Callable;

/**
 * @author chenhp
 * @Date: 2020/11/13/14:33
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {

	/**
	 * 上下文
	 */
	private ChannelHandlerContext context;

	/**
	 * 返回的结果
	 */
	private String result;

	/**
	 * 客户端调用时传入的参数
	 */
	private String parameter;


	/**
	 * 被代理对象调用 发送数据给服务器 -> wait   等待被唤醒
	 * author: chenhp
	 *3
	 * @Date: 2020/11/13 14:50
	 */
	@Override
	public synchronized Object call() throws Exception {
		context.writeAndFlush(parameter);
		//进行wait
		wait();//等到channelRead获取到服务器的结果后唤醒
		return result;
	}


	/**
	 * 与服务器连接创建成功 就会被调用
	 * author: chenhp
	 *1
	 * @Date: 2020/11/13 14:36
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		//因为在其他方法会使用ctx
		context = ctx;
	}


	/**
	 *  4
	 *  author: chenhp
	 *  @Date: 2020/11/13 15:25
	 */
	@Override
	public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		result = msg.toString();
		//唤醒等待的线程
		notify();
	}


	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}


	/**
	 * 2
	 *  author: chenhp
	 *  @Date: 2020/11/13 15:25
	 */
	void setParameter(String parameter){
		this.parameter = parameter;
	}
}
