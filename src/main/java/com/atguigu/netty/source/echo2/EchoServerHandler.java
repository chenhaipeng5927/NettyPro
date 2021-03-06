/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.atguigu.netty.source.echo2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.concurrent.Callable;

/**
 * Handler implementation for the echo server.
 */
@Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * group 充当业务线程池 可将任务提交到该线程池
     */
    static final EventExecutorGroup  group = new DefaultEventExecutorGroup(16);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws InterruptedException {
        System.out.println("EchoServer  Handler 的线程是="+Thread.currentThread().getName());
//        group.submit(new Callable<Object>() {
//            @Override
//            public Object call() throws Exception {
//                //接收客户端信息
//                ByteBuf buf = (ByteBuf) msg;
//                byte[] bytes = new byte[buf.readableBytes()];
//                buf.readBytes(bytes);
//                String s = new String(bytes, CharsetUtil.UTF_8);
//                //休眠十秒
//                Thread.sleep(10 * 1000);
//                System.out.println("greoup.submit的call线程是="+Thread.currentThread().getName());
//                ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端.",CharsetUtil.UTF_8));
//                System.out.println("greoup.submit的call线程是="+Thread.currentThread().getName());
//                return null;
//            }
//        });

        ByteBuf buf = (ByteBuf) msg;
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        String s = new String(bytes, CharsetUtil.UTF_8);
        //休眠十秒
        Thread.sleep(10 * 1000);
        System.out.println("greoup.submit的call线程是="+Thread.currentThread().getName());
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端.",CharsetUtil.UTF_8));
        System.out.println("greoup.submit的call线程是="+Thread.currentThread().getName());
        System.out.println("go on");

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
