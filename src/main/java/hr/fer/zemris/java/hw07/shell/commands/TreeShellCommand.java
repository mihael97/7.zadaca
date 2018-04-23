package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Razred koji implementira naredbu za izradu stabla putem rekurzivnih poziva
 * 
 * @author Mihael
 *
 */
public class TreeShellCommand implements ShellCommand {
	/**
	 * Ime naredbe
	 */
	private static String name = "tree";
	/**
	 * Opis naredbe
	 */
	private static List<String> description = new ArrayList<>();

	/**
	 * Inicijalni konstruktor
	 */
	public TreeShellCommand() {
		description.add("Command prints file tree of directory given directory");
	}

	/**
	 * Metoda koja izvršava naredbu tako da rekurzivnim putem ispisuje stablo
	 * datoteka
	 * 
	 * @param env
	 *            - ljuska
	 * @param arguments
	 *            - path do datoteke
	 * 
	 * @return {@link ShellStatus} za nastavak programa
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			env.write(makeTree(Functions.split(arguments, 1)[0], 0));
		} catch (IllegalArgumentException e) {
			System.err.println("Argument has unclosed or unopened quotation marks");
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * Vraća ime naredbe
	 * 
	 * @return ime naredbe
	 */
	@Override
	public String getCommandName() {
		return name;
	}

	/**
	 * Vraća opis naredbe
	 * 
	 * @return opis naredbe
	 */
	@Override
	public List<String> getCommandDescription() {
		return description;
	}

	/**
	 * Metoda rekurzivnim pozivima stavara stablo datoteka
	 * 
	 * @param path
	 *            - put do direktorija
	 * @param level
	 *            - razina drveća(uvjetuje broj praznina - uvlačenje)
	 * @return ispis stabla u obliku {@link String}
	 */
	private String makeTree(String path, int level) {

		StringBuilder builder = new StringBuilder();

		if (level != 0) {

			int i = 0;
			while (i < level) {
				builder.append("\t");
				i++;
			}

			builder.append(new File(path).getName()).append("\n");
		}

		File[] files = new File(path).listFiles();

		if (files != null) {
			for (File file : files) {
				String pomString = makeTree(file.getAbsolutePath(), level + 1);
				if (pomString.length() != 0)
					builder.append(pomString + "\n");
			}
		}

		return builder.toString();
	}

}
