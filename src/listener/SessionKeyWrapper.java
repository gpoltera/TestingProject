package listener;

import javax.swing.event.EventListenerList;

public class SessionKeyWrapper {
	private EventListenerList listeners = new EventListenerList();
	
	public void addSessionKeyListener(SessionKeyListener listener) {
		listeners.add(SessionKeyListener.class, listener);
	}
	
	public void removeSessionKeyListener(SessionKeyListener listener) {
		listeners.remove(SessionKeyListener.class, listener);
	}
	
	protected synchronized void notifySessionKeyChange(SessionKeyInputEvent event) {
		for (SessionKeyListener l : listeners.getListeners(SessionKeyListener.class)) {
			l.sessionKeyChange(event);
		}
	}
}