package hr.fer.zemris.java.hw07.shell.commands;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.MyShell;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Razred koji predtavlja naredbu za kopiranje sadržaja iz datoteke
 * 
 * @author Mihael
 *
 */
public class CopyShellCommand implements ShellCommand {
	/**
	 * Ime naredbe
	 */
	private static String name = "copy";
	/**
	 * Opis naredbe
	 */
	private static List<String> description = new ArrayList<>();

	/**
	 * Zadani konstruktor
	 */
	public CopyShellCommand() {
		description.add("Command copies source file to another file");
	}

	/**
	 * Metoda za pokretanje kopiranja sadržaja između dviju datoteka
	 * 
	 * @param env
	 *            - ljuska
	 * @param arguments
	 *            - argumenti
	 * 
	 * @return {@link ShellStatus} za nastavak
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] array = Functions.split(arguments, 2);
		if (array.length != 2) {
			System.err.println("Wrong number of arguments!");
		} else {
			try {
				Path path1 = Paths.get(array[0]);
				Path path2 = Paths.get(array[1]);

				if (!path1.toFile().isFile()) {
					System.err.println("First argument must be file!");
				} else {

					if (path2.toFile().isDirectory()) {
						path2 = Paths.get(path2.toString() + "/" + path1.getFileName());
					}

					if (path2.toFile().exists()) {

						System.out.println("Do you want to overwrite your old file[Y/N]?");
						if (!MyShell.sc.nextLine().toUpperCase().equals("Y")) {
							return ShellStatus.CONTINUE;
						}

					}

					copyFile(path1, path2);
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * Metoda kopira sadržaj iz izvorne u odredišnu datoteku
	 * 
	 * @param path1
	 *            - put do izvorne datoteke
	 * @param path2
	 *            - put do odredišne datoteke
	 */
	private void copyFile(Path path1, Path path2) {
		try (BufferedReader input = Files.newBufferedReader(path1);
				BufferedWriter output = Files.newBufferedWriter(path2)) {

			String line = "";

			while ((line = input.readLine()) != null) {
				output.write(line);
			}

		} catch (IOException e) {
			System.err.println("Problems during file copying");
		}
	}

	/**
	 * Metoda vraća ime datoteke
	 * 
	 * @return ime datoteke
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
