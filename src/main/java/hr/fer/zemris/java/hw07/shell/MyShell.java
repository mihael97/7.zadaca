package hr.fer.zemris.java.hw07.shell;

import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;
import java.util.SortedMap;

public class MyShell implements Environment {
	private static Character PROMPTSYMBOL = '>';
	private static Character MORELINESSYMBOL = '\\';
	private static Character MULTILINESYMBOL;

	public static void main(String[] args) {
		ShellStatus status = ShellStatus.CONTINUE;
		do {
			try {

			} catch (ShellIOException e) {
				e.printStackTrace();
				System.exit(1);
			} catch (Exception e) {
				System.err.println("Unexcepted exception,error!");
				System.exit(1);
			}
		} while (status != ShellStatus.TERMINATE);
	}

	@Override
	public String readLine() throws ShellIOException {
		System.out.print("Welcome to MyShell v 1.0");

		try (Scanner sc = new Scanner(System.in)) {
			System.out.print("\n" + PROMPTSYMBOL);
			return sc.nextLine();
		} catch (InputMismatchException e) {
			throw new ShellIOException("Problems with scanner in readLine()");
		}
	}

	@Override
	public void write(String text) throws ShellIOException {
		try {
			System.out.println(text);
		} catch (Exception e) {
			throw new ShellIOException("Exception during writing on standard output!");
		}
	}

	@Override
	public void writeln(String text) throws ShellIOException {
		write(text);
	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
		return null;
	}

	@Override
	public Character getMultilineSymbol() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {

	}

	@Override
	public Character getPromptSymbol() {
		return PROMPTSYMBOL;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		PROMPTSYMBOL = Objects.requireNonNull(symbol);
	}

	@Override
	public Character getMorelinesSymbol() {
		return MORELINESSYMBOL;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		MORELINESSYMBOL = Objects.requireNonNull(symbol);
	}

}
