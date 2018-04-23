package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Razred implementira čitač datoteke koja se kasnije ispijuje na standardni
 * izlaz
 * 
 * @author Mihael
 *
 */
public class CatShellCommand implements ShellCommand {
	/**
	 * Naziv naredbe
	 */
	private static final String name = "cat";
	/**
	 * Opis naredbe
	 */
	private static final List<String> list = new ArrayList<>();

	/**
	 * Zadani konstruktor
	 */
	public CatShellCommand() {
		list.add("Command prints context of file to standard output. "
				+ "It can have one or two arguments. First must be path to file,and second is charset. "
				+ "If command is initialized with one argument,charset will be set to default charset.");
	}

	/**
	 * Metoda koja izvršava naredbu tako da pročita sve linije i pojedinačno ih
	 * prosljeđuje metodama ljuske
	 * 
	 * @param env
	 *            - referenca na ljusku
	 * @param arguments
	 *            - path do datoteke(obavezno) i charset(opcionalno)
	 * 
	 * @return {@link ShellStatus} za nastavak programa
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] array = Functions.split(arguments, 2);

		try {
			for (String string : Files.readAllLines(Paths.get(array[0]),
					array[1] == null ? Charset.defaultCharset() : Charset.forName(array[1].trim()))) {
				env.write(string);
			}
		} catch (IllegalCharsetNameException e) {
			System.err.println("Charset " + array[1] + " doesn't exist");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * Vraća naziv naredbe
	 * 
	 * @return naziv naredbe
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
		return list;
	}

}
