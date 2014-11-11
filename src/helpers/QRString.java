package helpers;

import crypto.SessionKey;

public class QRString {
	private static final String DELIMITER = "\n";
	
	public static String createQRString(Metadata metadata, SessionKey sessionKey) {
		String qrString = "";
		qrString = metadata.getFilename() + DELIMITER;
		qrString += metadata.getFilesize() + DELIMITER;
		qrString += metadata.getURI() + DELIMITER;
		qrString += sessionKey.getBase32();
		
		return qrString;
	}
	
	public static String getSessionKey(String qrString) {
		String[] values = qrString.split(DELIMITER);	
		String sessionKey = values[3];
		
		return sessionKey;
	}
}