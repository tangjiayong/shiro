package test.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOClient 
{
	private Selector selector; 
	private SocketChannel channel;
	
	public void initClient() throws Exception 
	{ 
	    channel = SocketChannel.open();  
	    channel.configureBlocking(false);  
	    selector = Selector.open();  
	     
	    boolean connected = channel.connect(new InetSocketAddress("127.0.0.1",8090)); 
	    if( connected==false )
	    	channel.register(selector, SelectionKey.OP_CONNECT);
	    else
	    	initChannel(channel);
	}
	
	public void listen() throws Exception 
	{
	    while (true) 
	    {
	       if(selector.select()==0)
				continue;
		   Set<SelectionKey> keys = selector.selectedKeys();
		   for(Iterator<SelectionKey> ite = keys.iterator(); ite.hasNext();)
		   {
				SelectionKey key = ite.next();
	            if (key.isConnectable()) 
	            {
	               SocketChannel channel = (SocketChannel) key.channel();  
	               if( channel.isConnectionPending() )
	               {
	                  channel.finishConnect();        
	               }
	               initChannel(channel);
	               
	            } else if (key.isReadable()) 
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
				   System.out.println("Server : "+strbf.toString());
	            }
	            ite.remove();//已处理的事件需移除
	        }
	    }
	}
	
	public void initChannel(SocketChannel cnl) throws Exception
	{
		cnl.write( ByteBuffer.wrap("Client coming! 你好，服务端！".getBytes("UTF-16")) );  
		cnl.register(selector, SelectionKey.OP_READ);
	}
	
	public static void main(String[] args) throws Exception
	{
		NIOClient client = new NIOClient();
		client.initClient();
		client.listen();
	}
}
