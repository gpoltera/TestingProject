package test;

import crypto.SessionKey;
import helpers.Base32;

public class Base32Test {
	public static void main(String[] args) {
		SessionKey sessionKey = new SessionKey(256);
		String result1 = sessionKey.getBase32();
		String result2 = Base32.getBitString(result1);
		
		System.out.println(sessionKey.getBits().toString(2));
		System.out.println(result1);
		System.out.println(result2);
	}
}