package www.springmvcplus.com.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import www.springmvcplus.com.util.WebsocketUitil;

@ServerEndpoint("/websocket/{username}")
public class MyWebSocket {
	public static Map<String, Session> websocketMap=new HashMap<String, Session>();
	
	@OnOpen
	public void onOpen(Session session,@PathParam("username")String username) {
		websocketMap.put(username, session);
		System.out.println("username:"+username+"加入了websocket监控");
	}
	@OnClose
	public void onClose(@PathParam("username")String username) {
		websocketMap.remove(username);
		System.out.println("username:"+username+"退出了websocket监控");
	}
	@OnMessage
	public void onMessage(String message,@PathParam("username")String username) {
		if (message.contains("__")) {
			String tousername=message.split("__")[0];
			String tomessage=message.split("__")[1];
			WebsocketUitil.sendMessageByUsername(tousername, tomessage);
		}else {
			WebsocketUitil.sendMessageAllUser(message);
		}
	}
	@OnError
	public void onError(Session session, Throwable error) {
		System.out.println("websocket发生了错误");
		error.printStackTrace();
	}
}
