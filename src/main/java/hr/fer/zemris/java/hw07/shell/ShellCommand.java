package hr.fer.zemris.java.hw07.shell;

import java.util.List;

/**
 * Sučelje koje propisuje metode koje svaka naredba mora implementirati
 * 
 * @author Mihael
 *
 */
public interface ShellCommand {
	/**
	 * Metoda za izvršavanje naredbe
	 * 
	 * @param env
	 *            - ljuska
	 * @param arguments
	 *            - argumenti
	 * @return {@link ShellStatus} ovisno treba li program nastaviti sa radom
	 */
	ShellStatus executeCommand(Environment env, String arguments);

	/**
	 * Vraća ime naredbe
	 * 
	 * @return ime naredbe
	 */
	String getCommandName();

	/**
	 * Vraća opis naredbe
	 * 
	 * @return opis naredbe
	 */
	List<String> getCommandDescription();
}
