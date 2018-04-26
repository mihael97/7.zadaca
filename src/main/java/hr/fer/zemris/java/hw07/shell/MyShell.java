package hr.fer.zemris.java.hw07.shell;

import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw07.shell.commands.CatShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.CharsetsShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.CopyShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.Functions;
import hr.fer.zemris.java.hw07.shell.commands.HelpShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.HexdumpShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.LsShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.MkdirShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.TreeShellCommand;

/**
 * Razred koji predtavlja ljusku za komunikaciju s korisnikom
 * 
 * @author Mihael
 *
 */
public class MyShell implements Environment {
	/**
	 * Simbol koji se pojavljuje na početku svake naredbe
	 */
	private static Character PROMPTSYMBOL;
	/**
	 * Simnbol koji simbolizira nastavak višelinijske naredbe
	 */
	private static Character MORELINESSYMBOL;
	/**
	 * Simbol koji se pojavljuje na početku svake linije višelinijske naredbe
	 */
	private static Character MULTILINESYMBOL;
	/**
	 * Mapa svih naredbi
	 */
	private static SortedMap<String, ShellCommand> commands;
	/**
	 * Referenca na scanner koji prima upis s ulaza
	 */
	public static Scanner sc;

	/**
	 * Metoda inicijalizira početne simbole i mapu naredb
	 */
	public void initialize() {
		PROMPTSYMBOL = '>';
		MORELINESSYMBOL = '\\';
		MULTILINESYMBOL = '|';

		commands = new TreeMap<>();

		commands.put("cat", new CatShellCommand());
		commands.put("charsets", new CharsetsShellCommand());
		commands.put("copy", new CopyShellCommand());
		commands.put("exit", new ExitShellCommand());
		commands.put("help", new HelpShellCommand());
		commands.put("ls", new LsShellCommand());
		commands.put("mkdir", new MkdirShellCommand());
		commands.put("hexdump", new HexdumpShellCommand());
		commands.put("tree", new TreeShellCommand());

		System.out.print("Welcome to MyShell v 1.0");

	}

	/**
	 * Metoda čita naredbu sa ulaza
	 * 
	 * @return naredba pročitana u obliku stringa
	 */
	@Override
	public String readLine() throws ShellIOException {
		List<String> list = new LinkedList<>();
		String str = new String();

		try {
			write("\n" + PROMPTSYMBOL + " ");
			str = sc.nextLine();

			list.add(str);
			if (str.endsWith(String.valueOf(MORELINESSYMBOL))) {
				while (true) {
					write("\n" + MULTILINESYMBOL + " ");
					str = sc.nextLine();
					list.add(str);

					if (!str.endsWith(String.valueOf(MORELINESSYMBOL))) {
						break;
					}
				}

				return Functions.oneLine(list);
			}

			return str;
		} catch (InputMismatchException e) {
			throw new ShellIOException("Problems with scanner in readLine()");
		}
	}

	/**
	 * Metoda ispisuje naredbu na standardni izlaz
	 * 
	 * @param text
	 *            - naredba za ispis
	 */
	@Override
	public void write(String text) throws ShellIOException {
		try {
			System.out.print(text);
		} catch (Exception e) {
			throw new ShellIOException("Exception during writing on standard output!");
		}
	}

	/**
	 * Metoda ispisuje naredbu na standardni izlaz
	 * 
	 * @param text
	 *            - naredba za ispis
	 */
	@Override
	public void writeln(String text) throws ShellIOException {
		System.out.println(text);
	}

	/**
	 * Metoda vraća mapu koja sadrži sve naredbi
	 * 
	 * @return {@link SortedMap} svih naredbi
	 */
	@Override
	public SortedMap<String, ShellCommand> commands() {
		return commands;
	}

	/**
	 * Metoda vraća trenutni znak koji predstavlja početak nastavka višelinijske
	 * naredbe
	 * 
	 * @return trenutni znak koji predstavlja početak nastavka višelinijske naredbe
	 */
	@Override
	public Character getMultilineSymbol() {
		return MULTILINESYMBOL;
	}

	/**
	 * Metoda postavlja trenutni znak koji predstavlja početak nastavka višelinijske
	 * naredbe
	 * 
	 * @param symbol
	 *            - budući znak koji predstavlja početak nastavka višelinijske
	 *            naredbe
	 */
	@Override
	public void setMultilineSymbol(Character symbol) {
		MULTILINESYMBOL = Objects.requireNonNull(symbol);
	}

	/**
	 * Metoda vraća trenutni simbol za početak naredbe
	 * 
	 * @return trenutni simbol za početak naredbe
	 */
	@Override
	public Character getPromptSymbol() {
		return PROMPTSYMBOL;
	}

	/**
	 * Metoda koja postavlja simbol koji predstavlja početak upisa naredbe
	 * 
	 * @param symbol
	 *            - novi simbol
	 */
	@Override
	public void setPromptSymbol(Character symbol) {
		PROMPTSYMBOL = Objects.requireNonNull(symbol);
	}

	/**
	 * Metoda vraća trenutni simbol za nastavak višelinijske naredbe
	 * 
	 * @return simbol za nastavak više linijske naredbe
	 */
	@Override
	public Character getMorelinesSymbol() {
		return MORELINESSYMBOL;
	}

