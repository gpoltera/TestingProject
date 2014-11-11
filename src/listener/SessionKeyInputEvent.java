package listener;

import java.util.EventObject;

import crypto.SessionKey;

public class SessionKeyInputEvent extends EventObject {
	private static final long serialVersionUID = 1L;
	
	private SessionKey sessionKey;
	
	public SessionKeyInputEvent(Object source, SessionKey sessionKey) {
		super(source);
		this.sessionKey = sessionKey;
	}

	public SessionKey getSessionKey() {
		return sessionKey;
	}
}