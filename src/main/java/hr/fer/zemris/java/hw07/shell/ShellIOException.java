package hr.fer.zemris.java.hw07.shell;

/**
 * Posebna iznimka koja se korisiti kod iznimnih situacija nekih metoda u
 * razredu {@link MyShell}
 * 
 * @author ime
 *
 */
public class ShellIOException extends RuntimeException {
	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor koji prima poruku za ispis
	 * 
	 * @param description
	 *            - opis pogreške za ispis
	 */
	public ShellIOException(String description) {
		super(description);
	}
}