	/**
	 * Metoda koja postavlja znak za simboliziranje višelinijske naredbe
	 * 
	 * @param symbol
	 *            - novi simbol
	 */
	@Override
	public void setMorelinesSymbol(Character symbol) {
		MORELINESSYMBOL = Objects.requireNonNull(symbol);
	}

	/**
	 * Metoda koja iz naredbe odbacuje argumente i vraća samo naziv naredbe
	 * 
	 * @param line
	 *            - naredba
	 * @return naziv naredbe
	 */
	public String extractCommandName(String line) {
		char[] array = line.toCharArray();
		int index = 0;
		boolean name = false;

		while (index < array.length) {
			if (Character.isLetter(array[index]) && name == false) {
				name = true;
			} else if (Character.isWhitespace(array[index]) && name == true) {
				if (commands.containsKey(line.substring(0, index)) || line.startsWith("symbol")) {
					return line.substring(0, index);
				}

				throw new IllegalArgumentException("Unsupported operation!");
			}

			index++;
		}

		if (commands.containsKey(line)) {
			return line;
		}

		throw new IllegalArgumentException("Wrong input!");
	}

	/**
	 * Metoda koja iz naredbe izbacuje naziv naredbe i vraća argumente naredbe
	 * 
	 * @param line
	 *            - naredba
	 * @return argumenti naredbe
	 */
	public String extractArguments(String line) {
		StringBuilder builder = new StringBuilder();
		char[] array = line.toCharArray();
		int i = 0, count = 0;
		boolean insideString = false, index = false;

		while (i < array.length) {
			if (Character.isLetter(array[i]) && index == false) {
				index = true;
			} else if (Character.isWhitespace(array[i]) && index == true && count == 0) {
				while (i < array.length && Character.isWhitespace(array[i]))
					i++;

				insideString = true;
				count++;

				if (i < array.length) {
					builder.append(array[i]);
				}

			} else if (insideString) {
				builder.append(array[i]);
			}

			i++;
		}

		return builder.toString();
	}

	/**
	 * Metoda koja postavlja/ispisuje znakove za početak naredbe ili višelinijske
	 * naredbe. PROMPT - znak za početak naredbe,MORELINES - znak na kraju linije
	 * naredbe koji pokazuje da je ostatak naredbe u sljedećem redu,MULTILINES -
	 * početni znak u višelinijskim naredbama
	 * 
	 * @param arguments
	 *            - argument gdje propisujemo što želimo vidjeti
	 */
	private static void symbols(String arguments) {
		String[] array = arguments.split(" ");

		switch (array.length) {
		case 1: // samo ispis
			switch (array[0]) {
			case "PROMPT":
				System.out.print("Symbol for PROMPT is \'" + PROMPTSYMBOL + "\'");
				break;
			case "MORELINES":
				System.out.print("Symbol for MORELINES is \'" + MORELINESSYMBOL + "\'");
				break;
			case "MULTILINE":
				System.out.print("Symbol for MULTILINES is \'" + MULTILINESYMBOL + "\'");
				break;
			default:
				System.err.print("Unsupported symbol!");
			}

			break;
		case 2:
			switch (array[0]) {
			case "PROMPT":
				System.out.print("Symbol for PROMPT changed from \'" + PROMPTSYMBOL);
				PROMPTSYMBOL = array[1].charAt(0);
				System.out.print("\' to \'" + PROMPTSYMBOL + "\'");
				break;
			case "MORELINES":
				System.out.print("Symbol for MORELINES changed from \'" + MORELINESSYMBOL);
				MORELINESSYMBOL = array[1].charAt(0);
				System.out.print("\' to \'" + MORELINESSYMBOL + "\'");
				break;
			case "MULTILINE":
				System.out.print("Symbol for MULTILINE changed from \'" + MULTILINESYMBOL);
				MULTILINESYMBOL = array[1].charAt(0);
				System.out.print("\' to \'" + MULTILINESYMBOL + "\'");
				break;
			default:
				System.err.print("Unsupported symbol!");
			}

			break;
		default:
			System.err.println("Wrong number of arguments!");
		}
	}

	/**
	 * Privatan razred koji sadrži glavni program za pokretanje ljuske
	 * 
	 * @author Mihael
	 *
	 */
	@SuppressWarnings("unused")
	private static class MyShellMain {

		/**
		 * Glavni program
		 * 
		 * @param args
		 *            - ne koristi se
		 */
		public static void main(String[] args) {
			ShellStatus status = ShellStatus.CONTINUE;
			MyShell shell = new MyShell();
			shell.initialize();
			sc = new Scanner(System.in);

			do {
				try {
					String line = shell.readLine();
					String commandName = shell.extractCommandName(line);
					String arguments = shell.extractArguments(line);

					if (commandName.equals("symbol")) {
						symbols(arguments);
					} else {
						ShellCommand command = shell.commands().get(commandName);
						status = command.executeCommand(shell, arguments);
					}
				} catch (IllegalArgumentException e) {
					System.err.println(e.getMessage());
				} catch (ShellIOException e) {
					e.printStackTrace();
					sc.close();
					System.exit(1);
				}
			} while (status != ShellStatus.TERMINATE);

			System.out.println("Goodbye!");
			sc.close();
		}

	}

}
