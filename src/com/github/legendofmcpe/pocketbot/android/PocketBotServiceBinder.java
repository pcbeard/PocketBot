package com.github.legendofmcpe.pocketbot.android;

import android.os.Binder;

public class PocketBotServiceBinder extends Binder{
	private PocketBotService service;
	public PocketBotServiceBinder(PocketBotService service){
		this.service = service;
	}
	public PocketBotService getService(){
		return service;
	}
}
