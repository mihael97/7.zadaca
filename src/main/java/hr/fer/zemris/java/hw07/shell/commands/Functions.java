package hr.fer.zemris.java.hw07.shell.commands;

import java.lang.reflect.Array;

/**
 * Razred implementira metode koje olakšavaju korištenje programa i izbjegavaju
 * dupliranje koda
 * 
 * @author Mihael
 *
 */
public abstract class Functions {
	private static int index;
	private static char[] array;

	public static String prepareForPath(String arguments) {
		StringBuilder builder = new StringBuilder();
		array = arguments.toCharArray();
		index = 0;

		while (index < arguments.length()) {
			char c = array[index];
			if (c == '\"') {
				builder.append(readString());
			} else {
				builder.append(readFile());
			}
		}

		return builder.toString();
	}

	private static String readString() {
		StringBuilder builder = new StringBuilder();

		int length = array.length;
		char c;
		while (index < length) {
			c = array[index];

			if (c == '\\') {
				if ((index + 1 < length) && (array[index + 1] == '\\' || array[index + 1] == '\"')) {
					c = array[index + 1];
				}
			} else if (c == '\"') {
				builder.append(c);
				break;
			}

			builder.append(c);
		}

		if ((index + 1) < length && !Character.isWhitespace(array[index + 1])) {
			System.err.println("String cannot be parsed!");
		}

		return builder.toString();
	}

}
