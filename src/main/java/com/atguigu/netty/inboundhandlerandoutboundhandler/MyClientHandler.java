package com.atguigu.netty.inboundhandlerandoutboundhandler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * Created with IntelliJ IDEA.
 *
 * @author 24321
 * @Auther: chenhp
 * @Date: 2020/11/06/11:13
 * @Description:
 */
public class MyClientHandler extends SimpleChannelInboundHandler<Long> {
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
		System.out.println("服务器的ip" + ctx.channel().remoteAddress());
		System.out.println("收到服务器消息=" + msg);
	}
	//重写channelActive发送数据

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("MyClientHandler 发送数据 ");
		//ctx.writeAndFlush(Unpooled.copiedBuffer(""))
		//发送123456long
		ctx.writeAndFlush(123456L);


		//分析
		//1."abcdabcdabcdabcd"是16个字节
		//2. 该处理器的前一个handler是MyLongToByteEncoder
		//3.MyLongToByteEncoder 父类是 MessageToByteEncoder
		//4.父类有messaheToByteEncoder
		/*if (acceptOutboundMessage(msg)) { //判断当前的msg是不是应该处理的类型  如果是就处理，不是就跳过
			@SuppressWarnings("unchecked")
			I cast = (I) msg;
			buf = allocateBuffer(ctx, cast, preferDirect);
			try {
				encode(ctx, cast, buf);
			} finally {
				ReferenceCountUtil.release(cast);
			}

			if (buf.isReadable()) {
				ctx.write(buf, promise);
			} else {
				buf.release();
				ctx.write(Unpooled.EMPTY_BUFFER, promise);
			}
			buf = null;
		} else {
			ctx.write(msg, promise);
		}*/
		//4.因此我们在编写encoder时要注意传入的数据类型和处理的数据类型一致
		//ctx.writeAndFlush(Unpooled.copiedBuffer("abcdabcdabcdabcd", CharsetUtil.UTF_8));
	}
}
