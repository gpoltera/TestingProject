package crypto;

import java.math.BigInteger;
import java.security.SecureRandom;

public class RandomGenerator {
	/**
	 * Create a random-value with individual bit-length
	 * 
	 * @param bitlength
	 *            the length of the random-value
	 * @return random-value
	 */
	public static BigInteger getRandom(int bitlength) {
		BigInteger random = new BigInteger(bitlength, new SecureRandom());

		while (random.bitLength() != bitlength) {
			random = new BigInteger(bitlength, new SecureRandom());
		}

		return random;
	}
        
        public static BigInteger getRandomWithLeading1(int bitlength) {
            		BigInteger random = new BigInteger(bitlength, new SecureRandom());
		// Set the first two bits to 1
		random = new BigInteger("11".concat(random.toString(2)
				.subSequence(2, random.bitLength()).toString()), 2);

		while (random.bitLength() != bitlength) {
			random = new BigInteger(bitlength, new SecureRandom());
			// Set the first two bits to 1
			random = new BigInteger("11".concat(random.toString(2)
					.subSequence(2, random.bitLength()).toString()), 2);
		}

		return random;
        }
}

