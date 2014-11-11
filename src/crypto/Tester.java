package crypto;

import java.beans.PropertyChangeSupport;

public class Tester {
	private String name = "";
	
	private PropertyChangeSupport changes = new PropertyChangeSupport(this);
	
	public void setName(String name) {
		String oldName = this.name;
		this.name = name;
		changes.firePropertyChange("name", oldName, name);
	}
}