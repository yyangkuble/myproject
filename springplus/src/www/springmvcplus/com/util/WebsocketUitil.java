package www.springmvcplus.com.util;

import java.io.IOException;

import javax.websocket.Session;

import www.springmvcplus.com.services.MyWebSocket;


public class WebsocketUitil {
	public static void sendMessageByUsername(String username,String message) {
		Session session=MyWebSocket.websocketMap.get(username);
		if (session != null) {
			try {
				session.getBasicRemote().sendText(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void sendMessageAllUser(String message) {
		for (String username : MyWebSocket.websocketMap.keySet()) {
			Session session=MyWebSocket.websocketMap.get(username);
			if (session != null) {
				try {
					session.getBasicRemote().sendText(message);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	
}
