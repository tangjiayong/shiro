package test.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOServer 
{
	
	private Selector selector;
	private ServerSocketChannel srvcnl;
	
	public void initServer() throws Exception
	{
		srvcnl = ServerSocketChannel.open();
		srvcnl.configureBlocking(false);
		srvcnl.socket().bind( new InetSocketAddress("127.0.0.1", 8090) );
		
		selector = Selector.open();
		srvcnl.register(selector, SelectionKey.OP_ACCEPT);
	}
	
	public void initWorker() throws Exception
	{
		while(true)
		{
			if(selector.select()==0)
				continue;
			Set<SelectionKey> keys = selector.selectedKeys();
			for(Iterator<SelectionKey> ite = keys.iterator(); ite.hasNext();)
			{
				SelectionKey key = ite.next();
				if(key.isAcceptable())
				{
					ServerSocketChannel srvcnl = (ServerSocketChannel) key.channel();
					SocketChannel sktcnl = srvcnl.accept();
					sktcnl.configureBlocking(false);
					sktcnl.write( ByteBuffer.wrap("Hello Client, 欢迎来到NIO服务端！".getBytes("UTF-16")) );
					sktcnl.register(selector, SelectionKey.OP_READ);
					
				}else if(key.isReadable())
				{
					SocketChannel cnl = (SocketChannel) key.channel();
					ByteBuffer btbf = ByteBuffer.allocate(20);
					StringBuilder strbf = new StringBuilder();
					
					int num = cnl.read(btbf);
					while( num!=0 && num!=-1 )
					{
						strbf.append(new String(btbf.array(), 0, num, "UTF-16"));
						btbf.clear();
						num = cnl.read(btbf);
					}
					System.out.println("Client : "+strbf.toString());
					cnl.write( ByteBuffer.wrap(("消息["+strbf.toString()+"]服务端已收到！").getBytes("UTF-16")) );
				}
				ite.remove();//已处理的事件需移除
			}
		}
	}
	
	public static void main(String[] args) throws Exception
	{
		NIOServer server = new NIOServer();  
        server.initServer();  
        server.initWorker();
	}
	
}
