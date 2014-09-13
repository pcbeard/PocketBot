package com.github.legendofmcpe.pocketbot;

public interface Logger{
	public void info(String line);
	public void debug(String line);
	public void error(String line);
	public void critical(String line);
	public void exception(Throwable t);
	public void warning(String string);
}
