package hr.fer.zemris.java.hw07.shell;

/**
 * Enum koji predstavlja moguća stanja koja naredbe mogu vratit. Stanje su
 * continue i terminate,gdje jedino naredbe exit vraća terminate
 * 
 * @author Mihael
 *
 */
public enum ShellStatus {
	/**
	 * Stanje koja označava dozvolu za nastvak izvođenja programa
	 */
	CONTINUE,
	/**
	 * Stanje koje označava kraj izvođenja programa
	 */
	TERMINATE;
}
