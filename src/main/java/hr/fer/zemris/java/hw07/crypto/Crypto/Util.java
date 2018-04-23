package hr.fer.zemris.java.hw07.crypto.Crypto;

/**
 * Razred implementira metode za pretvaranje nizova u polja bajtova i suprotno
 * 
 * @author Mihael
 *
 */
public class Util {
	/**
	 * Metoda pretvara niz koji predstavlja heksadekadski broj u polje bajtova
	 * 
	 * @param keyText
	 *            - heksadekadski niz
	 * @return - polje bajtova
	 * 
	 * @throws IllegalArgumentException
	 *             - ako se niz ne može pretvptiti u polje bajtova
	 */
	public static byte[] hextobyte(String keyText) {
		byte[] array = new byte[keyText.length() / 2];

		for (int i = 0, length = array.length; i < length; i++) {
			try {
				array[i] = (byte) Integer.parseInt(keyText.substring(i * 2, i * 2 + 2), 16);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Argument can't be converted to byte array!");
			}
		}

		return array;
	}

	/**
	 * Metoda pretvara polje bajtova u reprezentaciju u obliku niza znakova
	 * 
	 * @param array
	 *            - polje bajtova
	 * @return znakovni prikaz polja
	 */
	public static String bytetohex(byte[] array) {
		StringBuilder builder = new StringBuilder();

		for (byte pomByte : array) {
			builder.append(String.format("%02x", pomByte));
		}

		return builder.toString();
	}
	
	

}
