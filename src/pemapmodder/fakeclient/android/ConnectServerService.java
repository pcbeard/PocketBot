package pemapmodder.fakeclient.android;

import pemapmodder.fakeclient.ServerInterface;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

public class ConnectServerService extends Service{
	public ServerInterface client;
	public ConnectServerService(Bundle args){
		try{
			client = new ServerInterface(null, this);
		}catch(Exception e){
			err(e);
		}
	}
	@Override public int onStartCommand(Intent intent, int flags, int flagID){
		client.start();
		return START_NOT_STICKY;
	}
	@Override public IBinder onBind(Intent intent){
		return null;
	}
	public void err(Throwable e){
		// TODO
	}
}
