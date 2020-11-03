package com.atguigu.bio;


import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: chenhp
 * @Date: 2020/10/22/10:54
 * @Description:
 */
public class BIOServer {
	public static void main(String[] args) throws IOException {
		//线程池机制

		//思路
		//创建一个线程池
		//如果有客户端连接，就创建一个线程与之通讯（单独写一个方法）


		ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
		//创建serversocket
		ServerSocket serverSocket = new ServerSocket(6666);

		System.out.println("服务器启动");

		while (true) {
			//监听，等待客户端连接
			System.out.println(Thread.currentThread().getId());
			final Socket socket = serverSocket.accept(); //阻塞等待连接
			System.out.println("连接一个客户端");
			System.out.println(Thread.currentThread().getId());
//			handler(socket);
			//创建一个线程与之通讯 ,每次都创建一个线程
			newCachedThreadPool.execute(new Runnable() {
				public void run() {//重写
					//可以和客户端通讯
					handler(socket);
				}
			});
		}
	}

	//编写一个handler方法与客户端通讯
	public static void handler(Socket socket) {
		try {
			byte[] bytes = new byte[1024];//1kb
			//通过socket获取输入流
			InputStream inputStream = socket.getInputStream();
			//循环读取客户端发送的数据
			while (true) {
				int read = inputStream.read(bytes); //会阻塞等待数据
				if (read != -1) {
					System.out.println(new java.lang.String(bytes, 0, read));
					//输出客户端发送额数据
				} else {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("关闭和client的连接");
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
