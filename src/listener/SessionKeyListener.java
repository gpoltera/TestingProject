package listener;

import java.util.EventListener;

public interface SessionKeyListener extends EventListener {
	void sessionKeyChange(SessionKeyInputEvent e);
}