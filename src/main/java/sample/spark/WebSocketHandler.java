package sample.spark;

import static spark.Spark.*;
import org.eclipse.jetty.websocket.api.*;
import org.eclipse.jetty.websocket.api.annotations.*;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebSocket
public class WebSocketHandler {

    private static List<Session> users = new ArrayList<Session>();

    @OnWebSocketConnect
    public void onConnect(Session user) throws Exception {
	users.add(user);
	LoggerFactory.getLogger(this.getClass()).info("***** onConnect");
    }

    @OnWebSocketClose
    public void onClose(Session user, int status, String reason) {
	users.remove(user);
	LoggerFactory.getLogger(this.getClass()).info("***** onClose");
    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) {
	try {
	    user.getRemote().sendString(" ECHO " + message);
	} catch (Exception e) {
	    e.printStackTrace();
	    LoggerFactory.getLogger(this.getClass()).info("***** onMessage exception " + e.toString());
	}
    }

    static class Report {
	public String id;
	public boolean actsby;
	public String calls;
	public String cpuusage;
	public Report(String i, boolean as, String c, String cpu) {
	    id = i;
	    actsby = as;
	    calls = c;
	    cpuusage = cpu;
	};
    };

    public static void sendAll(String message) {
	try {
	    Report rep = new Report(message, true, message, "nodata");
	    ObjectMapper mapper = new ObjectMapper();

	    String json = mapper.writeValueAsString(rep);

	    users.forEach(user -> {try {
			user.getRemote().sendString(json);
		    } catch (Exception e) {
			e.printStackTrace();
			LoggerFactory.getLogger("").info("***** sendAll exception " + e.toString());
		    }
		});
	} catch (JsonProcessingException e) {
	    LoggerFactory.getLogger("").info("***** sendAll exception " + e.toString());
	}
    }
}
