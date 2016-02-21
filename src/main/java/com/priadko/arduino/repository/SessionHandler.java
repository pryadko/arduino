package com.priadko.arduino.repository;

import com.priadko.arduino.entry.Mock;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import javax.websocket.Session;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Repository
@Scope("singleton")
public class SessionHandler {

	private static Mock mockValue;
	static {
		mockValue = new Mock();
		mockValue.setValue(false);
	}

	private final Set<Session> sessions = new HashSet<>();



	public void addSession(Session session) {
		sessions.add(session);
	}

	public void removeSession(Session session) {
		sessions.remove(session);
	}



	public void toggleMock() {
		mockValue.setValue(!mockValue.getValue());
		JsonProvider provider = JsonProvider.provider();
		JsonObject updateMockMessage = provider.createObjectBuilder()
				.add("value", mockValue.getValue())
				.build();
		sendToAllConnectedSessions(updateMockMessage);
	}

	private void sendToAllConnectedSessions(JsonObject message) {
		for (Session session : sessions) {
			sendToSession(session, message);
		}
	}

	private void sendToSession(Session session, JsonObject message) {
		try {
			session.getBasicRemote().sendText(message.toString());
		} catch (IOException ex) {
			sessions.remove(session);
		}
	}
}
