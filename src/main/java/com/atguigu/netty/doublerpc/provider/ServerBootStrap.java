package com.atguigu.netty.doublerpc.provider;

import com.atguigu.netty.doublerpc.netty.NettyServer;

/**
 * @author chenhp
 * @Date: 2020/11/13/13:49 会启动一个服务的提供者  就是nettyServer
 */
public class ServerBootStrap {
	public static void main(String[] args) {
		//代码待填
		NettyServer.startServer("127.0.0.1",7000);
	}
}
