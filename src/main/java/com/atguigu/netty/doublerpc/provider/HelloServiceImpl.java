package com.atguigu.netty.doublerpc.provider;

import com.atguigu.netty.doublerpc.publicinierface.HelloService;

/**
 * Created by chenhp
 *
 * @author chenhp
 * @Date: 2020/11/13/13:16
 * @Description:
 */
public class HelloServiceImpl implements HelloService {

	private static int count = 0;

	//当有消费方调用该方法时   就返回一个额结果
	@Override
	public String hello(String mes) {
		System.out.println("受到客户端消息=" + mes);
		//根据msg返回不同的结果
		if (mes != null) {
			return "你好客户端，我已经收到了你的消息【" + mes + "】" + ++count;
		} else {
			return "你好客户端，我已经收到你的消息 ";
		}
	}
}
