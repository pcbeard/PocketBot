package com.github.legendofmcpe.pocketbot.runner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

public class PropertiesConfig{
	private Map<String, String> data;
	private File file;
	public PropertiesConfig(File file){
		this(file, new HashMap<String, String>(0));
	}
	public PropertiesConfig(File file, Map<String, String> defaults){
		this.file = file;
		if(file.isFile()){
			try{
				data = new HashMap<String, String>();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(new FileInputStream(file)));
				String line;
				int i = 0;
				while((line = reader.readLine()) != null){
					i++;
					if(line.charAt(0) == '#'){
						continue;
					}
					if(line.trim().length() == 0){
						continue;
					}
					String[] parts = line.split("=", 2);
					if(parts.length < 2){
						System.out.println("Error parsing " + file.getCanonicalPath()
								+ ": '=' not found on line " + i
								+ ", ignoring that line");
						continue;
					}
					if(data.put(parts[0], parts[1]) != null){
						System.out.println("Warning for properties file "
								+ file.getCanonicalPath() + ": Key " + parts[0]
								+ " duplicated; using the one on line " + i);
					}
				}
				reader.close();
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}
		else{
			data = defaults;
		}
	}
	public String get(String key){
		return data.get(key);
	}
	public String set(String key, String value){
		return data.put(key, value);
	}
	public String unset(String key){
		return data.remove(key);
	}
	public boolean isset(String key){
		return data.containsKey(key);
	}
	public void save(){
		try{
			OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file));
			for(Map.Entry<String, String> entry: data.entrySet()){
				writer.write(entry.getKey()
						.concat("=")
						.concat(entry.getValue())
						.concat(System.getProperty("line.separator")));
			}
			writer.flush();
			writer.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
}
