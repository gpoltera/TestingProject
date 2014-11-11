package gui;

import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.text.*;

import crypto.Checksum;
import crypto.SessionKey;

public class SessionKeyInputDocument extends PlainDocument {
	private static final long serialVersionUID = 1L;
	private int maxChars, actualBlock, lastBlock, overallChecksumLength;
	private JTextField firstField, actualField, nextField;
	private JTextField[] textFields;
	private JButton button;
	private String overallChecksum, partialyChecksum;
	private static SessionKey sessionKey;
	private static String[] blocks;

	public SessionKeyInputDocument(int maxChars, JTextField[] textFields,  int actualBlock, JButton button) {
		this.maxChars= maxChars;
		this.textFields = textFields;
		this.actualBlock = actualBlock;
		this.button = button;
		this.firstField = this.textFields[0];
		this.firstField.setEnabled(true);
		this.actualField = this.textFields[this.actualBlock];			
		this.lastBlock = this.textFields.length - 1;
		this.overallChecksumLength = this.maxChars * this.textFields.length - this.textFields.length;
		if (this.actualBlock == this.lastBlock) {
			this.nextField = this.actualField;
		} else {
			this.nextField = this.textFields[actualBlock + 1];
		}
		blocks = new String[this.textFields.length];
	}

	public void insertString(int offset, String string, AttributeSet attributeSet)
			throws BadLocationException {
		int length = getLength() + string.length();
		
		//Check the maximal allowed characters per textfield
		if (length <= maxChars) {
			//Convert the lower case characters to upper case characters
			if (string != null) {
				string = string.toUpperCase();
			}
			//Write the character in the field
			super.insertString(offset, string, attributeSet);					
			//If block is complete
			if (length == maxChars) {
				
				if (actualBlock == lastBlock) {
					partialyChecksum = actualField.getText().substring(0, maxChars - 2);
				} else {
					partialyChecksum = actualField.getText();
				}
				//Check if checksum of the block is ok
				if (Checksum.verifyChecksum(partialyChecksum, 1)) {
					actualField.setBackground(Color.WHITE);
					actualField.setEnabled(false);
					if (actualBlock == lastBlock) {
						blocks[actualBlock] = actualField.getText().substring(0, maxChars - 3) + actualField.getText().substring(maxChars - 2);
					} else {
						blocks[actualBlock] = actualField.getText().substring(0, maxChars - 1);
					}
					nextField.setEnabled(true);
					nextField.requestFocusInWindow();
				} else {
					actualField.setBackground(Color.RED);
				}
				
				overallChecksum = "";
				for (String block : blocks) {
					if (block != null) {
						overallChecksum += block;
					}
				}
				if (overallChecksum.length() == overallChecksumLength) {
					if (Checksum.verifyChecksum(overallChecksum, 2)) {
						sessionKey = new SessionKey(overallChecksum.substring(0, overallChecksum.length() - 2));
						for (JTextField textField : textFields) {
							textField.setBackground(Color.GREEN);
							textField.setEnabled(false);
						}
						button.setEnabled(true);
						
					} else {
						for (JTextField textField : textFields) {
							textField.setBackground(Color.RED);
							textField.setEnabled(true);
						}
					}
				}
			}
		} else {
			Toolkit.getDefaultToolkit().beep();
		}
	}

	public SessionKey getSessionKey() {
		return sessionKey;
	}
}
