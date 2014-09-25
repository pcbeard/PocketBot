package com.github.legendofmcpe.pocketbot;

import java.io.PrintStream;

public interface Logger{
	public void debug(String line);
	public void error(String line);
	public void warning(String line);
	public void notice(String line);
	public void critical(String line);
	public void cmd(String line);
	public void info(String line);
	public void trace(String line);
	public void exception(Throwable t);
	public PrintStream getPrinter();
}
