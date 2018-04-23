package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Razred koji predstavlja naredbu za ispis svih podržanih charsetova
 * 
 * @author Mihael
 *
 */
public class CharsetsShellCommand implements ShellCommand {

	/**
	 * Ime naredbe
	 */
	private final static String name = "charsets";
	/**
	 * Opis naredbe
	 */
	private final static List<String> description = new ArrayList<>();

	/**
	 * Zadani program
	 */
	public CharsetsShellCommand() {
		description.add("Command prints all current available charsets for Java");
	}

	/**
	 * Metoda koja izvodi naredbu za ispis svih podržanih charsetova
	 * 
	 * @return {@link ShellStatus} za nastavak programa
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		for (String string : Charset.availableCharsets().keySet()) {
			env.write(string);
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * Metoda koja vraća ime naredbe
	 * 
	 * @return ime naredbe
	 */
	@Override
	public String getCommandName() {
		return name;
	}

	/**
	 * Metoda koja vraća opis naredbe
	 * 
	 * @return opis naredbe
	 */
	@Override
	public List<String> getCommandDescription() {
		return description;
	}
}
