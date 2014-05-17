package pemapmodder.fakeclient.android;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

public class ConnectServerService extends Service{
	public ConnectServerService(Bundle args){
		
	}
	@Override
	public IBinder onBind(Intent intent){
		return null;
	}
}
