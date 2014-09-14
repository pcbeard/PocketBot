package com.github.legendofmcpe.pocketbot.runner;

import java.io.File;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.github.legendofmcpe.pocketbot.Lang;
import com.github.legendofmcpe.pocketbot.Logger;
import com.github.legendofmcpe.pocketbot.PocketBot;

public class PocketBotRunner{
	private ConsoleListener console;
	private PropertiesConfig config;
	private Map<String, PocketBot> bots = new HashMap<String, PocketBot>(1, 1f);
	public final static File dir;
	static{
		dir = new File("PocketBot");
		dir.mkdirs();
	}

	public PocketBotRunner(){
		loadProfile();
		console = new ConsoleListener(this);
		console.start();
	}
	private void loadProfile(){
		Random random = new Random();
		HashMap<String, String> defaults = new HashMap<String, String>();
		StringBuilder randomChars = new StringBuilder("PocketBot|");
		byte[] randomBytes = new byte[5];
		random.nextBytes(randomBytes);
		for(byte b: randomBytes){
			int i = b;
			while(i < 0){
				i += 62;
			}
			while(i > 62){
				i -= 62;
			}
			if(i < 26){
				randomChars.append((char) (((int) 'A') + i));
			}
			else if(i < 52){
				randomChars.append((char) (((int) 'a') + i - 26));
			}
			else{
				randomChars.append((char) (((int) '0') + i - 52));
			}
		}
		defaults.put("username", randomChars.toString());
		defaults.put("clientId", String.valueOf(random.nextLong()));
		config = new PropertiesConfig(new File(dir,
				"config.properties"), defaults);
	}
	public void addBot(String ip, short port, String name, Logger logger, Lang lang) throws UnknownHostException{
		PocketBot bot = new PocketBot(name, new InetSocketAddress(ip, port), logger, lang, config.get("username"), Long.getLong(config.get("clientId"), 0L));
		bots.put(name, bot);
	}
	public void endBot(String name){
		PocketBot bot = bots.get(name);
		if(bot != null){
			bot.end();
		}
	}
	public PocketBot getBot(String name){
		return bots.get(name);
	}
	public void end(){
		console.end();
	}

	public static void main(String[] args){
		new PocketBotRunner();
	}
}
