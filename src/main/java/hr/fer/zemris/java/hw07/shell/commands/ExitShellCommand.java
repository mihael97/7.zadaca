package hr.fer.zemris.java.hw07.shell.commands;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Razred koji implementira naredbu za završetak izvođenja programa
 * 
 * @author Mihael
 *
 */
public class ExitShellCommand implements ShellCommand {

	/**
	 * Ime naredbe
	 */
	private static String name = "exit";
	/**
	 * Opis naredbe
	 */
	private static List<String> description = new ArrayList<>();

	/**
	 * Zadani konstruktor
	 */
	public ExitShellCommand() {
		description.add("Command terminates current shell");
	}

	/**
	 * Izvedba ove naredbe zaustavlja trenutni program
	 * 
	 * @param env
	 *            - ljuska
	 * @param arguments
	 *            - argumenti(ne koristi se)
	 * 
	 * @return {@link ShellStatus} za prekid programa
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		return ShellStatus.TERMINATE;
	}

	/**
	 * Metoda vrća ime naredbe
	 * 
	 * @return String ime naredbe
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
