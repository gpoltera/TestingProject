package crypto;

import helpers.Base32;

import java.math.BigInteger;

public class Checksum {
	private static final BigInteger BIG32 = BigInteger.valueOf(32);
	
	public static String addChecksum(String string, int position, int overall) {
		String result = "";
		int checksumNumber = 0;
		//Overall checksum if enabled
		if (overall > 0) {
			checksumNumber = checksumNumber + overall;
		}
		//Number of checksums, Specifically worth
		double checksumsCalc = (double) string.length() / (double) position;
		//Number of checksums, Round up
		checksumNumber = checksumNumber + (int) StrictMath.ceil(checksumsCalc) + 1;
		//Calculating the positions of the checksums
		int[] positions = new int[checksumNumber];
		for (int i = 0; i < checksumNumber; i++) {
			positions[i] = (i + 1) * (position - 2) + i;
		}
		//Initial the checksums
		BigInteger overallChecksum = BigInteger.ZERO;
		BigInteger partialChecksum = BigInteger.ZERO;
		//For every character in the string
		for (int i = 0; i < string.length(); i++) {
			//Character
			String character = string.substring(i, i + 1);
			//BigInteger bitstring of the character
			BigInteger value = BigInteger.valueOf(Integer.parseInt(Base32.getBitString(character)));
			//Add to the partialChecksum
			partialChecksum = partialChecksum.add(value);
			//Add the character to the result
			result = result + character;
			//If the position for a checksum is arrive, calculate the checksum and add it to the result
			for (int j = 0; j < positions.length; j++) {
				if (i == positions[j]) {
					result = result + Base32.getBase32(partialChecksum.mod(BIG32).toString(2));
					partialChecksum = BigInteger.ZERO;
				}
			}
			//If the overall checksum is enabled, add the value to the overallChecksum
			if (overall > 0) {
				overallChecksum = overallChecksum.add(value);
			}
		}		
		//Add last partial checksum
		result = result + Base32.getBase32(partialChecksum.mod(BIG32).toString(2));
		//If the overall checksum is enabled, calculate the checksum and add it to the result
		if (overall > 0) {
			//Add overall checksum
			String checksum = Base32.getBase32(overallChecksum.mod(BIG32.pow(overall)).toString(2));
			while (checksum.length() < overall) {
				checksum = checksum + "Z";
			} 
			result = result + checksum;
		}

		return result;
	}
	
	public static boolean verifyChecksum(String string, int checksumNumber) {
		//Initial the checksums
		BigInteger checksum = BigInteger.ZERO;
		
		//For every character in the string, except the last one (checksum)
		for (int i = 0; i < string.length() - checksumNumber; i++) {
			//Character
			String character = string.substring(i, i + 1);
			//BigInteger bitstring of the character
			BigInteger value = BigInteger.valueOf(Integer.parseInt(Base32.getBitString(character)));
			//Add to the partialChecksum
			checksum = checksum.add(value);
		}
		//Calculate the new checksum and compare with the old one
		String newChecksum = Base32.getBase32(checksum.mod(BIG32.pow(checksumNumber)).toString(2));
		String oldChecksum = string.substring(string.length() - checksumNumber);
		while (newChecksum.length() < checksumNumber) {
			newChecksum = newChecksum + "Z";
		}
		if (newChecksum.equals(oldChecksum)) {
			return true;
		}

		return false;
	}
}

