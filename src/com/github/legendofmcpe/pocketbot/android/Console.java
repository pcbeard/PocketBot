package com.github.legendofmcpe.pocketbot.android;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.support.v4.util.ArrayMap;

import com.github.legendofmcpe.pocketbot.Logger;

public class Console implements Logger{
	public static abstract class UpdateListener{
		public void onLineAdded(String line){}
		public void onCleared(){}
	}

	private Context ctx;
	private Map<String, UpdateListener> listeners = new ArrayMap<String, UpdateListener>(1);
	private List<String> lines = new ArrayList<String>();
	public Console(Context ctx){
		this.ctx = ctx;
	}
	public boolean addListener(String key, UpdateListener listener){
		if(listeners.containsKey(key)){
			return false;
		}
		listeners.put(key, listener);
		return true;
	}
	public boolean removeListener(String key){
		return listeners.remove(key) != null;
	}
	@Override
	public void debug(String line){
		addLine(ctx.getString(R.string.console_debug).concat(line));
	}
	@Override
	public void error(String line){
		addLine(ctx.getString(R.string.console_error).concat(line));
	}
	@Override
	public void warning(String line){
		addLine(ctx.getString(R.string.console_warning).concat(line));
	}
	@Override
	public void notice(String line){
		addLine(ctx.getString(R.string.console_notice).concat(line));
	}
	@Override
	public void critical(String line){
		addLine(ctx.getString(R.string.console_critical).concat(line));
	}
	@Override
	public void cmd(String line){
		addLine(ctx.getString(R.string.console_cmd).concat(line));
	}
	@Override
	public void trace(String line){
		addLine(ctx.getString(R.string.console_trace).concat(line));
	}
	@Override
	public void info(String line){
		addLine(ctx.getString(R.string.console_info).concat(line));
	}
	@Override
	public synchronized void exception(Throwable t){
		addLine(ctx.getString(R.string.console_exception)
				.replace("%class%", t.getClass().getSimpleName())
				.replace("%msg%", t.getMessage()));
		
		addLine(ctx.getString(R.string.console_exception_trace));
		int i = 0;
		for(StackTraceElement l: t.getStackTrace()){
			trace(ctx.getString(R.string.console_exception_trace_line)
					.replace("%i%", String.valueOf(i++))
					.replace("%file%", l.getFileName())
					.replace("%line%", String.valueOf(l.getLineNumber()))
					.replace("%method%", l.getMethodName())
					.replace("%class%", l.getClassName()));
		}
	}
	private void addLine(String line){
		line = line.trim();
		for(String l: line.split("\n")){
			lines.add(l);
			for(UpdateListener listener: listeners.values()){
				listener.onLineAdded(l);
			}
		}
	}

}
