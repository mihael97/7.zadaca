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
 * Razred koji implementira naredbu za stvaranje novog direktorija
 * 
 * @author Mihael
 *
 */
public class Mkdir implements ShellCommand {
	/**
	 * Naziv naredbe
	 */
	private static final String name = "mkdir";
	/**
	 * Lista koja sadrži opis naredbe
	 */
	private static final List<String> list = new ArrayList<>();

	/**
	 * Zadani konstruktor
	 */
	public Mkdir() {
		list.add("Method accepts argument which reperesents path to new directory and creates it");
	}

	/**
	 * Metoda izvršava naredbu stvaranja novog direktorija
	 * 
	 * @param env
	 *            - referenca na ljusku
	 * @param arguments
	 *            - String koji sadrži path na mjesto stvaranja
	 * 
	 * @return {@link ShellStatus} - oznaka za nastavak programa
	 * 
	 * @throws IOException
	 *             - ako je došlo problema za vrijeme stvaranja
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			Files.createDirectories(Paths.get(Functions.prepareForPath(arguments)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return ShellStatus.CONTINUE;
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
	 * Metoda vraća opis metode
	 * 
	 * @return opis metode
	 */
	@Override
	public List<String> getCommandDescription() {
		return list;
	}

}
