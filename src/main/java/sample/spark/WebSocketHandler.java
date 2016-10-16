package sample.spark;

import static spark.Spark.*;
import org.eclipse.jetty.websocket.api.*;
import org.eclipse.jetty.websocket.api.annotations.*;

@WebSocket
public class WebSocketHandler {

    private String sendre, msg;

    @OnWebSocketConnect
    public void onConnect(Session user) throws Exception {
    }

    @OnWebSocketClose
    public void onClose(Session user, int status, String reason) {
    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) {
	try {
	user.getRemote().sendString(" ECHO " + message);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}


