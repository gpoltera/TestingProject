package helpers;

public class Base64 {
	private static final String[] CHARACTERS = { "2", "3", "4", "5", "6", "7",
			"8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k",
			"m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y",
			"z", "A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "L",
			"M", "N", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
			"+", "-", "_", "*", "!", "?", "/" };
	private static final int[] VALUES = { 1, 2, 4, 8, 16, 32 };

	/**
	 * Transform a bit-string in a base64 string
	 * 
	 * @param bitstring
	 * @return base64 string
	 */
	public static String getBase64(String bin) {
		String string = "";

		for (int i = 0; i < bin.length(); i++) {
			int begin = bin.length() - 1 - i - VALUES.length;
			int end = bin.length() - 1 - i;

			if (begin < 0) {
				begin = 0;
			}

			String bincharacter = bin.substring(begin, end);
			i = i + VALUES.length;

			int value = toBase64(bincharacter);

			string = getBase64Character(value) + string;
		}

		return string;

	}

	/**
	 * Transform a bit-string in a base64 integer
	 * 
	 * @param bitstring
	 * @return base64 integer
	 */
	private static int toBase64(String string) {
		int value = 0;
		if (string.length() <= VALUES.length) {
			for (int i = 0; i < string.length(); i++) {
				char substring = string.charAt(string.length() - i - 1);
				if (substring == '1') {
					value += VALUES[i];
				}
			}
		}

		return value;
	}

	public static String getBase64Character(int value) {
		return CHARACTERS[value];
	}
}

