package com.atguigu.nio.groupChat;



import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: chenhp
 * @Date: 2020/10/27/13:19
 * @Description:
 */
public class GroupChatClient {

	//定义相关的属性
	private final String HOST = "127.0.0.1";  //服务器ip
	private final int PORT = 6667;//服务器端口
	private Selector selector;
	private SocketChannel socketChannel;
	private String username;

	//构造器
	public GroupChatClient() throws IOException {
		//完成初始化工作
		selector = Selector.open();
		//连接服务器
		socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
		//设置非阻塞
		socketChannel.configureBlocking(false);
		//将channel注册到Selector
		socketChannel.register(selector, SelectionKey.OP_READ);
		//得到username
		username = socketChannel.getLocalAddress().toString().substring(1);
		System.out.println(username + " is ok....");
	}


	//向服务器发送消息
	public void sendInfo(String info) {
		info = username + "说:" + info;
		try {
		    socketChannel.write(ByteBuffer.wrap(info.getBytes()));
		}catch (IOException e){
			e.printStackTrace();
		}
	}

	//读取从服务器端回复的消息
	public void readInfo(){
		try {
			int readChannels = selector.select();
			if (readChannels > 0) {//有可用的通道 有事件发生的通道
				Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
				while (iterator.hasNext()) {
					SelectionKey key = iterator.next();
					if (key.isReadable()){
						//得到相关的通道
						SocketChannel sc = (SocketChannel) key.channel();
						//得到一个buffer
						ByteBuffer buffer = ByteBuffer.allocate(1024);
						//读取
						sc.read(buffer);
						//把读到的缓冲器的数据转成字符串
						String msg = new String(buffer.array());
						System.out.println(msg.trim());
						iterator.remove();//删除当前的selectionKey,防止重复操作
					}
				}

			}else {
				//System.out.println("没有可以用的通道.....");//(由于这里的select方法阻塞，所以也不会走到这里)
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args) throws IOException {
		//启动我们的客户端
		GroupChatClient chatClient = new GroupChatClient();

		//启动一个线程,每隔3秒，读取从服务器端发送数据
		new Thread(){
			public void run() {
			    while (true) {
			        chatClient.readInfo();
			        try {
			            Thread.currentThread().sleep(3000);
			        }catch (InterruptedException e){
			        	e.printStackTrace();
					}
			    }
			}
		}.start();
		//发送数据给服务器端

		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNext()) {
			String s = scanner.nextLine();
			chatClient.sendInfo(s);
		}
	}

}
