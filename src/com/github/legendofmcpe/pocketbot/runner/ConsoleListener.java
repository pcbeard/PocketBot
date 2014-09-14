package com.github.legendofmcpe.pocketbot.runner;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.github.legendofmcpe.pocketbot.Logger;
import com.github.legendofmcpe.pocketbot.PocketBot;

public class ConsoleListener extends Thread implements Logger{
	private boolean running = false;
	private PocketBotRunner runner;
	private String clientName = null;

	public ConsoleListener(PocketBotRunner runner){
		this.runner = runner;
	}
	@Override
	public void run(){
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		running = true;
		while(running){
			try{
				String line = in.readLine();
				if(line != null){
					line = line.trim();
					if(line.length() > 0){
						String[] argsArray = line.split(" ");
						List<String> args = new ArrayList<String>(argsArray.length);
						for(int i = 1; i < argsArray.length; i++){
							args.add(argsArray[i]);
						}
						handleCommand(argsArray[0], args);
					}
				}
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}
		try{
			in.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

	public void handleCommand(String cmd, List<String> args){
		if(cmd.charAt(0) == '.'){
			if(clientName == null){
				cmd("You are not on a server! Use 'switch <name>' to switch to a server.");
			}
			else{
				PocketBot bot = runner.getBot(clientName);
				if(bot == null){
					clientName = null;
					cmd("You are not on a server! Use 'switch <name>' to switch to a server.");
				}
				else if(!bot.isRunning()){
					clientName = null;
					cmd("You are not on a server! Use 'switch <name>' to switch to a server.");
				}
				else{
					StringBuilder msg = new StringBuilder(cmd.substring(1));
					for(String arg: args){
						msg.append(" " + arg);
					}
					bot.queueMessage(msg.toString());
				}
			}
			return;
		}
		if(cmd.equalsIgnoreCase("stop")){
			runner.end();
			cmd("Stopping...");
		}
		else if(cmd.equalsIgnoreCase("connect")){
			if(args.size() < 2){
				cmd("Usage: connect <name> <ip>:<port>");
				return;
			}
			String name = args.get(0);
			if(runner.getBot(name) != null){
				cmd("You are already connecting to a server with this name!");
				return;
			}
			String[] addr = args.get(1).split(":", 2);
			String ip = addr[0];
			short port = 0;
			try{
				port = Short.parseShort(addr[1]);
			}
			catch(NumberFormatException e){
				cmd("Usage: connect <name> <ip>:<port>");
				return;
			}
			try{
				runner.addBot(ip, port, name, this, new PropertiesLang(
						new File(PocketBotRunner.dir, "language.lang")));
			}
			catch(UnknownHostException e){
				cmd("Usage: connect <name> <ip>:<port>; you entered an invalid IP.");
			}
			clientName = name;
		}
		else if(cmd.equalsIgnoreCase("quit")){
			
		}
	}

	public boolean isRunning(){
		return running;
	}
	public void end(){
		running = false;
	}

	public void debug(String line){
		System.out.println("[DEBUG] ".concat(line));
	}
	public void error(String line){
		System.out.println("[ERROR] ".concat(line));
	}
	public void warning(String line){
		System.out.println("[WARNING] ".concat(line));
	}
	public void notice(String line){
		System.out.println("[NOTICE] ".concat(line));
	}
	public void critical(String line){
		System.out.println("[CRITICAL] ".concat(line));
	}
	public void cmd(String line){
		System.out.println("[CMD] ".concat(line));
	}
	public void info(String line){
		System.out.println("[INFO] ".concat(line));
	}
	public void trace(String line){
		System.out.println("[TRACE] ".concat(line));
	}
	public void exception(Throwable t){
		t.printStackTrace(System.err);
	}
}
