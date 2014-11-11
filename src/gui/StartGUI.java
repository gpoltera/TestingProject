package gui;

import helpers.Metadata;
import helpers.QRString;
import helpers.SessionKeySeparator;
import crypto.SessionKey;

public class StartGUI {
	private static String qrString;
	private static SessionKey sessionKey;
	private static Metadata metadata;
	
	public static void main(String[] args) {
		
		metadata = new Metadata();
		metadata.setFilename("aTestPDFDocument.pdf");
		metadata.setFilesize("12.5 MB");
		metadata.setURI("http://hsr.ch/029839839489489.file");
		
		
		sessionKey = new SessionKey(128);
		qrString = QRString.createQRString(metadata, sessionKey);
		
		new SessionKeyGenerator(qrString, sessionKey.getSeparatedWithChecksum());
		new SessionKeyReader();
	}
}