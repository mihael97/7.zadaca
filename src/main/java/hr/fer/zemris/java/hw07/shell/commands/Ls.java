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
 * Razred implementira razred za ispis datoteka unutar nekog direktorija s
 * njezinim informacijama(veličina,datum i vrijeme izrade,naziv i osnovne
 * karakteristike(izvršna,da li je može u nju pisati odnosno čitati ili ako je
 * direktorij)
 * 
 * @author Mihael
 *
 */
public class Ls implements ShellCommand {
	/**
	 * Naziv naredbe
	 */
	private static final String name = "ls";
	/**
	 * Lista koja sadrži opis naredbe
	 */
	private static final List<String> list = new ArrayList<>();;

	/**
	 * Zadani konstruktor
	 */
	public Ls() {
		list.add("Method prints statistic of all files. It prints file's name,time "
				+ "and date when it was made,size and basic informations(executable,writable,"
				+ "readable,is directory)");
	}

	/**
	 * Metoda izvršava naredbu tako da pozivom metode za obilazak po
	 * datotekama,stvara opisnik sa svim informacijama za ispis
	 * 
	 * @param env
	 *            - referenca na ljusku
	 * @param arguments
	 *            - String koji sadrži path direktorija kojeg želimo provjeriti
	 * 
	 * @return {@link ShellStatus} - oznaka za nastavak programa
	 * 
	 * @throws IOException
	 *             - ako je došlo problema za vrijeme prolaska kroz stablo
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		MyFileVisitor visitor = new MyFileVisitor();
		try {
			Files.walkFileTree(Paths.get(arguments), visitor);

			for (String string : visitor.getList()) {
				env.write(string);
			}

		} catch (IOException e) {
			System.err.println("Problems with file visitor!");
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
