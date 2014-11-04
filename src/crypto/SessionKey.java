package crypto;

import helpers.Base32;
import helpers.Base64;
import helpers.SessionKeySeparator;

import java.math.BigInteger;

public class SessionKey {

	private String random;

	public SessionKey(int keySize) {
		this.random = RandomGenerator.getRandom(keySize).toString(2);
	}
	
	public SessionKey(String sessionKey) {
		this.random = Base32.getBitString(sessionKey);
	}

	/**
	 * @return bits BigInteger
	 */
	public BigInteger getBits() {
		return new BigInteger(random, 2);
	}

	/**
	 * @return decimal BigInteger
	 */
	public BigInteger getDecimal() {
		return new BigInteger(random, 10);
	}

	/**
	 * @return hex BigInteger
	 */
	public BigInteger getHex() {
		return new BigInteger(random, 16);
	}

	/**
	 * @return base32 string
	 */
	public String getBase32() {
		return Base32.getBase32(random);
	}
	
	/**
	 * @return base64 string
	 */
	public String getBase64() {
		return Base64.getBase64(random);
	}
	
	/**
	 * @return CheckSum Base32 for the random value
	 */	
	public String getWithCheckSum32() {
		return Checksum.addChecksum(getBase32(), 5, 2);
	}
	
	/**
	 * @return Separated in blocks, with checksum
	 */	
	public String[] getSeparatedWithChecksum() {
		return SessionKeySeparator.getSeparated(getWithCheckSum32(), 5);
	}
}

