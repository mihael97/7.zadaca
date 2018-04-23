package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Razred koji implementira naredbu za ispis datoteke u heksadekadskom obliku
 * 
 * @author Mihael
 *
 */
public class HexdumpShellCommand implements ShellCommand {
	/**
	 * Ime naredbe
	 */
	private static final String name = "hexdump";
	/**
	 * Opis naredbe
	 */
	private static List<String> description = new ArrayList<>();

	/**
	 * Zadani konstruktor
	 */
	public HexdumpShellCommand() {
		description.add("Command converts context from file to hex");
	}

	/**
	 * Metoda izvodi naredbu za pretvaranje znakova u heksadekadske brojeve
	 * 
	 * @param env
	 *            - ljuska
	 * @param arguments
	 *            - argument(path do datoteke=)
	 * 
	 * @throws IOException
	 *             - ukoliko dođe do problema sa čitanjem datoteke
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] array = Functions.split(arguments, 1);

		int row = 0;

		try {
			char[] argumentArray = Functions.fromList(Files.readAllLines(Paths.get(array[0])));
			StringBuilder forReturn, builder;

			for (int index = 0, length = argumentArray.length; index < length; index++) {

				if (index != 0)
					index--;

				builder = new StringBuilder();
				forReturn = new StringBuilder().append(makeString(Integer.toHexString(row * 16))).append(": ");

				for (int j = 0; j < 2; j++) {
					int i;
					for (i = 0; i < 8 && index < length; i++, index++) {
						int size = (int) argumentArray[index];
						if (size < 32 || size > 127) {
							builder.append(".");
						} else {
							builder.append(argumentArray[index]);
						}

						forReturn.append(Integer.toHexString((int) argumentArray[index])).append(" ");
					}

					while (i < 8) {
						forReturn.append("   ");
						i++;
					}

					forReturn.append("|");
				}

				forReturn.append(" ").append(builder);
				env.write(forReturn.toString());
				row++;
			}
		} catch (IOException e) {
			System.err.println("Exception during file reading!");
		}

		return ShellStatus.CONTINUE;
	}

	private Object makeString(String hexString) {
		StringBuilder builder = new StringBuilder();

		int i = 0;
		while (i < (8 - hexString.length())) {
			builder.append('0');
			i++;
		}

		return builder.append(hexString);
	}

	/**
	 * Metoda vraća ime naredbe
	 * 
	 * @return ime naredbe
	 */
	@Override
	public String getCommandName() {
		return name;
	}

	/**
	 * Metoda vraća opis naredbe
	 * 
	 * @return opis naredbe
	 */
	@Override
	public List<String> getCommandDescription() {
		return description;
	}

}
