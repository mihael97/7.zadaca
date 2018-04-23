package hr.fer.zemris.java.hw07.shell.commands;

import java.util.List;

/**
 * Razred implementira metode koje olakšavaju korištenje programa i izbjegavaju
 * dupliranje koda
 * 
 * @author Mihael
 *
 */
public class Functions {
	/**
	 * Trenutna pozicija u nizu
	 */
	private static int index;
	/**
	 * Polje znakova ulaznog niza
	 */
	private static char[] array;
	/**
	 * Duljina niza
	 */
	private static int length;

	/**
	 * Metoda čita naredbu van navodnika
	 * 
	 * @return pročitani sadržaj
	 */
	private static String readFile() {
		StringBuilder builder = new StringBuilder();

		spaces();

		while (index < array.length) {
			char c = array[index];

			if (c == '\"' || Character.isWhitespace(c))
				break;
			builder.append(c);
			index++;
		}

		return builder.toString();
	}

	/**
	 * Metoda koja preskače bjeline u nizu
	 */
	private static void spaces() {
		while (Character.isWhitespace(array[index]))
			index++;
	}

	/**
	 * Metoda čita naredbu unutar navodnika
	 * 
	 * @return pročitani sadržaj
	 */
	private static String readString() {
		StringBuilder builder = new StringBuilder();

		spaces();

		char c;
		while (index < length) {
			c = array[index];

			if (c == '\\') {
				if ((index + 1 < length) && (array[index + 1] == '\\' || array[index + 1] == '\"')) {
					c = array[index + 1];
				}
			} else if (c == '\"') {
				index++;
				break;
			}

			index++;
			builder.append(c);
		}

		if ((index + 1) < length && !Character.isWhitespace(array[index + 1])) {
			throw new IllegalArgumentException("String can't be parsed");
		}

		return builder.toString();
	}

	/**
	 * Metoda rastavlja argumente naredbe i pretvara ih u zadovoljavajući oblik
	 * 
	 * @param arguments
	 *            - argumenti naredbe
	 * @param i
	 *            - očekivani broj argumenata
	 * @return polje argumenata
	 */
	public static String[] split(String arguments, int i) {
		String[] forReturn = new String[i];

		array = arguments.toCharArray();
		length = array.length;
		index = 0;
		int ind = 0;

		try {
			while (index < length && ind < i) {
				char c = array[index];
				if (c == '\"') {
					index++;
					forReturn[ind++] = readString();
				} else {
					forReturn[ind++] = readFile();
				}
			}

			if (index < length) {
				throw new IllegalArgumentException("Too many arguments!" + index + " " + length);
			}
		} catch (IndexOutOfBoundsException e) {
			System.err.println("Error!");
		}

		return forReturn;

	}

	/**
	 * Metoda višelinijsku naredbu pretvara u jednolinijsku
	 * 
	 * @param list
	 *            -dijelovi naredbe
	 * @return naredba
	 */
	public static String oneLine(List<String> list) {
		StringBuilder builder = new StringBuilder();

		if (list.size() == 1)
			return list.get(0);

		for (int i = 0; i < list.size(); i++) {
			String string = list.get(i);

			if ((i + 1) != list.size()) {
				builder.append(string.substring(0, string.length() - 1));
			} else {
				builder.append(string);
			}
		}

		return builder.toString();
	}

	/**
	 * Metoda koja od elemenata liste stvara novi string
	 * 
	 * @param readAllLines
	 *            - sadržaj datoteke
	 * @return sadržaj u obliku jednog stringa
	 */
	public static char[] fromList(List<String> readAllLines) {
		StringBuilder builder = new StringBuilder();

		for (String string : readAllLines) {
			builder.append(string).append(" ");
		}

		return builder.toString().substring(0, builder.length() - 1).toCharArray();
	}

}
