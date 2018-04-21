package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

public class Charsets implements ShellCommand {

	private final static String name = "charsets";
	private final static String description = "Command prints all current available charsets for Java";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		for (String string : Charset.availableCharsets().keySet()) {
			env.write(string);
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return name;
	}

	@Override
	public List<String> getCommandDescription() {
		return description;
	}
}
