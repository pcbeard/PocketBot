package legendofmcpe.fakeclient.android;

import java.io.File;
import legendofmcpe.fakeclient.ServerInterface;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;

public class ConnectServerService extends Service{
	public ServerInterface client;
	public final static File dir = new File(Environment.getExternalStorageDirectory(), "fakeclient");
	public final static File oldLog = new File(dir, "console-history.log");
	public final static File currentLog = new File(dir, "console.log");
	public Console console;
	public ConnectServerService(Bundle args){
		try{
			if(!currentLog.isFile()){
				currentLog.delete();
				currentLog.createNewFile();
			}
			console = new Console(this, currentLog);
			client = new ServerInterface(null, this, console);
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
		
	}
}
