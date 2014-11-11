package gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import crypto.SessionKey;

public class SessionKeyInputPanel extends JPanel {
	private static final long serialVersionUID = 3241489157408381878L;
	private JTextField[] textFields = new JTextField[7];
	private JButton button;
	private SessionKeyInputDocument document;
	private SessionKey sessionKey;
	
	public SessionKeyInputPanel() {
		
		setLayout(new FlowLayout());
		
		for (int i = 0; i < textFields.length; i++) {
			textFields[i] = new JTextField(5);
			textFields[i].setEnabled(false);
		}
		
		button = new JButton();
		button.setText("Decrypt");
		button.setEnabled(false);
		button.addActionListener(new ActionListener() {
			 @Override
	            public void actionPerformed(ActionEvent e) {
				 	sessionKey = document.getSessionKey();
				 	System.out.println(sessionKey.getBase32());
	            }
		});
		
		for (int i = 0; i < textFields.length; i++) {
			document = new SessionKeyInputDocument(5, textFields, i, button);
			textFields[i].setDocument(document);
			add(textFields[i]);
		}
		add(button);
	}
	
	public void updateSessionKey(SessionKey sessionKey) {
		String[] blocks = sessionKey.getSeparatedWithChecksum();
		
		if (blocks.length == textFields.length) {
			for (int i = 0; i < textFields.length; i++) {
				textFields[i].setText(blocks[i]);
			}	
		}
	}
}